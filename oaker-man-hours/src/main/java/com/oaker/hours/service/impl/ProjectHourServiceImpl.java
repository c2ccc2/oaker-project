package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.utils.FestivalsUtil;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.MhUserHourMiss;
import com.oaker.hours.doman.entity.MhUserHourMissDetail;
import com.oaker.hours.doman.entity.MhUserLeave;
import com.oaker.hours.doman.entity.MhUserLeaveDetail;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.entity.ProjectUser;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.OverallHourDetailVO;
import com.oaker.hours.doman.vo.OverallHourListVO;
import com.oaker.hours.doman.vo.ProjectFillCalendar;
import com.oaker.hours.doman.vo.ProjectFillCalendarDetail;
import com.oaker.hours.doman.vo.ProjectHourFillStatListVO;
import com.oaker.hours.doman.vo.ProjectHourStatListVO;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.ProjectHourStatUser;
import com.oaker.hours.doman.vo.ProjectUserFillVO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.enums.ProjectStatusEnum;
import com.oaker.hours.enums.UserHourFillStatusEnum;
import com.oaker.hours.mapper.ProjectHourMapper;
import com.oaker.hours.service.ProjectHourService;
import com.oaker.system.domain.SysPost;
import com.oaker.system.service.impl.SysPostServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 13:47
 */
@Service
public class ProjectHourServiceImpl extends ServiceImpl<ProjectHourMapper, ProjectHour> implements ProjectHourService {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private SysPostServiceImpl postService;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private MhUserHourServiceImpl userHourService;

    @Resource
    private MhHourDetailServiceImpl hourDetailService;

    @Resource
    private MhUserLeaveServiceImpl userLeaveService;

    @Resource
    private ProjectUserServiceImpl projectUserService;

    @Resource
    private MhUserHourMissServiceImpl userHourMissService;



    @Override
    public boolean addTotalHour(Long projectId, BigDecimal useHour) {
        return baseMapper.addUseHour(projectId, useHour) > 0;
    }

    @Override
    public TableDataInfo queryProjectHourStat(String projectStatus, int pageNum, int pageSize) {
        TableDataInfo tableDataInfo = new TableDataInfo();
        EntityWrapper<MhProject> ety = new EntityWrapper<>();
        ety.eq(Columns.deleted, Boolean.FALSE);
        if (StringUtils.isNotBlank(projectStatus)) {
            ety.eq(Columns.Project.projectStatus, projectStatus);
        }
        int count = projectService.selectCount(ety);
        if (count <= 0) {
            tableDataInfo.setCode(HttpStatus.SUCCESS);
            tableDataInfo.setRows(Collections.emptyList());
            return tableDataInfo;
        }
        ety.orderDesc(Collections.singleton(Columns.Project.projectId));
        Page<MhProject>  page = new Page<>(pageNum, pageSize);
        Page<MhProject> mhProjectPage = projectService.selectPage(page, ety);
        List<MhProject> mhProjects = mhProjectPage.getRecords();
        ProjectHourStatListVO vo;
        ProjectHour projectHour;
        SysUserEntity sysUserEntity;
        LocalDate now = LocalDate.now();
        List<MhHourDetail> mhHourDetails;
        List<ProjectUser> projectUsers;
        List<ProjectUser> everydayUsers;
        List<ProjectHourStatListVO> list =  new ArrayList<>(mhProjects.size());
        for (MhProject mhProject : mhProjects) {
            Long projectId = mhProject.getProjectId();
            projectHour = this.selectById(projectId);
            Long projectManager = mhProject.getProjectManager();
            sysUserEntity = userService.selectById(projectManager);
            // 填报人员集合
            mhHourDetails = userHourService.queryFillByProject(projectId, now);
            // 全部参与项目人员集合
            projectUsers = projectUserService.queryUserByProjectId(projectId, null);
            // 每日上报组人员集合
            everydayUsers = projectUserService.queryUserByProjectId(projectId, true);
            // 每日上报组上报人员 = 每日上报组所有人员 ∩ 填报人员集合
            Set<Long> mhHourUserIds = mhHourDetails.stream().map(MhHourDetail::getUserId).collect(Collectors.toSet());
            Set<Long> everydayUserIds = everydayUsers.stream().map(ProjectUser::getUserId).collect(Collectors.toSet());
            mhHourUserIds.retainAll(everydayUserIds);

            String projectStatusName = ProjectStatusEnum.getDescByCode(mhProject.getProjectStatus());
            vo = new ProjectHourStatListVO();
            vo.setProjectId(mhProject.getProjectId())
                    .setProjectName(mhProject.getProjectName())
                    .setProjectManager(projectManager)
                    .setProjectManagerName(sysUserEntity.getNickName())
                    .setProjectStatus(projectStatusName)
                    .setTotalHour(projectHour.getManHour())
                    .setUseHour(projectHour.getUseHour())
                    .setTodayMustFill(everydayUserIds.size())
                    .setTodayMustFillNum(mhHourUserIds.size())
                    .setTotalFill(mhHourDetails.size())
                    .setTotalFillNum(projectUsers.size());
            list.add(vo);
        }
        tableDataInfo.setTotal(count);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setRows(list);
        return tableDataInfo;
    }

    @Override
    public List<ProjectHourFillStatListVO> queryProjectHourFillStat(Long projectId, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> localDates = FestivalsUtil.interval(startDate, endDate);
        List<ProjectHourFillStatListVO> list = new ArrayList<>(localDates.size());
        ProjectHourFillStatListVO vo;
        List<MhHourDetail> mhHourDetails;
        List<MhUserHourMissDetail> missDetails;
        for (LocalDate localDate : localDates) {
            vo = new ProjectHourFillStatListVO();
            mhHourDetails = userHourService.queryProjectFillDetail(projectId, localDate);
            missDetails = userHourMissService.queryProjectMiss(projectId, localDate);
            int everydayFill = mhHourDetails.stream()
                    .filter(MhHourDetail::getEveryday).collect(Collectors.toList())
                    .size();
            BigDecimal totalHour = mhHourDetails.stream()
                    .map(MhHourDetail::getUseHour).reduce(BigDecimal.ZERO, BigDecimal::add);
            vo.setStatDate(localDate)
                    .setEverydayFill(everydayFill)
                    .setEverydayMissNum(missDetails.size())
                    .setAllFill(mhHourDetails.size() + missDetails.size())
                    .setTotalHour(totalHour);
            list.add(vo);
        }
        return list;
    }

    @Override
    public ProjectUserFillVO queryFillDetail(Long projectId, LocalDate date) {
        ProjectUserFillVO result = new ProjectUserFillVO();
        MhProject mhProject = projectService.selectById(projectId);
        Date createTime = mhProject.getCreateTime();
        LocalDate startDate = createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 如果查询日期 项目还未创建则直接返回
        if (startDate.isAfter(date)) {
            return result;
        }
        // 填报
        List<MhHourDetail> mhHourDetails = userHourService.queryProjectUserHour(projectId, date);
        SysPost sysPost;
        SysUserEntity userEntity;
        ProjectUserFillVO.UserFill userFill;
        List<ProjectUserFillVO.UserFill> fills = new ArrayList<>(mhHourDetails.size());
        for (MhHourDetail mhHourDetail : mhHourDetails) {
            userFill = new ProjectUserFillVO.UserFill();
            userEntity = userService.selectById(mhHourDetail.getUserId());
            sysPost = postService.getByUserId(mhHourDetail.getUserId());
            userFill.setUserId(mhHourDetail.getUserId())
                    .setUseHour(mhHourDetail.getUseHour())
                    .setCreateTime(mhHourDetail.getCreateTime())
                    .setNickName(userEntity.getNickName())
                    .setEveryday(mhHourDetail.getEveryday())
                    .setFillStatus(UserHourFillStatusEnum.FILLED.getStatus())
                    .setPostName(sysPost.getPostName());
            fills.add(userFill);
        }
        result.setFills(fills);
        // 缺报
        List<MhUserHourMissDetail> missDetails = userHourMissService.queryProjectMiss(projectId, date);
        ProjectUserFillVO.UserFill missFill;
        List<ProjectUserFillVO.UserFill> unFills = new ArrayList<>(missDetails.size());
        for (MhUserHourMissDetail missDetail : missDetails) {
            missFill = new ProjectUserFillVO.UserFill();
            userEntity = userService.selectById(missDetail.getUserId());
            sysPost = postService.getByUserId(missDetail.getUserId());
            missFill.setUserId(missDetail.getUserId())
                    .setFillStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus())
                    .setEveryday(Boolean.TRUE)
                    .setNickName(userEntity.getNickName())
                    .setPostName(sysPost.getPostName());
            unFills.add(missFill);
        }
        // 请假
        Set<Long> userIds = projectUserService.queryJoinUserIds(date);
        EntityWrapper<MhUserLeave> wrapper = new EntityWrapper<>();
        wrapper.in(Columns.MhUserLeave.userId, userIds)
                .and()
                .eq(Columns.MhUserLeave.leaveDate, date);
        List<MhUserLeave> mhUserLeaves = userLeaveService.selectList(wrapper);
        for (MhUserLeave mhUserLeaf : mhUserLeaves) {
            missFill = new ProjectUserFillVO.UserFill();
            userEntity = userService.selectById(mhUserLeaf.getUserId());
            sysPost = postService.getByUserId(mhUserLeaf.getUserId());
            missFill.setUserId(mhUserLeaf.getUserId())
                    .setFillStatus(UserHourFillStatusEnum.LEAVE.getStatus())
                    .setEveryday(Boolean.TRUE)
                    .setNickName(userEntity.getNickName())
                    .setPostName(sysPost.getPostName());
            unFills.add(missFill);
        }
        result.setUnFills(unFills);
        return result;
    }

    @Override
    public List<ProjectHourStatMonth> queryProjectHourMonthStat(Long projectId) {
        List<MhHourDetail> mhHourDetails = userHourService.queryProjectUserHour(projectId, null);
        if (CollectionUtils.isEmpty(mhHourDetails)) {
            return Collections.emptyList();
        }
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM");
        List<ProjectHourStatMonth> hourList = new ArrayList<>(mhHourDetails.size());
        mhHourDetails.forEach(mhHourDetail -> {
            ProjectHourStatMonth hourStatMonth = new ProjectHourStatMonth();
            hourStatMonth.setMonth(dtf3.format(mhHourDetail.getFillDate()));
            hourStatMonth.setUseHour(mhHourDetail.getUseHour());
            hourStatMonth.setUserId(mhHourDetail.getUserId());
            hourList.add(hourStatMonth);
        });
        Map<String, List<ProjectHourStatMonth>> listMap = hourList.stream()
                .collect(Collectors.groupingBy(ProjectHourStatMonth::getMonth));
        Set<Map.Entry<String, List<ProjectHourStatMonth>>> entrySet = listMap.entrySet();
        List<ProjectHourStatMonth> resultList = new ArrayList<>(entrySet.size());
        ProjectHourStatMonth projectHourStatMonth;
        for (Map.Entry<String, List<ProjectHourStatMonth>> entry : entrySet) {
            projectHourStatMonth = new ProjectHourStatMonth();
            List<ProjectHourStatMonth> hourStatMonths = entry.getValue();
            BigDecimal useHour = hourStatMonths.stream()
                    .map(ProjectHourStatMonth::getUseHour)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            List<Long> userIds = hourStatMonths.stream()
                    .map(ProjectHourStatMonth::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
            projectHourStatMonth.setMonth(entry.getKey())
                    .setUseHour(useHour)
                    .setUsers(userIds);
            resultList.add(projectHourStatMonth);
        }
        return resultList;
    }

    @Override
    public List<ProjectHourStatUser> queryProjectHourUserStat(Long projectId, String yearMonth) {
        List<MhHourDetail> mhHourDetails = userHourService.queryProjectUserHourMonth(projectId, yearMonth);
        if (CollectionUtils.isEmpty(mhHourDetails)) {
            return Collections.emptyList();
        }
        Map<Long, List<MhHourDetail>> userListMap = mhHourDetails.stream()
                .collect(Collectors.groupingBy(MhHourDetail::getUserId));
        List<ProjectHourStatUser> resultList = new ArrayList<>(userListMap.size());
        userListMap.forEach((userId, list) -> {
            ProjectHourStatUser statUser = new ProjectHourStatUser();
            BigDecimal useHour = list.stream()
                    .map(MhHourDetail::getUseHour)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            SysPost sysPost = postService.getByUserId(userId);
            SysUserEntity userEntity = userService.selectById(userId);
            statUser.setUserId(userId)
                    .setUseHour(useHour)
                    .setNickName(userEntity.getNickName())
                    .setPostName(sysPost.getPostName());
            resultList.add(statUser);
        });
        return resultList;
    }

    @Override
    public TableDataInfo queryProjectMonthUserDetail(ProjectHourStatMonthDetailDTO monthDetailDTO, int pageStart, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        int count = userHourService.countProjectMonthUserDetail(monthDetailDTO);
        if (count <= 0) {
            return dataInfo;
        }
        List<ProjectHourStatMonthDetail> details = userHourService.queryProjectMonthUserDetail(monthDetailDTO, pageStart, pageSize);
        details.forEach(vo -> {
            SysUserEntity sysUserEntity = userService.selectById(vo.getUserId());
            SysPost post = postService.getByUserId(vo.getUserId());
            vo.setNickName(sysUserEntity.getNickName())
                    .setPostName(post.getPostName());
        });
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(count);
        dataInfo.setRows(details);
        return dataInfo;
    }

    @Override
    public List<OverallHourListVO> queryOverallList(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dateList = FestivalsUtil.interval(startDate, endDate);
        List<OverallHourListVO> voList = new ArrayList<>(dateList.size());
        OverallHourListVO vo;
        for (LocalDate localDate : dateList) {
            OverallHourDetailVO.OverallHourStat overallHourStat = this.queryOverallDetailStat(localDate);
            vo = new OverallHourListVO();
            vo.setDate(localDate)
                    .setFillNum(overallHourStat.getFillNum())
                    .setUnFillNum(overallHourStat.getUnFillNum())
                    .setLeaveNum(overallHourStat.getLeaveNum())
                    .setTotalHour(overallHourStat.getTotalHour());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public TableDataInfo queryOverallDetail(LocalDate localDate, int pageNum, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(HttpStatus.SUCCESS);
        EntityWrapper<MhUserHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHour.fillDate, localDate);
        int count = userHourService.selectCount(wrapper);
        if (count <= 0) {
            return dataInfo;
        }
        Page<MhUserHour> mhUserHourPage = new Page<>();
        Page<MhUserHour> userHourPage = userHourService.selectPage(mhUserHourPage, wrapper);
        List<MhUserHour> userHours = userHourPage.getRecords();
        List<OverallHourDetailVO> list = new ArrayList<>(userHours.size());
        Long userId;
        SysPost sysPost;
        OverallHourDetailVO vo;
        List<UserHourDetailVO> detailVos;
        for (MhUserHour userHour : userHours) {
            vo = new OverallHourDetailVO();
            userId = userHour.getUserId();
            sysPost = postService.getByUserId(userId);
            detailVos = userHourService.queryDetail(userHour.getId());
            List<OverallHourDetailVO.ProjectDetail> projectDetails = new ArrayList<>(detailVos.size());
            detailVos.forEach(detailVO -> {
                OverallHourDetailVO.ProjectDetail detail = new OverallHourDetailVO.ProjectDetail();
                detail.setProjectId(detailVO.getProjectId())
                        .setUseHour(detailVO.getUseHour())
                        .setProjectName(projectService.selectById(detailVO.getProjectId()).getProjectName());
                projectDetails.add(detail);
            });
            vo.setUserId(userId)
                    .setNickName(userService.selectById(userId).getNickName())
                    .setPostId(sysPost.getPostId())
                    .setPostName(sysPost.getPostName())
                    .setTotalHour(userHour.getTotalHour())
                    .setFillTime(userHour.getCreateTime())
                    .setProjectDetails(projectDetails);
            list.add(vo);
        }
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(count);
        dataInfo.setRows(list);
        return dataInfo;
    }

    @Override
    public OverallHourDetailVO.OverallHourStat queryOverallDetailStat(LocalDate localDate) {
        OverallHourDetailVO.OverallHourStat vo = new OverallHourDetailVO.OverallHourStat();
        EntityWrapper<MhUserHour> userHourWrapper = new EntityWrapper<>();
        userHourWrapper.eq(Columns.MhUserHour.fillDate, localDate);
        List<MhUserHour> mhUserHours = userHourService.selectList(userHourWrapper);
        int missNum = this.countMissNum(localDate);
        int leaveNum = this.countLeaveNum(localDate);
        BigDecimal totalHour = mhUserHours.stream()
                .map(MhUserHour::getTotalHour)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        vo.setDate(localDate)
                .setFillNum(mhUserHours.size())
                .setUnFillNum(missNum)
                .setLeaveNum(leaveNum)
                .setTotalHour(totalHour);
        return vo;
    }

    private int countMissNum(LocalDate localDate) {
        EntityWrapper<MhUserHourMiss> missWrapper = new EntityWrapper<>();
        missWrapper.eq(Columns.MhUserHourMiss.missDate, localDate);
        return userHourMissService.selectCount(missWrapper);
    }

    private int countLeaveNum(LocalDate localDate) {
        EntityWrapper<MhUserLeave> leaveWrapper = new EntityWrapper<>();
        leaveWrapper.eq(Columns.MhUserLeave.leaveDate, localDate);
        return userLeaveService.selectCount(leaveWrapper);
    }

    @Override
    public TableDataInfo overallLeaveDetail(LocalDate localDate, int pageNum, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(HttpStatus.SUCCESS);
        int leaveNum = this.countLeaveNum(localDate);
        if (leaveNum <= 0) {
            return dataInfo;
        }
        EntityWrapper<MhUserLeave> leaveWrapper = new EntityWrapper<>();
        leaveWrapper.eq(Columns.MhUserLeave.leaveDate, localDate);
        Page<MhUserLeave> page = new Page<>(pageNum, pageSize);
        Page<MhUserLeave> leavePage = userLeaveService.selectPage(page, leaveWrapper);
        List<MhUserLeave> records = leavePage.getRecords();
        SysUserEntity sysUser;
        OverallHourDetailVO.UserVO vo;
        List<OverallHourDetailVO.UserVO> list = new ArrayList<>(records.size());
        for (MhUserLeave record : records) {
            vo = new OverallHourDetailVO.UserVO();
            Long userId = record.getUserId();
            sysUser = userService.selectById(userId);
            SysPost sysPost = postService.getByUserId(userId);
            vo.setUserId(userId)
                    .setNickName(sysUser.getNickName())
                    .setPostId(sysPost.getPostId())
                    .setPostName(sysPost.getPostName());
            list.add(vo);
        }
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(leaveNum);
        dataInfo.setRows(list);
        return dataInfo;
    }

    @Override
    public TableDataInfo overallMissDetail(LocalDate localDate, int pageNum, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(HttpStatus.SUCCESS);
        int missNum = this.countMissNum(localDate);
        if (missNum <= 0) {
            return dataInfo;
        }
        EntityWrapper<MhUserHourMiss> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMiss.missDate, localDate);
        Page<MhUserHourMiss> page = new Page<>(pageNum, pageSize);
        Page<MhUserHourMiss> leavePage = userHourMissService.selectPage(page, wrapper);
        List<MhUserHourMiss> records = leavePage.getRecords();
        List<OverallHourDetailVO.UserVO> list = new ArrayList<>(records.size());
        records.forEach(record -> {
            OverallHourDetailVO.UserVO vo = new OverallHourDetailVO.UserVO();
            Long userId = record.getUserId();
            SysUserEntity sysUser = userService.selectById(userId);
            SysPost sysPost = postService.getByUserId(userId);
            vo.setUserId(userId)
                    .setNickName(sysUser.getNickName())
                    .setPostId(sysPost.getPostId())
                    .setPostName(sysPost.getPostName());
            list.add(vo);
        });
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(missNum);
        dataInfo.setRows(list);
        return dataInfo;
    }

    @Override
    public List<ProjectFillCalendar> queryProjectFillCalendar(LocalDate localDate, Long projectId) {
        LocalDate first = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfMonth());
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .between(Columns.MhUserHour.fillDate, first, last);
        // 填报集合
        List<MhHourDetail> mhHourDetails = hourDetailService.selectList(wrapper);
        Map<LocalDate, List<MhHourDetail>> fillMap = mhHourDetails.stream().collect(groupingBy(MhHourDetail::getFillDate));
        List<LocalDate> dates = FestivalsUtil.interval(first, last);
        List<ProjectFillCalendar> list = new ArrayList<>(dates.size());
        ProjectFillCalendar calendar;
        List<MhHourDetail> hourDetails;
        for (LocalDate date : dates) {
            calendar = new ProjectFillCalendar();
            if (!FestivalsUtil.isItAHoliday(date)) {
                calendar.setFestivals(true);
            }
            if (fillMap.containsKey(date)) {
                hourDetails = fillMap.get(date);
                List<Long> userIds = hourDetails.stream().map(MhHourDetail::getUserId).collect(Collectors.toList());
                BigDecimal hours = hourDetails.stream().map(MhHourDetail::getUseHour).reduce(BigDecimal.ZERO, BigDecimal::add);
                calendar.setDate(date)
                        .setProjectId(projectId)
                        .setUsers(userIds)
                        .setHours(hours);
            }
            list.add(calendar);
        }
        return list;
    }

    @Override
    public ProjectFillCalendarDetail getProjectFillCalendarDetail(LocalDate localDate, Long projectId) {
        ProjectFillCalendarDetail detail = new ProjectFillCalendarDetail();
        MhProject mhProject = projectService.selectById(projectId);
        detail.setProjectName(mhProject.getProjectName());
        if (!FestivalsUtil.isItAHoliday(localDate)) {
            detail.setFestivals(true);
        }
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .eq(Columns.MhUserHour.fillDate, localDate);
        // 填报集合
        List<MhHourDetail> mhHourDetails = hourDetailService.selectList(wrapper);
        List<ProjectFillCalendarDetail.UserFill> userFills = new ArrayList<>(mhHourDetails.size());
        ProjectFillCalendarDetail.UserFill userFill;
        SysUserEntity userEntity;
        SysPost sysPost;
        for (MhHourDetail mhHourDetail : mhHourDetails) {
            userFill = new ProjectFillCalendarDetail.UserFill();
            userEntity = userService.selectById(mhHourDetail.getUserId());
            sysPost = postService.getByUserId(mhHourDetail.getUserId());
            userFill.setUserId(mhHourDetail.getUserId())
                    .setUseHour(mhHourDetail.getUseHour())
                    .setDaily(mhHourDetail.getDaily())
                    .setEveryday(mhHourDetail.getEveryday())
                    .setFillStatus(UserHourFillStatusEnum.FILLED.getStatus())
                    .setNickName(userEntity.getNickName())
                    .setPostName(sysPost.getPostName());
            userFills.add(userFill);
        }
        // 请假集合
        List<MhUserLeaveDetail> leaveDetails = userLeaveService.queryByProjectId(projectId, localDate);
        if (!CollectionUtils.isEmpty(leaveDetails)) {
            for (MhUserLeaveDetail leaveDetail : leaveDetails) {
                userFill = new ProjectFillCalendarDetail.UserFill();
                userEntity = userService.selectById(leaveDetail.getUserId());
                sysPost = postService.getByUserId(leaveDetail.getUserId());
                int fillStatus = UserHourFillStatusEnum.getCodeByLeaveType(leaveDetail.getLeaveType());
                userFill.setUserId(leaveDetail.getUserId())
                        .setFillStatus(fillStatus)
                        .setEveryday(Boolean.TRUE)
                        .setNickName(userEntity.getNickName())
                        .setPostName(sysPost.getPostName());
                userFills.add(userFill);
            }
        }
        detail.setFills(userFills);
        // 缺报集合
        List<MhUserHourMissDetail> missDetails = userHourMissService.queryProjectMiss(projectId, localDate);
        List<ProjectFillCalendarDetail.UserFill> unFills = new ArrayList<>(missDetails.size());
        ProjectFillCalendarDetail.UserFill unfill;
        for (MhUserHourMissDetail missDetail : missDetails) {
            unfill = new ProjectFillCalendarDetail.UserFill();
            userEntity= userService.selectById(missDetail.getUserId());
            sysPost = postService.getByUserId(missDetail.getUserId());
            unfill.setUserId(missDetail.getUserId())
                    .setNickName(userEntity.getNickName())
                    .setPostName(sysPost.getPostName());
            unFills.add(unfill);
        }
        detail.setUnFills(unFills);
        return detail;
    }
}



