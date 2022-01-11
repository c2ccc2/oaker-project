package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.exception.ServiceException;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.entity.MhUserLeave;
import com.oaker.hours.doman.entity.MhUserLeaveDetail;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.enums.ProjectStatusEnum;
import com.oaker.hours.mapper.MhUserLeaveDetailMapper;
import com.oaker.hours.mapper.MhUserLeaveMapper;
import com.oaker.hours.service.MhUserLeaveService;
import com.oaker.hours.service.ProjectUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/8 16:44
 */
@Service
public class MhUserLeaveServiceImpl extends ServiceImpl<MhUserLeaveMapper, MhUserLeave> implements MhUserLeaveService {

    @Resource
    private MhUserLeaveDetailMapper userLeaveDetailMapper;

    @Resource
    private MhUserHourMissServiceImpl userHourMissService;

    @Resource
    private ProjectUserService projectUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(LocalDate leaveDate, int leaveType) {
        Long userId = SecurityUtils.getUserId();
        EntityWrapper<MhUserLeave> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserLeave.userId, userId)
                .and()
                .eq(Columns.MhUserLeave.leaveDate, leaveDate);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new ServiceException("当前日期已存在请假/调休记录，不能重复提交。");
        }
        MhUserLeave leave = new MhUserLeave();
        leave.setLeaveDate(leaveDate)
                .setUserId(userId)
                .setLeaveType(leaveType)
                .setCreateTime(new Date());
         baseMapper.insert(leave);
        List<UserProjectShortVO> collect = projectUserService.userProjects(userId, null, Boolean.TRUE);
        List<UserProjectShortVO> userProjects = collect.stream()
                .filter(vo -> !Objects.equals(vo.getProjectStatus(), ProjectStatusEnum.COMPLETE.getCode()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userProjects)) {
            return Boolean.TRUE;
        }
        MhUserLeaveDetail detail;
        for (UserProjectShortVO userProject : userProjects) {
            detail = new MhUserLeaveDetail();
            detail.setLeaveDate(leaveDate)
                    .setLeaveId(leave.getId())
                    .setProjectId(userProject.getProjectId())
                    .setProjectStatus(ProjectStatusEnum.getCodeByDesc(userProject.getProjectStatus()))
                    .setUserId(userId)
                    .setLeaveType(leaveType);
            userLeaveDetailMapper.insert(detail);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<MhUserLeave> queryLeave(Long userId, LocalDate startDate, LocalDate endDate) {
        EntityWrapper<MhUserLeave> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserLeave.userId, userId)
                .and()
                .between(Columns.MhUserLeave.leaveDate, startDate, endDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unLeave(Long id) {
        MhUserLeave leave = baseMapper.selectById(id);
        if (Objects.isNull(leave)) {
            throw new ServiceException("请假/调休记录不存在");
        }
        userHourMissService.createMissByLeaveId(id);
        baseMapper.deleteById(id);
        EntityWrapper<MhUserLeaveDetail> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq(Columns.MhUserLeaveDetail.leaveId, id);
        userLeaveDetailMapper.delete(entityWrapper);
        return Boolean.TRUE;
    }

    @Override
    public List<MhUserLeaveDetail> queryLeaveDetail(Long id) {
        EntityWrapper<MhUserLeaveDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserLeaveDetail.leaveId, id);
        return userLeaveDetailMapper.selectList(wrapper);
    }

    @Override
    public List<MhUserLeaveDetail> queryByProjectId(Long projectId, LocalDate date) {
        EntityWrapper<MhUserLeaveDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserLeaveDetail.projectId, projectId)
                .and()
                .eq(Columns.MhUserLeaveDetail.leaveDate, date);
        return userLeaveDetailMapper.selectList(wrapper);
    }

    @Override
    public MhUserLeaveDetail queryProjectUserLeave(Long projectId, Long userId, LocalDate now) {
        MhUserLeaveDetail detail = new MhUserLeaveDetail();
        detail.setUserId(userId)
                .setLeaveDate(now)
                .setProjectId(projectId);
        return userLeaveDetailMapper.selectOne(detail);
    }
}
