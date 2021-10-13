package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHourMissDetail;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.entity.ProjectUser;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.ProjectHourStatListVO;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.ProjectHourStatUser;
import com.oaker.hours.doman.vo.ProjectUserFillVO;
import com.oaker.hours.enums.ProjectStatusEnum;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Integer count = projectService.selectCount(ety);
        if (count <= 0) {
            tableDataInfo.setCode(HttpStatus.SUCCESS);
            tableDataInfo.setRows(Collections.emptyList());
            return tableDataInfo;
        }
        ety.orderDesc(Collections.singleton(Columns.Project.projectId));
        List<MhProject> mhProjects = projectService.selectList(ety);
        ProjectHourStatListVO vo;
        ProjectHour projectHour;
        SysUserEntity sysUserEntity;
        LocalDate now = LocalDate.now();
        List<ProjectUser> projectUsers;
        List<ProjectHourStatListVO> list =  new ArrayList<>(mhProjects.size());
        for (MhProject mhProject : mhProjects) {
            Long projectId = mhProject.getProjectId();
            projectHour = this.selectById(projectId);
            Long projectManager = mhProject.getProjectManager();
            sysUserEntity = userService.selectById(projectManager);
            int todayFill = userHourService.countFillByProject(projectId, now);
            projectUsers = projectUserService.queryUserByProjectId(projectId);
            int yesFill = userHourService.countFillByProject(projectId, now.plusDays(-1));
            int yesMissNum = userHourMissService.countProjectMiss(projectId, now.plusDays(-1));
            String projectStatusName = ProjectStatusEnum.getDescByCode(mhProject.getProjectStatus());
            vo = new ProjectHourStatListVO();
            vo.setProjectId(mhProject.getProjectId())
                    .setProjectName(mhProject.getProjectName())
                    .setProjectManager(projectManager)
                    .setProjectManagerName(sysUserEntity.getNickName())
                    .setProjectStatus(projectStatusName)
                    .setTotalHour(projectHour.getManHour())
                    .setUseHour(projectHour.getUseHour())
                    .setTodayFill(todayFill)
                    .setTodayMustFill(projectUsers.size())
                    .setYesFill(yesFill)
                    .setYesMustFill(yesFill + yesMissNum);
            list.add(vo);
        }
        tableDataInfo.setTotal(count);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setRows(list);
        return tableDataInfo;
    }

    @Override
    public ProjectUserFillVO queryFillDetail(Long projectId, LocalDate date) {
        ProjectUserFillVO result = new ProjectUserFillVO();
        List<MhHourDetail> mhHourDetails = userHourService.queryProjectUserHour(projectId, date);
        SysPost sysPost;
        SysUserEntity userEntity;
        if (!CollectionUtils.isEmpty(mhHourDetails)) {
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
                        .setPostName(sysPost.getPostName());
                fills.add(userFill);
            }
            result.setFills(fills);
        }

        List<MhUserHourMissDetail> missDetails = userHourMissService.queryProjectMiss(projectId, date);
        if (!CollectionUtils.isEmpty(missDetails)) {
            ProjectUserFillVO.UserFill missFill;
            List<ProjectUserFillVO.UserFill> unFills = new ArrayList<>(missDetails.size());
            for (MhUserHourMissDetail missDetail : missDetails) {
                missFill = new ProjectUserFillVO.UserFill();
                userEntity = userService.selectById(missDetail.getUserId());
                sysPost = postService.getByUserId(missDetail.getUserId());
                missFill.setUserId(missDetail.getUserId())
                        .setNickName(userEntity.getNickName())
                        .setPostName(sysPost.getPostName());
                unFills.add(missFill);
            }
            result.setUnFills(unFills);
        }
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
    public List<ProjectHourStatMonthDetail> queryProjectMonthUserDetail(Long projectId, String yearMonth) {
        List<MhHourDetail> mhHourDetails = userHourService.queryProjectUserHourMonth(projectId, yearMonth);
        if (CollectionUtils.isEmpty(mhHourDetails)) {
            return Collections.emptyList();
        }
        Map<LocalDate, List<MhHourDetail>> dateListMap = mhHourDetails.stream()
                .collect(Collectors.groupingBy(MhHourDetail::getFillDate));
        Set<Map.Entry<LocalDate, List<MhHourDetail>>> entrySet = dateListMap.entrySet();
        List<ProjectHourStatMonthDetail> resultList = new ArrayList<>(entrySet.size());
        for (Map.Entry<LocalDate, List<MhHourDetail>> dateListEntry : entrySet) {
            List<MhHourDetail> detailList = dateListEntry.getValue();
            Map<Long, List<MhHourDetail>> listMap = detailList.stream()
                    .collect(Collectors.groupingBy(MhHourDetail::getUserId));
            List<ProjectHourStatMonthDetail.UserStatHour> userStatHourList = new ArrayList<>(listMap.size());
            listMap.forEach((userId, mhHourDetailList) -> {
                ProjectHourStatMonthDetail.UserStatHour userStatHour = new ProjectHourStatMonthDetail.UserStatHour();
                SysUserEntity userEntity = userService.selectById(userId);
                BigDecimal useHour = mhHourDetailList.stream().map(MhHourDetail::getUseHour).reduce(BigDecimal.ZERO, BigDecimal::add);
                userStatHour.setUserId(userId)
                        .setNickName(userEntity.getNickName())
                        .setUseHour(useHour);
                userStatHourList.add(userStatHour);
            });
            ProjectHourStatMonthDetail detail = new ProjectHourStatMonthDetail();
            detail.setDate(dateListEntry.getKey())
                    .setUserStatHours(userStatHourList);
            resultList.add(detail);
        }
        return resultList;
    }

}



