package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.utils.FestivalsUtil;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.MhUserHourMiss;
import com.oaker.hours.doman.entity.MhUserHourMissDetail;
import com.oaker.hours.doman.entity.MhUserLeave;
import com.oaker.hours.doman.entity.MhUserLeaveDetail;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.enums.ProjectStatusEnum;
import com.oaker.hours.mapper.MhUserHourMissDetailMapper;
import com.oaker.hours.mapper.MhUserHourMissMapper;
import com.oaker.hours.service.MhUserHourMissService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description :  用户缺报记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 16:50
 */
@Slf4j
@Service
public class MhUserHourMissServiceImpl extends ServiceImpl<MhUserHourMissMapper, MhUserHourMiss> implements MhUserHourMissService {

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private MhUserHourServiceImpl userHourService;

    @Resource
    private MhUserLeaveServiceImpl userLeaveService;

    @Resource
    private ProjectUserServiceImpl projectUserService;

    @Resource
    private MhUserHourMissDetailMapper userHourMissDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIdAndDetail(Long id) {
        EntityWrapper<MhUserHourMissDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMissDetail.missId, id);
        userHourMissDetailMapper.delete(wrapper);
        baseMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public List<MhUserHourMiss> queryUserList(Long userId, LocalDate startDate, LocalDate endDate) {
        EntityWrapper<MhUserHourMiss> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMiss.userId, userId)
                .and()
                .between(Columns.MhUserHourMiss.missDate, startDate, endDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMiss(Long userId, LocalDate localDate) {
        List<UserProjectShortVO> userProjects = projectUserService.userProjects(userId, null, Boolean.TRUE);
        List<UserProjectShortVO> collect = userProjects.stream()
                .filter(vo -> !Objects.equals(vo.getProjectStatus(), ProjectStatusEnum.COMPLETE.getCode()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return Boolean.TRUE;
        }
        MhUserHourMiss miss = new MhUserHourMiss();
        miss.setMissDate(localDate)
                .setUserId(userId);
        baseMapper.insert(miss);
        MhUserHourMissDetail detail;
        for (UserProjectShortVO userProject : collect) {
            detail = new MhUserHourMissDetail();
            detail.setMissDate(localDate)
                    .setMissId(miss.getId())
                    .setProjectId(userProject.getProjectId())
                    .setProjectStatus(userProject.getProjectStatus())
                    .setUserId(userId);
            userHourMissDetailMapper.insert(detail);
        }
        return Boolean.TRUE;
    }

    @Override
    public void userMissTask() {
        log.info("【缺报定时任务】开始执行 ... ...");
        LocalDate localDate = LocalDate.now().minusDays(1);
        if (FestivalsUtil.isAHoliday(localDate.toString())) {
            return;
        }
        // 获取所有填报用户
        Set<Long> userIds = projectUserService.queryJoinUserIds(localDate);
        // 查询所有填报记录
        List<MhUserHour> mhUserHours = this.queryFillAll(localDate);
        Set<Long> hourSet = mhUserHours.stream().map(MhUserHour::getUserId).collect(Collectors.toSet());
        // 查询所有请假记录
        List<MhUserLeave> mhUserLeaves = this.queryLeaveAll(localDate);
        Set<Long> leaveUsers = mhUserLeaves.stream().map(MhUserLeave::getUserId).collect(Collectors.toSet());
        for (Long userId : userIds) {
            if (hourSet.contains(userId) || leaveUsers.contains(userId)) {
                continue;
            }
            this.createMiss(userId, localDate);
        }
        log.info("【缺报定时任务】执行结束 ... ...");
    }

    private List<MhUserHour> queryFillAll(LocalDate localDate) {
        EntityWrapper<MhUserHour> hourWrapper = new EntityWrapper<>();
        hourWrapper.eq(Columns.MhUserHour.fillDate, localDate);
        return userHourService.selectList(hourWrapper);
    }

    private List<MhUserLeave> queryLeaveAll(LocalDate localDate) {
        EntityWrapper<MhUserLeave> leaveWrapper = new EntityWrapper<>();
        leaveWrapper.eq(Columns.MhUserLeave.leaveDate, localDate);
        return userLeaveService.selectList(leaveWrapper);
    }

    @Override
    public List<MhUserHourMissDetail> queryProjectMiss(Long projectId, LocalDate date) {
        EntityWrapper<MhUserHourMissDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMissDetail.projectId, projectId);
        if (!Objects.isNull(date)) {
            wrapper.eq(Columns.MhUserHourMissDetail.missDate, date);
        }
        return userHourMissDetailMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMiss(LocalDate leaveDate) {
        Long userId = SecurityUtils.getUserId();
        MhUserHourMiss miss = new MhUserHourMiss();
        miss.setUserId(userId)
                .setMissDate(leaveDate);
        miss = baseMapper.selectOne(miss);
        if (Objects.isNull(miss)) {
            return true;
        }
        Long missId = miss.getId();
        baseMapper.deleteById(missId);
        EntityWrapper<MhUserHourMissDetail> detailWrapper = new EntityWrapper<>();
        detailWrapper.eq(Columns.MhUserHourMissDetail.missId, missId);
        userHourMissDetailMapper.delete(detailWrapper);
        return true;
    }

    @Override
    public List<UserProjectShortVO> queryMyMissProject(Long missId) {
        EntityWrapper<MhUserHourMissDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMissDetail.missId, missId);
        List<MhUserHourMissDetail> missDetails = userHourMissDetailMapper.selectList(wrapper);
        List<UserProjectShortVO> list = new ArrayList<>(missDetails.size());
        UserProjectShortVO vo;
        for (MhUserHourMissDetail missDetail : missDetails) {
            vo = new UserProjectShortVO();
            MhProject mhProject = projectService.selectById(missDetail.getProjectId());
            vo.setProjectId(mhProject.getProjectId())
                    .setProjectStatus(missDetail.getProjectStatus())
                    .setProjectName(mhProject.getProjectName());
            list.add(vo);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMissByLeaveId(Long id) {
        MhUserLeave leave = userLeaveService.selectById(id);
        if (leave.getLeaveDate().compareTo(LocalDate.now()) == 0) {
            return Boolean.TRUE;
        }
        List<MhUserLeaveDetail> details = userLeaveService.queryLeaveDetail(id);
        if (CollectionUtils.isEmpty(details)) {
            return Boolean.TRUE;
        }
        MhUserHourMiss userHourMiss = new MhUserHourMiss();
        userHourMiss.setUserId(leave.getUserId())
                .setMissDate(leave.getLeaveDate());
        baseMapper.insert(userHourMiss);
        MhUserHourMissDetail missDetail;
        for (MhUserLeaveDetail detail : details) {
            missDetail = new MhUserHourMissDetail();
            missDetail.setMissId(userHourMiss.getId())
                    .setUserId(leave.getUserId())
                    .setProjectId(detail.getProjectId())
                    .setProjectStatus(detail.getProjectStatus())
                    .setMissDate(detail.getLeaveDate());
            userHourMissDetailMapper.insert(missDetail);
        }
        return Boolean.TRUE;
    }
}
