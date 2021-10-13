package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.exception.ServiceException;
import com.oaker.common.utils.DateUtils;
import com.oaker.common.utils.FestivalsUtil;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.MhUserHourMiss;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourStatDestailVO;
import com.oaker.hours.doman.vo.UserHourStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.mapper.MhUserHourMapper;
import com.oaker.hours.service.MhUserHourService;
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
    private ProjectHourServiceImpl projectHourService;

    @Resource
    private MhHourDetailServiceImpl hourDetailService;

    @Resource
    private MhUserHourMissServiceImpl userHourMissService;

    @Resource
    private ProjectUserServiceImpl projectUserService;

    @Resource
    private ProjectServiceImpl projectService;

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

        LocalDate fillDate = userHourSaveDTO.getDate();
        Date createTime = new Date();
        MhUserHour userHour = new MhUserHour();
        userHour.setUserId(userId)
                .setFillDate(fillDate)
                .setTotalHour(total)
                .setCreateTime(createTime);
        // 1. 写入mh_user_hour 用户工时填报表
        baseMapper.insert(userHour);
        MhHourDetail mhHourDetail;
        // 2. 写入mh_hour_detail工时填报详情表
        List<MhHourDetail> detailList = new ArrayList<>(projectHours.size());
        for (UserHourSaveDTO.ProjectHour projectHour : projectHours) {
            mhHourDetail = new MhHourDetail();
            mhHourDetail.setProjectId(projectHour.getProjectId())
                    .setUserId(userId)
                    .setUseHour(projectHour.getHour())
                    .setFillDate(fillDate)
                    .setHourId(userHour.getId())
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

        long between = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> allDay = Stream.iterate(startDate, localDate -> localDate.plusDays(1)).limit(between + 1).collect(toList());
        UserHourListVO userHourListVO;
        LocalDate now = LocalDate.now();
        List<UserHourListVO> resultList = new ArrayList<>(allDay.size());
        for (LocalDate localDate : allDay) {
            userHourListVO = new UserHourListVO();
            userHourListVO.setDate(localDate);
            if (localDate.isAfter(now)) {
                userHourListVO.setStatus(UserHourListVO.StatusEnum.NO_NEED.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            if (FestivalsUtil.isAHoliday(localDate.toString()) && !hourDates.contains(localDate)) {
                userHourListVO.setStatus(UserHourListVO.StatusEnum.FESTIVAL.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            if (hourDates.contains(localDate)) {
                MhUserHour userHour = hourMap.get(localDate);
                userHourListVO.setId(userHour.getId());
                userHourListVO.setCreateTime(userHour.getCreateTime());
                userHourListVO.setStatus(UserHourListVO.StatusEnum.FILLED.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            if (missDates.contains(localDate)) {
                MhUserHourMiss mhUserHourMiss = missMap.get(localDate);
                userHourListVO.setId(mhUserHourMiss.getId());
                userHourListVO.setStatus(UserHourListVO.StatusEnum.NOT_FILLED.getStatus());
                resultList.add(userHourListVO);
                continue;
            }
            // 如果是当天
            if (now.compareTo(localDate) == 0) {
                List<UserProjectShortVO> vos = projectUserService.queryMyProject(userId, new Date());
                if (CollectionUtils.isNotEmpty(vos)) {
                    userHourListVO.setStatus(UserHourListVO.StatusEnum.NOT_FILLED.getStatus());
                    resultList.add(userHourListVO);
                    continue;
                }
            }
            // 其它情况  用户空挡期 没有参与任何项目
            userHourListVO.setStatus(UserHourListVO.StatusEnum.NO_NEED.getStatus());
            resultList.add(userHourListVO);
        }
        return resultList;
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
        }
        return Boolean.TRUE;
    }

    @Override
    public UserHourStatVO getMyHourStat(LocalDate localDate) {
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        long mustFillNum = FestivalsUtil.workingDays(first, last);
        UserHourStatVO userHourStatVO = new UserHourStatVO();
        // 应报天数
        userHourStatVO.setMustFillNum((int)mustFillNum);
        Long userId = SecurityUtils.getUserId();
        // 上报天数
        List<MhUserHour> mhUserHours = this.queryUserHourInfo(userId, first, last);
        userHourStatVO.setFillNum(mhUserHours.size());
        List<MhHourDetail> mhHourDetails = this.queryUserHourDetailInfo(userId, first, last);
        // 总工时
        BigDecimal totalHour = mhHourDetails.stream()
                .map(MhHourDetail::getUseHour)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        userHourStatVO.setTotalHour(totalHour);
        // 项目工时详情
        Map<Long, List<MhHourDetail>> collect = mhHourDetails.stream()
                .collect(groupingBy(MhHourDetail::getProjectId));
        List<UserHourStatVO.ProjectHour> projectHourList = new ArrayList<>();
        collect.forEach((projectId, list) -> {
            UserHourStatVO.ProjectHour projectHour = new UserHourStatVO.ProjectHour();
            projectHour.setProjectId(projectId);
            MhProject mhProject = projectService.selectById(projectId);
            projectHour.setProjectName(mhProject.getProjectName());
            BigDecimal totalUse = list.stream().map(MhHourDetail::getUseHour).reduce(BigDecimal.ZERO, BigDecimal::add);
            projectHour.setUseHour(totalUse);
            projectHourList.add(projectHour);
        });
        userHourStatVO.setProjectHours(projectHourList);
        // 缺报天数
        List<MhUserHourMiss> hourMisses = this.queryMiss(userId, first, last);
        userHourStatVO.setMissFillNum(hourMisses.size());
        return userHourStatVO;
    }

    private List<MhUserHour> queryUserHourInfo(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhUserHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        return baseMapper.selectList(wrapper);
    }

    private List<MhHourDetail> queryUserHourDetailInfo(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        return hourDetailService.selectList(wrapper);
    }

    private List<MhUserHourMiss> queryMiss(Long userId, LocalDate first, LocalDate last) {
        EntityWrapper<MhUserHourMiss> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.userId, userId)
                .and()
                .between(Columns.MhUserHourMiss.missDate, first, last);
        return userHourMissService.selectList(wrapper);
    }

    @Override
    public List<UserHourStatDestailVO> queryMyHourStatDetail(LocalDate localDate) {
        Long userId = SecurityUtils.getUserId();
        Date firstJoinTime = projectUserService.getUserFirstJoinTime(userId);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        // 如果当前用户没有加入项目 或者 加入项目查询范围内用户还没有加入项目
        if (Objects.isNull(firstJoinTime) || endDate.isBefore(DateUtils.date2LocalDate(firstJoinTime))) {
            return Collections.emptyList();
        }
        if (startDate.isAfter(DateUtils.date2LocalDate(firstJoinTime))) {
            startDate = DateUtils.date2LocalDate(firstJoinTime);
        }
        // 查询用户所有缺报记录
        List<MhUserHourMiss> mhUserHourMisses = userHourMissService.queryUserList(userId, startDate, endDate);
        Set<LocalDate> missDates = mhUserHourMisses.stream().map(MhUserHourMiss::getMissDate).collect(Collectors.toSet());

        List<LocalDate> dateList = FestivalsUtil.interval(startDate, endDate);
        UserHourStatDestailVO detailVo;
        List<UserHourStatDestailVO> resultList = new ArrayList<>(dateList.size());
        for (LocalDate date : dateList) {
            detailVo = new UserHourStatDestailVO();
            detailVo.setDate(date);
            // 如果是节假日不需要填报
            if (FestivalsUtil.isAHoliday(localDate.toString())) {
                detailVo.setStatus(UserHourListVO.StatusEnum.NO_NEED.getStatus());
                resultList.add(detailVo);
                continue;
            }
            // 如果存在缺报记录
            if (missDates.contains(date)) {
                detailVo.setStatus(UserHourListVO.StatusEnum.NOT_FILLED.getStatus());
                resultList.add(detailVo);
                continue;
            }
            // 如果存在填报记录
            List<MhHourDetail> mhHourDetails = this.queryUserHourDetail(userId, date);
            if (CollectionUtils.isNotEmpty(mhHourDetails)) {
                List<UserHourStatVO.ProjectHour> projectHours = new ArrayList<>(mhHourDetails.size());
                for (MhHourDetail mhHourDetail : mhHourDetails) {
                    UserHourStatVO.ProjectHour projectHour = new UserHourStatVO.ProjectHour();
                    projectHour.setProjectId(mhHourDetail.getProjectId());
                    projectHour.setUseHour(mhHourDetail.getUseHour());
                    MhProject mhProject = projectService.selectById(mhHourDetail.getProjectId());
                    projectHour.setProjectName(mhProject.getProjectName());
                    projectHours.add(projectHour);
                    detailVo.setProjectHours(projectHours);
                }
                detailVo.setStatus(UserHourListVO.StatusEnum.FILLED.getStatus());
                resultList.add(detailVo);
                continue;
            }
            // 其它情况  可能用户空挡期， 没有参与项目
            detailVo.setStatus(UserHourListVO.StatusEnum.NO_NEED.getStatus());
            resultList.add(detailVo);
        }
        return resultList;
    }

    private List<MhHourDetail> queryUserHourDetail(Long userId, LocalDate date) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.userId, userId)
                .and()
                .eq(Columns.MhHourDetail.fillDate, date);
        return hourDetailService.selectList(wrapper);
    }

    @Override
    public int countFillByProject(Long projectId, LocalDate date) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .eq(Columns.MhHourDetail.fillDate, date);
        return hourDetailService.selectCount(wrapper);
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
            wrapper.like(false, Columns.MhHourDetail.fillDate, yearMonth + "%");
        }
        return hourDetailService.selectList(wrapper);
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
}
