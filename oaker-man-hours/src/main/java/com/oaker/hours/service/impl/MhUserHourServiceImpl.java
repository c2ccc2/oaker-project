package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.config.AppConfig;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.domain.entity.SysUser;
import com.oaker.common.core.domain.model.LoginUser;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.exception.ServiceException;
import com.oaker.common.utils.DateUtils;
import com.oaker.common.utils.FestivalsUtil;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.MhUserHourMiss;
import com.oaker.hours.doman.entity.MhUserLeave;
import com.oaker.hours.doman.entity.ProjectUser;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.UserHourCalendarVO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourExportVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourProjectStat;
import com.oaker.hours.doman.vo.UserHourStatDetailVO;
import com.oaker.hours.doman.vo.UserHourStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.enums.LeaveTypeEnum;
import com.oaker.hours.enums.ProjectStatusEnum;
import com.oaker.hours.enums.UserHourFillStatusEnum;
import com.oaker.hours.mapper.MhUserHourMapper;
import com.oaker.hours.service.MhUserHourService;
import com.oaker.system.domain.SysPost;
import com.oaker.system.service.impl.SysPostServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 14:26
 */
@Service
public class MhUserHourServiceImpl extends ServiceImpl<MhUserHourMapper, MhUserHour> implements MhUserHourService {

    @Resource
    private SysPostServiceImpl postService;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private MhUserLeaveServiceImpl userLeaveService;

    @Resource
    private ProjectHourServiceImpl projectHourService;

    @Resource
    private MhHourDetailServiceImpl hourDetailService;

    @Resource
    private ProjectUserServiceImpl projectUserService;

    @Resource
    private MhUserHourMissServiceImpl userHourMissService;

    /**
     * 1. 写入mh_user_hour 用户工时填报表
     * 2. 写入mh_hour_detail工时填报详情表
     * 3. 更新mh_project_hour表中已用工时
     * 4. 如果mh_user_hour_miss用户缺报记录表 有缺报记录 则执行删除
     * @param userHourSaveDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(UserHourSaveDTO userHourSaveDTO) {
        Long userId = SecurityUtils.getUserId();
        EntityWrapper<MhUserHour> queryWrapper = new EntityWrapper<>();
        queryWrapper.eq(Columns.MhUserHour.fillDate, userHourSaveDTO.getDate())
                .and()
                .eq(Columns.MhUserHour.userId, userId);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new ServiceException("当前日期已经填报工时，请勿重复提交");
        }
        List<UserHourSaveDTO.ProjectHour> projectHours = userHourSaveDTO.getProjectHours();
        BigDecimal total = projectHours.stream()
                .map(UserHourSaveDTO.ProjectHour::getHour)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 工时总数判断
        if (total.compareTo(new BigDecimal("24")) > 0) {
            throw new ServiceException("工时总数不能超过24");
        }
        if (total.compareTo(AppConfig.WORK_TIME) > 0 && !AppConfig.OVERTIME_ALLOW) {
            throw new ServiceException("提交工时总数超过限定值");
        }
        LocalDate fillDate = userHourSaveDTO.getDate();
        Date createTime = new Date();
        MhUserHour userHour = new MhUserHour();
        userHour.setUserId(userId)
                .setFillDate(fillDate)
                .setTotalHour(total)
                .setCreateTime(createTime);
        // 1. 写入mh_user_hour 用户工时填报表
        baseMapper.insert(userHour);
        ProjectUser projectUser;
        MhHourDetail mhHourDetail;
        // 2. 写入mh_hour_detail工时填报详情表
        List<MhHourDetail> detailList = new ArrayList<>(projectHours.size());
        for (UserHourSaveDTO.ProjectHour projectHour : projectHours) {
            mhHourDetail = new MhHourDetail();
            MhProject mhProject = projectService.selectById(projectHour.getProjectId());
            EntityWrapper<ProjectUser> wrapper = new EntityWrapper<>();
            wrapper.eq(Columns.ProjectUser.projectId, projectHour.getProjectId())
                    .and()
                    .eq(Columns.ProjectUser.userId, userId);
            projectUser = projectUserService.selectOne(wrapper);
            mhHourDetail.setProjectId(projectHour.getProjectId())
                    .setUserId(userId)
                    .setUseHour(projectHour.getHour())
                    .setFillDate(fillDate)
                    .setProjectStatus(mhProject.getProjectStatus())
                    .setHourId(userHour.getId())
                    .setEveryday(projectUser.getEveryday())
                    .setDaily(projectHour.getDaily())
                    .setCreateTime(createTime);
            detailList.add(mhHourDetail);
            // 3. 更新mh_project_hour表中已用工时
            projectHourService.addTotalHour(projectHour.getProjectId(), projectHour.getHour());
        }
        hourDetailService.insertBatch(detailList);
        // 4. 如果mh_user_hour_miss用户缺报记录表 有缺报记录 则执行删除
        EntityWrapper<MhUserHourMiss> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMiss.userId, userId)
                .and()
                .eq(Columns.MhUserHourMiss.missDate, fillDate);
        MhUserHourMiss mhUserHourMiss = userHourMissService.selectOne(wrapper);
        if (!Objects.isNull(mhUserHourMiss)) {
            userHourMissService.deleteByIdAndDetail(mhUserHourMiss.getId());
        }
        return Boolean.TRUE;
    }


    @Override
    public List<UserHourListVO> queryList(LocalDate startDate, LocalDate endDate) {
        Long userId = SecurityUtils.getUserId();
        Date firstJoinTime = projectUserService.getUserFirstJoinTime(userId);
        // 如果当前用户没有加入项目 或者 加入项目查询范围内用户还没有加入项目
        if (Objects.isNull(firstJoinTime) || endDate.isBefore(DateUtils.date2LocalDate(firstJoinTime))) {
            return Collections.emptyList();
        }
        if (startDate.isBefore(DateUtils.date2LocalDate(firstJoinTime))) {
            startDate = DateUtils.date2LocalDate(firstJoinTime);
        }
        // 查询出用户所有缺报记录
        List<MhUserHourMiss> mhUserHourMisses = userHourMissService.queryUserList(userId, startDate, endDate);
        Map<LocalDate, MhUserHourMiss> missMap = mhUserHourMisses.stream().collect(Collectors.toMap(MhUserHourMiss::getMissDate, miss -> miss));
        Set<LocalDate> missDates = mhUserHourMisses.stream().map(MhUserHourMiss::getMissDate).collect(Collectors.toSet());

        // 查询用户所用填报记录
        EntityWrapper<MhUserHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, startDate, endDate);
        List<MhUserHour> mhUserHours = baseMapper.selectList(wrapper);
        Map<LocalDate, MhUserHour> hourMap = mhUserHours.stream().collect(Collectors.toMap(MhUserHour::getFillDate, mhUserHour -> mhUserHour));
        Set<LocalDate> hourDates = mhUserHours.stream().map(MhUserHour::getFillDate).collect(Collectors.toSet());

        // 查询用户所有请假记录
        List<MhUserLeave> mhUserLeaves = userLeaveService.queryLeave(userId, startDate, endDate);
        Map<LocalDate, MhUserLeave> leaveMap = mhUserLeaves.stream().collect(Collectors.toMap(MhUserLeave::getLeaveDate, leave -> leave));
        Set<LocalDate> leaveDates = mhUserLeaves.stream().map(MhUserLeave::getLeaveDate).collect(Collectors.toSet());

        long between = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> allDay = Stream.iterate(startDate, localDate -> localDate.plusDays(1)).limit(between + 1).collect(toList());
        UserHourListVO userHourListVO;
        LocalDate now = LocalDate.now();
        List<UserHourListVO> resultList = new ArrayList<>(allDay.size());
        for (LocalDate localDate : allDay) {
            userHourListVO = new UserHourListVO();
            userHourListVO.setDate(localDate);
            if (localDate.isAfter(now)) {
                if (FestivalsUtil.isAHoliday(localDate.toString())) {
                    userHourListVO.setStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
                    resultList.add(userHourListVO);
                    continue;
                }
                userHourListVO.setStatus(UserHourFillStatusEnum.NO_NEED.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            if (FestivalsUtil.isAHoliday(localDate.toString()) && !hourDates.contains(localDate)) {
                userHourListVO.setStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            if (hourDates.contains(localDate)) {
                MhUserHour userHour = hourMap.get(localDate);
                userHourListVO.setId(userHour.getId());
                userHourListVO.setCreateTime(userHour.getCreateTime());
                userHourListVO.setStatus(UserHourFillStatusEnum.FILLED.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            if (leaveDates.contains(localDate)) {
                MhUserLeave leave = leaveMap.get(localDate);
                userHourListVO.setId(leave.getId());
                int leaveType = UserHourFillStatusEnum.LEAVE.getStatus();
                if(LeaveTypeEnum.OFF_DUTY.getCode() == leave.getLeaveType()) {
                    leaveType = UserHourFillStatusEnum.OFF_DUTY.getStatus();
                }
                userHourListVO.setStatus(leaveType);
                resultList.add(userHourListVO);
                continue;
            }
            if (missDates.contains(localDate)) {
                MhUserHourMiss mhUserHourMiss = missMap.get(localDate);
                userHourListVO.setId(mhUserHourMiss.getId());
                userHourListVO.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            // 如果是当天
            if (now.compareTo(localDate) == 0) {
                List<UserProjectShortVO> vos = projectUserService.userProjects(userId, null, null);
                List<UserProjectShortVO> collect = vos.stream()
                        .filter(vo -> !Objects.equals(vo.getProjectStatus(), ProjectStatusEnum.COMPLETE.getCode()))
                        .collect(toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    userHourListVO.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
                    resultList.add(userHourListVO);
                    continue;
                }
            }
            // 其它情况  用户空挡期 没有参与任何项目
            userHourListVO.setStatus(UserHourFillStatusEnum.NO_NEED.getStatus());
            resultList.add(userHourListVO);
        }
        return resultList;
    }

    @Override
    public TableDataInfo missList(int pageNum, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        Long userId = SecurityUtils.getUserId();
        EntityWrapper<MhUserHourMiss> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq(Columns.MhUserHourMiss.userId, userId);
        int count = userHourMissService.selectCount(entityWrapper);
        if (count <= 0) {
            return dataInfo;
        }
        Page<MhUserHourMiss> page = new Page<>(pageNum, pageSize);
        Page<MhUserHourMiss> missPage = userHourMissService.selectPage(page, entityWrapper);
        List<MhUserHourMiss> records = missPage.getRecords();
        List<UserHourListVO> list = new ArrayList<>(records.size());
        UserHourListVO vo;
        for (MhUserHourMiss record : records) {
            vo = new UserHourListVO();
            vo.setId(record.getId())
                    .setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus())
                    .setDate(record.getMissDate());
            list.add(vo);
        }
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(count);
        dataInfo.setRows(list);
        return dataInfo;
    }

    @Override
    public List<UserHourDetailVO> queryDetail(Long hourId) {
        EntityWrapper<MhHourDetail> detailWrapper = new EntityWrapper<>();
        detailWrapper.eq(Columns.MhHourDetail.hourId, hourId);
        List<MhHourDetail> mhHourDetails = hourDetailService.selectList(detailWrapper);
        if (CollectionUtils.isEmpty(mhHourDetails)) {
            return Collections.emptyList();
        }
        UserHourDetailVO vo;
        List<UserHourDetailVO> voList = new ArrayList<>(mhHourDetails.size());
        for (MhHourDetail mhHourDetail : mhHourDetails) {
            vo = new UserHourDetailVO();
            BeanUtils.copyProperties(mhHourDetail, vo);
            MhProject mhProject = projectService.selectById(mhHourDetail.getProjectId());
            vo.setProjectName(mhProject.getProjectName());
            vo.setDaily(mhHourDetail.getDaily());
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 修改 mh_user_hour 用户工时填报表
     * 修改 mh_hour_detail 工时填报详情表
     * 修改 mh_project_hour 表中已用工时
     * @param userHourUpdateDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserHour(UserHourUpdateDTO userHourUpdateDTO) {
        List<UserHourUpdateDTO.ProjectHourDetail> projectHours = userHourUpdateDTO.getProjectHours();
        BigDecimal total = projectHours.stream()
                .map(UserHourUpdateDTO.ProjectHourDetail::getHour)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        MhUserHour userHour = this.selectById(userHourUpdateDTO.getHourId());
        // 工时总数判断
        if (total.compareTo(new BigDecimal("24")) > 0) {
            throw new ServiceException("工时总数不能超过24");
        }
        if (total.compareTo(AppConfig.WORK_TIME) > 0 && !AppConfig.OVERTIME_ALLOW) {
            throw new ServiceException("提交工时总数超过限定值");
        }
        // 如果总工时不相等更新工时填报表
        if (userHour.getTotalHour().compareTo(total) != 0) {
            MhUserHour newUserHour = new MhUserHour();
            newUserHour.setTotalHour(total)
                    .setId(userHour.getId());
            this.updateById(newUserHour);
        }
        for (UserHourUpdateDTO.ProjectHourDetail projectHour : projectHours) {
            MhHourDetail mhHourDetail = hourDetailService.selectById(projectHour.getDetailId());
            // 如果更改了数值
            if (mhHourDetail.getUseHour().compareTo(projectHour.getHour()) != 0) {
                BigDecimal subtract = projectHour.getHour().subtract(mhHourDetail.getUseHour());
                // 更新总工时
                projectHourService.addTotalHour(projectHour.getProjectId(), subtract);
                // 更新详情
                mhHourDetail.setUseHour(projectHour.getHour());
                hourDetailService.updateById(mhHourDetail);
            }
            // 如果修改了日报内容
            if (!Objects.equals(mhHourDetail.getDaily(), projectHour.getDaily())) {
                mhHourDetail.setDaily(projectHour.getDaily());
                hourDetailService.updateById(mhHourDetail);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public List<UserHourStatVO> getMyHourStat(LocalDate localDate) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        SysUser user = loginUser.getUser();
        String nickName = user.getNickName();
        SysPost post = postService.getByUserId(userId);
        String postName = post.getPostName();
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        EntityWrapper<MhUserHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        // 填报集合
        List<MhUserHour> mhUserHours = baseMapper.selectList(wrapper);
        Map<LocalDate, List<MhUserHour>> fillMap = mhUserHours.stream().collect(groupingBy(MhUserHour::getFillDate));
        // 缺报集合
        List<MhUserHourMiss> userHourMisses = this.queryMiss(userId, first, last);
        Map<LocalDate, List<MhUserHourMiss>> missMap = userHourMisses.stream().collect(groupingBy(MhUserHourMiss::getMissDate));
        // 请假集合
        List<MhUserLeave> mhUserLeaves = this.queryLeaveNum(userId, first, last);
        Map<LocalDate, List<MhUserLeave>> leaveMap = mhUserLeaves.stream().collect(groupingBy(MhUserLeave::getLeaveDate));
        // 所有日期集合
        List<LocalDate> listDate = FestivalsUtil.interval(first, last);
        List<UserHourStatVO> list = new ArrayList<>(listDate.size());
        UserHourStatVO vo;
        LocalDate now = LocalDate.now();
        for (LocalDate date : listDate) {
            vo = new UserHourStatVO();
            vo.setDate(date)
                    .setNickName(nickName)
                    .setPostName(postName);
            if (fillMap.containsKey(date)) {
                MhUserHour userHour = fillMap.get(date).get(0);
                EntityWrapper<MhHourDetail> hourWrapper = new EntityWrapper<>();
                hourWrapper.eq(Columns.MhHourDetail.hourId, userHour.getId());
                List<MhHourDetail> mhHourDetails = hourDetailService.selectList(hourWrapper);
                List<UserHourStatVO.ProjectHour> projectHours = new ArrayList<>(mhHourDetails.size());
                UserHourStatVO.ProjectHour projectHour;
                for (MhHourDetail mhHourDetail : mhHourDetails) {
                    projectHour = new UserHourStatVO.ProjectHour();
                    projectHour.setProjectId(mhHourDetail.getProjectId())
                            .setProjectName(projectService.selectById(mhHourDetail.getProjectId()).getProjectName())
                            .setDaily(mhHourDetail.getDaily())
                            .setUseHour(mhHourDetail.getUseHour());
                    projectHours.add(projectHour);
                }
                vo.setStatus(UserHourFillStatusEnum.FILLED.getStatus())
                        .setId(userHour.getId())
                        .setFillTime(userHour.getCreateTime())
                        .setProjectHours(projectHours);
                list.add(vo);
                continue;
            }
            if (!FestivalsUtil.isItAHoliday(date)) {
                vo.setStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
                list.add(vo);
                continue;
            }
            if (missMap.containsKey(date)) {
                MhUserHourMiss miss = missMap.get(date).get(0);
                vo.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus())
                        .setId(miss.getId());
                list.add(vo);
                continue;
            }
            if (leaveMap.containsKey(date)) {
                MhUserLeave leave = leaveMap.get(date).get(0);
                int status = UserHourFillStatusEnum.getCodeByLeaveType(leave.getLeaveType());
                vo.setStatus(status)
                        .setId(leave.getId());
                list.add(vo);
                continue;
            }
            // 如果是当天
            if (now.compareTo(date) == 0) {
                List<UserProjectShortVO> projects = projectUserService.queryMyProject(userId, new Date());
                List<UserProjectShortVO> collect = projects.stream()
                        .filter(project -> !Objects.equals(project.getProjectStatus(), ProjectStatusEnum.COMPLETE.getCode()))
                        .collect(toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    vo.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
                    list.add(vo);
                    continue;
                }
            }
            vo.setStatus(UserHourFillStatusEnum.NO_NEED.getStatus());
            list.add(vo);
        }
        return list;
    }

    private List<MhUserHour> queryUserHourInfo(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhUserHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        return baseMapper.selectList(wrapper);
    }

    private List<MhUserHourMiss> queryMiss(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhUserHourMiss> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHourMiss.missDate, first, last);
        return userHourMissService.selectList(wrapper);
    }

    private List<MhUserLeave> queryLeaveNum(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhUserLeave> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserLeave.leaveDate, first, last);
        return userLeaveService.selectList(wrapper);
    }

    @Override
    public List<UserHourProjectStat> queryUserHourProjectStat(LocalDate localDate) {
        Long userId = SecurityUtils.getUserId();
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        List<MhHourDetail> mhHourDetails = this.queryUserHourDetailInfo(userId, first, last);
        // 项目工时详情
        Map<Long, List<MhHourDetail>> collect = mhHourDetails.stream()
                .collect(groupingBy(MhHourDetail::getProjectId));
        List<UserHourProjectStat> projectHourList = new ArrayList<>();
        collect.forEach((projectId, list) -> {
            UserHourProjectStat projectHour = new UserHourProjectStat();
            projectHour.setProjectId(projectId);
            MhProject mhProject = projectService.selectById(projectId);
            projectHour.setProjectName(mhProject.getProjectName());
            BigDecimal totalUse = list.stream().map(MhHourDetail::getUseHour).reduce(BigDecimal.ZERO, BigDecimal::add);
            projectHour.setUseHour(totalUse);
            projectHourList.add(projectHour);
        });
        return projectHourList;
    }

    private List<MhHourDetail> queryUserHourDetailInfo(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        return hourDetailService.selectList(wrapper);
    }

    @Override
    public UserHourExportVO userHourExport(LocalDate localDate, Long projectId) {
        Long userId = SecurityUtils.getUserId();
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .eq(Columns.MhHourDetail.userId, userId)
                .and()
                .between(Columns.MhHourDetail.fillDate, first, last);
        // 填报集合
        List<MhHourDetail> mhHourDetails = hourDetailService.selectList(wrapper);
        Map<LocalDate, List<MhHourDetail>> fillMap = mhHourDetails.stream().collect(groupingBy(MhHourDetail::getFillDate));
        // 缺报集合
        List<MhUserHourMiss> userHourMisses = this.queryMiss(userId, first, last);
        Set<LocalDate> missSet = userHourMisses.stream().map(MhUserHourMiss::getMissDate).collect(Collectors.toSet());
        // 请假集合
        List<MhUserLeave> mhUserLeaves = this.queryLeaveNum(userId, first, last);
        Map<LocalDate, List<MhUserLeave>> leaveMap = mhUserLeaves.stream().collect(groupingBy(MhUserLeave::getLeaveDate));
        // 所有日期集合
        List<LocalDate> listDate = FestivalsUtil.interval(first, last);
        List<UserHourExportVO.FillInfo> list = new ArrayList<>(listDate.size());
        UserHourExportVO.FillInfo fillInfo;
        for (LocalDate date : listDate) {
            fillInfo = new UserHourExportVO.FillInfo();
            fillInfo.setDate(date);
            if (fillMap.containsKey(date)) {
                MhHourDetail mhHourDetail = fillMap.get(date).get(0);
                fillInfo.setUseHour(mhHourDetail.getUseHour())
                        .setStatus(UserHourFillStatusEnum.FILLED.getStatus())
                        .setDaily(mhHourDetail.getDaily())
                        .setFillTime(mhHourDetail.getCreateTime());
                list.add(fillInfo);
                continue;
            }
            if (!FestivalsUtil.isItAHoliday(date)) {
                fillInfo.setStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
                list.add(fillInfo);
                continue;
            }
            if (missSet.contains(date)) {
                fillInfo.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
                list.add(fillInfo);
                continue;
            }
            if (leaveMap.containsKey(date)) {
                int leaveType = leaveMap.get(date).get(0).getLeaveType();
                int status = UserHourFillStatusEnum.getCodeByLeaveType(leaveType);
                fillInfo.setStatus(status);
                list.add(fillInfo);
                continue;
            }
            // 如果是当天
            if (LocalDate.now().compareTo(date) == 0) {
                List<UserProjectShortVO> projects = projectUserService.queryMyProject(userId, new Date());
                List<UserProjectShortVO> collect = projects.stream()
                        .filter(project -> !Objects.equals(project.getProjectStatus(), ProjectStatusEnum.COMPLETE.getCode()))
                        .collect(toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    fillInfo.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
                    list.add(fillInfo);
                    continue;
                }
            }
            fillInfo.setStatus(UserHourFillStatusEnum.NO_NEED.getStatus());
            list.add(fillInfo);
        }
        SysPost post = postService.getByUserId(userId);
        MhProject mhProject = projectService.selectById(projectId);
        String nickName = SecurityUtils.getLoginUser().getUser().getNickName();
        UserHourExportVO hourExportVO = new UserHourExportVO();
        hourExportVO.setProjectId(projectId)
                .setProjectName(mhProject.getProjectName())
                .setPostName(post.getPostName())
                .setNickName(nickName)
                .setFillInfos(list);
        return hourExportVO;
    }

    @Override
    public List<UserHourCalendarVO> queryUserHourCalendar(LocalDate localDate) {
        Long userId = SecurityUtils.getUserId();
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        EntityWrapper<MhUserHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        // 填报集合
        List<MhUserHour> mhUserHours = baseMapper.selectList(wrapper);
        Map<LocalDate, List<MhUserHour>> fillMap = mhUserHours.stream().collect(groupingBy(MhUserHour::getFillDate));
        // 缺报集合
        List<MhUserHourMiss> userHourMisses = this.queryMiss(userId, first, last);
        Map<LocalDate, List<MhUserHourMiss>> missMap = userHourMisses.stream().collect(groupingBy(MhUserHourMiss::getMissDate));
        // 请假集合
        List<MhUserLeave> mhUserLeaves = this.queryLeaveNum(userId, first, last);
        Map<LocalDate, List<MhUserLeave>> leaveMap = mhUserLeaves.stream().collect(groupingBy(MhUserLeave::getLeaveDate));
        List<LocalDate> listDate = FestivalsUtil.interval(first, last);
        List<UserHourCalendarVO> list = new ArrayList<>(listDate.size());
        UserHourCalendarVO vo;
        LocalDate now = LocalDate.now();
        for (LocalDate date : listDate) {
            vo = new UserHourCalendarVO();
            vo.setDate(date);
            if (fillMap.containsKey(date)) {
                BigDecimal hour = fillMap.get(date).stream().map(MhUserHour::getTotalHour)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                vo.setStatus(UserHourFillStatusEnum.FILLED.getStatus())
                        .setHour(hour)
                        .setId(fillMap.get(date).get(0).getId());
                list.add(vo);
                continue;
            }
            if (!FestivalsUtil.isItAHoliday(date)) {
                vo.setStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
                list.add(vo);
                continue;
            }
            if (missMap.containsKey(date)) {
                vo.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus())
                        .setId(missMap.get(date).get(0).getId());
                list.add(vo);
                continue;
            }
            if (leaveMap.containsKey(date)) {
                MhUserLeave leave = leaveMap.get(date).get(0);
                int leaveType = leave.getLeaveType();
                int status = UserHourFillStatusEnum.getCodeByLeaveType(leaveType);
                vo.setStatus(status)
                        .setId(leave.getId());
                list.add(vo);
                continue;
            }
            // 如果是当天
            if (now.compareTo(date) == 0) {
                List<UserProjectShortVO> projects = projectUserService.queryMyProject(userId, new Date());
                List<UserProjectShortVO> collect = projects.stream()
                        .filter(project -> !Objects.equals(project.getProjectStatus(), ProjectStatusEnum.COMPLETE.getCode()))
                        .collect(toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    vo.setStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
                    list.add(vo);
                    continue;
                }
            }
            vo.setStatus(UserHourFillStatusEnum.NO_NEED.getStatus());
            list.add(vo);
        }
        return list;
    }

    @Override
    public TableDataInfo queryMyHourStatDetail(LocalDate localDate, int pageNum, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        Long userId = SecurityUtils.getUserId();
        Date firstJoinTime = projectUserService.getUserFirstJoinTime(userId);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        // 如果当前用户没有加入项目 或者 加入项目查询范围内用户还没有加入项目
        if (Objects.isNull(firstJoinTime) || endDate.isBefore(DateUtils.date2LocalDate(firstJoinTime))) {
            return dataInfo;
        }
        if (startDate.isBefore(DateUtils.date2LocalDate(firstJoinTime))) {
            startDate = DateUtils.date2LocalDate(firstJoinTime);
        }
        EntityWrapper<MhHourDetail> detailWrapper = new EntityWrapper<>();
        detailWrapper.eq(Columns.MhHourDetail.userId, userId)
                .and()
                .between(Columns.MhHourDetail.fillDate, startDate, endDate);
        int count = hourDetailService.selectCount(detailWrapper);
        if (count <= 0) {
          return dataInfo;
        }
        Page<MhHourDetail> page = new Page<>(pageNum, pageSize);
        Page<MhHourDetail> detailPage = hourDetailService.selectPage(page, detailWrapper);
        List<MhHourDetail> records = detailPage.getRecords();
        List<UserHourStatDetailVO> list = new ArrayList<>(records.size());
        MhProject mhProject;
        UserHourStatDetailVO vo;
        SysPost post = postService.getByUserId(userId);
        String nickName = SecurityUtils.getLoginUser().getUser().getNickName();
        for (MhHourDetail record : records) {
            vo = new UserHourStatDetailVO();
            mhProject = projectService.selectById(record.getProjectId());
            vo.setId(record.getId())
                    .setProjectId(record.getProjectId())
                    .setEveryday(record.getEveryday())
                    .setFillDate(record.getFillDate())
                    .setProjectStatus(ProjectStatusEnum.getDescByCode(record.getProjectStatus()))
                    .setUseHour(record.getUseHour())
                    .setCreateTime(record.getCreateTime())
                    .setPostName(post.getPostName())
                    .setNickName(nickName)
                    .setProjectName(mhProject.getProjectName());
            list.add(vo);
        }
        dataInfo.setTotal(count);
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setRows(list);
        return dataInfo;
    }

    @Override
    public List<MhHourDetail> queryFillByProject(Long projectId, LocalDate date) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .eq(Columns.MhHourDetail.fillDate, date);
        return hourDetailService.selectList(wrapper);
    }

    @Override
    public List<MhHourDetail> queryProjectUserHour(Long projectId, LocalDate date) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId);
        if (!Objects.isNull(date)) {
            wrapper.eq(Columns.MhHourDetail.fillDate, date);
        }
        return hourDetailService.selectList(wrapper);
    }

    @Override
    public List<MhHourDetail> queryProjectUserHourMonth(Long projectId, String yearMonth) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId);
        if (StringUtils.isNotBlank(yearMonth)) {
            wrapper.like(Columns.MhHourDetail.fillDate, yearMonth, SqlLike.RIGHT);
        }
        return hourDetailService.selectList(wrapper);
    }

    @Override
    public int countProjectMonthUserDetail(ProjectHourStatMonthDetailDTO monthDetailDTO) {
        return baseMapper.countProjectMonthUserDetail(monthDetailDTO);
    }

    @Override
    public List<ProjectHourStatMonthDetail> queryProjectMonthUserDetail(ProjectHourStatMonthDetailDTO monthDetailDTO, int pageStart, int pageSize) {
        return baseMapper.queryProjectMonthUserDetail(monthDetailDTO, pageStart, pageSize);
    }

    @Override
    public MhUserHour getByUserId(Long userId, LocalDate localDate) {
        MhUserHour userHour = new MhUserHour();
        userHour.setUserId(userId);
        userHour.setFillDate(localDate);
        return baseMapper.selectOne(userHour);
    }

    @Override
    public int getMonthFill(Long userId, LocalDate localDate) {
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        // 上报天数
        List<MhUserHour> mhUserHours = this.queryUserHourInfo(userId, first, last);
        return mhUserHours.size();
    }

    @Override
    public int getMonthMissFill(Long userId, LocalDate localDate) {
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        List<MhUserHourMiss> hourMisses = this.queryMiss(userId, first, last);
        return hourMisses.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean userLeave(LocalDate leaveDate, int leaveType) {
        boolean result = userLeaveService.create(leaveDate, leaveType);
        userHourMissService.deleteMiss(leaveDate);
        return result;
    }

    @Override
    public boolean unLeave(Long id) {
        Long userId = SecurityUtils.getUserId();
        MhUserLeave leave = userLeaveService.selectById(id);
        if (!Objects.equals(userId, leave.getUserId())) {
            throw new ServiceException("只能本人取消请假/调休");
        }
        return userLeaveService.unLeave(id);
    }

    @Override
    public List<MhHourDetail> queryProjectFillDetail(Long projectId, LocalDate localDate) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .eq(Columns.MhHourDetail.fillDate, localDate);
        return hourDetailService.selectList(wrapper);
    }

    @Override
    public List<UserProjectShortVO> queryMyMissProject(Long missId) {
        return userHourMissService.queryMyMissProject(missId);
    }
}
