package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oaker.common.utils.FestivalsUtil;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.MhUserLeave;
import com.oaker.hours.doman.entity.MhUserLeaveDetail;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.IndexFillVO;
import com.oaker.hours.doman.vo.IndexProjectStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.enums.LeaveTypeEnum;
import com.oaker.hours.enums.UserHourFillStatusEnum;
import com.oaker.hours.service.IndexService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Description : 首页
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 9:38
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private MhUserHourServiceImpl userHourService;

    @Resource
    private MhUserLeaveServiceImpl userLeaveService;

    @Resource
    private ProjectHourServiceImpl projectHourService;

    @Resource
    private ProjectUserServiceImpl projectUserService;

    @Resource
    private MhHourDetailServiceImpl hourDetailService;

    @Override
    public List<IndexProjectStatVO> projectStat() {
        Long userId = SecurityUtils.getUserId();
        List<UserProjectShortVO> voList = projectUserService.queryMyProject(userId, new Date());
        if (CollectionUtils.isEmpty(voList)) {
            return Collections.emptyList();
        }
        Long projectId;
        MhProject mhProject;
        IndexProjectStatVO vo;
        MhHourDetail hourDetail;
        MhUserLeaveDetail leaveDetail;
        List<IndexProjectStatVO> list = new ArrayList<>(voList.size());
        for (UserProjectShortVO shortVo : voList) {
            vo = new IndexProjectStatVO();
            projectId = shortVo.getProjectId();
            mhProject = projectService.selectById(projectId);
            vo.setProjectId(projectId)
                    .setProjectCode(mhProject.getProjectCode())
                    .setProjectName(shortVo.getProjectName())
                    .setProjectStatus(shortVo.getProjectStatus());
            SysUserEntity userEntity = userService.selectById(mhProject.getProjectManager());
            vo.setProjectManager(userEntity.getUserId())
                    .setProjectManagerName(userEntity.getNickName());
            ProjectHour projectHour = projectHourService.selectById(projectId);
            vo.setManHour(projectHour.getManHour())
                    .setUseHour(projectHour.getUseHour());
            hourDetail = hourDetailService.queryProjectUserHour(projectId, userId, LocalDate.now());
            // 已填报
            if (!Objects.isNull(hourDetail)) {
                vo.setDayHour(hourDetail.getUseHour())
                        .setFillStatus(UserHourFillStatusEnum.FILLED.getStatus());
                list.add(vo);
                continue;
            }
            // 节假日
            if (FestivalsUtil.isAHoliday(LocalDate.now().toString())) {
                vo.setFillStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
                list.add(vo);
                continue;
            }
            // 请假/调休
            leaveDetail = userLeaveService.queryProjectUserLeave(projectId, userId, LocalDate.now());
            if (!Objects.isNull(leaveDetail)) {
                vo.setFillStatus(UserHourFillStatusEnum.getCodeByLeaveType(leaveDetail.getLeaveType()));
                list.add(vo);
                continue;
            }
            vo.setFillStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
            list.add(vo);
        }
        return list;
    }

    @Override
    public IndexFillVO fillInfo() {
        IndexFillVO indexFillVo = new IndexFillVO();
        Long userId = SecurityUtils.getUserId();
        this.getDayFillStatus(userId, indexFillVo);
        LocalDate now = LocalDate.now();
        int monthFill = userHourService.getMonthFill(userId, now);
        indexFillVo.setMonthFill(monthFill);
        int monthMissFill = userHourService.getMonthMissFill(userId, now);
        indexFillVo.setMonthMissFill(monthMissFill);

        LocalDate first = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = now.with(TemporalAdjusters.lastDayOfMonth());
        List<MhUserLeave> mhUserLeaves = userLeaveService.queryLeave(userId, first, last);
        Map<Integer, List<MhUserLeave>> collect = mhUserLeaves.stream().collect(groupingBy(MhUserLeave::getLeaveType));

        int leaveNum = CollectionUtils.isEmpty(collect.get(LeaveTypeEnum.LEAVE.getCode())) ? 0 : collect.get(LeaveTypeEnum.LEAVE.getCode()).size();
        int offDutyNum = CollectionUtils.isEmpty(collect.get(LeaveTypeEnum.OFF_DUTY.getCode())) ? 0 : collect.get(LeaveTypeEnum.OFF_DUTY.getCode()).size();
        indexFillVo.setLeaveNum(leaveNum);
        indexFillVo.setOffDutyNum(offDutyNum);
        return indexFillVo;
    }

    private void getDayFillStatus(Long userId, IndexFillVO indexFillVo) {
        // 如果没有参与每日上报项目 无需上报
        List<UserProjectShortVO> voList = projectUserService.queryMyProject(userId, new Date());
        if (CollectionUtils.isEmpty(voList)) {
            indexFillVo.setFillStatus(UserHourFillStatusEnum.NO_NEED.getStatus());
            return;
        }
        // 已填报
        MhUserHour userHour = userHourService.getByUserId(userId, LocalDate.now());
        if (!Objects.isNull(userHour)) {
            indexFillVo.setFillStatus(UserHourFillStatusEnum.FILLED.getStatus())
                    .setDayHour(userHour.getTotalHour());
            return;
        }
        if (FestivalsUtil.isAHoliday(LocalDate.now().toString())) {
            indexFillVo.setFillStatus(UserHourFillStatusEnum.FESTIVAL.getStatus());
            return;
        }
        // 请假/调休
        EntityWrapper<MhUserLeave> leaveWrapper = new EntityWrapper<>();
        leaveWrapper.eq(Columns.MhUserLeave.userId, userId)
                .and()
                .eq(Columns.MhUserLeave.leaveDate, LocalDate.now());
        MhUserLeave leave = userLeaveService.selectOne(leaveWrapper);
        if (!Objects.isNull(leave)) {
            indexFillVo.setFillStatus(UserHourFillStatusEnum.getCodeByLeaveType(leave.getLeaveType()));
            return;
        }
        indexFillVo.setFillStatus(UserHourFillStatusEnum.NOT_FILLED.getStatus());
    }

}
