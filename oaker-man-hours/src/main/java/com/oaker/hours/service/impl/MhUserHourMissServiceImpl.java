package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.utils.FestivalsUtil;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.MhUserHourMiss;
import com.oaker.hours.doman.entity.MhUserHourMissDetail;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.mapper.MhUserHourMissDetailMapper;
import com.oaker.hours.mapper.MhUserHourMissMapper;
import com.oaker.hours.service.MhUserHourMissService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
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
@Service
public class MhUserHourMissServiceImpl extends ServiceImpl<MhUserHourMissMapper, MhUserHourMiss> implements MhUserHourMissService {

    @Resource
    private ProjectUserServiceImpl projectUserService;

    @Resource
    private MhUserHourServiceImpl userHourService;

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
        List<UserProjectShortVO> userProjects = projectUserService.userProjects(userId, null);
        if (CollectionUtils.isEmpty(userProjects)) {
            return Boolean.TRUE;
        }
        MhUserHourMiss miss = new MhUserHourMiss();
        miss.setMissDate(localDate)
                .setUserId(userId);
        baseMapper.insert(miss);
        MhUserHourMissDetail detail;
        for (UserProjectShortVO userProject : userProjects) {
            detail = new MhUserHourMissDetail();
            detail.setMissDate(localDate)
                    .setMissId(miss.getId())
                    .setProjectId(userProject.getProjectId())
                    .setUserId(userId);
            userHourMissDetailMapper.insert(detail);
        }
        return Boolean.TRUE;
    }

    @Override
    public void userMissTask() {
        System.out.println("收到调用 ...");
        LocalDate localDate = LocalDate.now().minusDays(1);
        if (FestivalsUtil.isAHoliday(localDate.toString())) {
            return;
        }
        // 获取所有填报用户
        Set<Long> userIds = projectUserService.queryJoinUserIds();
        // 查询所有填报记录
        List<MhUserHour> mhUserHours = this.queryFillAll(localDate);
        Set<Long> hourSet = mhUserHours.stream().map(MhUserHour::getUserId).collect(Collectors.toSet());
        for (Long userId : userIds) {
            if (hourSet.contains(userId)) {
                continue;
            }
            this.createMiss(userId, localDate);
        }
    }

    private List<MhUserHour> queryFillAll(LocalDate localDate) {
        EntityWrapper<MhUserHour> hourWrapper = new EntityWrapper<>();
        hourWrapper.eq(Columns.MhUserHour.fillDate, localDate);
        return userHourService.selectList(hourWrapper);
    }

    @Override
    public int countProjectMiss(Long projectId, LocalDate date) {
        EntityWrapper<MhUserHourMissDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhUserHourMissDetail.missDate, date)
                .and()
                .eq(Columns.MhUserHourMissDetail.projectId, projectId);
        return userHourMissDetailMapper.selectCount(wrapper);
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
}
