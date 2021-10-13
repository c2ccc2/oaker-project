package com.oaker.hours.service.impl;

import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.IndexFillVO;
import com.oaker.hours.doman.vo.IndexProjectStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.service.IndexService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
            hourDetail = hourDetailService.queryProjectUserHour(projectId, userId, LocalDate.now());
            if (!Objects.isNull(hourDetail)) {
                vo.setDayHour(hourDetail.getUseHour());
            }
            hourDetail = hourDetailService.queryProjectUserHour(projectId, userId, LocalDate.now().plusMonths(-1));
            if (!Objects.isNull(hourDetail)) {
                vo.setYesHour(hourDetail.getUseHour());
            }
            ProjectHour projectHour = projectHourService.selectById(projectId);
            vo.setManHour(projectHour.getManHour())
                    .setUseHour(projectHour.getUseHour());
            list.add(vo);
        }
        return list;
    }

    @Override
    public IndexFillVO fillInfo() {
        IndexFillVO indexFillVo = new IndexFillVO();
        Long userId = SecurityUtils.getUserId();
        List<UserProjectShortVO> voList = projectUserService.queryMyProject(userId, new Date());
        if (CollectionUtils.isEmpty(voList)) {
            indexFillVo.setDayFill(3);
        }else {
            MhUserHour userHour = userHourService.getByUserId(userId, LocalDate.now());
            if (!Objects.isNull(userHour)) {
                indexFillVo.setDayFill(2);
            }else {
                indexFillVo.setDayFill(1);
            }
        }
        LocalDate now = LocalDate.now();
        int monthFill = userHourService.getMonthFill(userId, now);
        indexFillVo.setMonthFill(monthFill);
        int monthMissFill = userHourService.getMonthMissFill(userId, now);
        indexFillVo.setMonthMissFill(monthMissFill);
        return indexFillVo;
    }
}
