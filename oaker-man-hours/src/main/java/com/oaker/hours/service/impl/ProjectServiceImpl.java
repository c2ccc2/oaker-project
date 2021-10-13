package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.exception.ServiceException;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.dto.ProjectSaveDTO;
import com.oaker.hours.doman.dto.ProjectUpdateDTO;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.mapper.ProjectMapper;
import com.oaker.hours.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 13:29
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, MhProject> implements ProjectService {

    @Resource
    private ProjectHourServiceImpl projectHourService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProjectSaveDTO projectSaveDTO) {
        EntityWrapper<MhProject> ety = new EntityWrapper<>();
        ety.eq(Columns.Project.projectCode, projectSaveDTO.getProjectCode());
        Integer count = baseMapper.selectCount(ety);
        if (count > 0) {
            throw new ServiceException("项目编码已存在");
        }
        String username = SecurityUtils.getUsername();
        Date date = new Date();
        MhProject mhProject = new MhProject();
        mhProject.setProjectName(projectSaveDTO.getProjectName())
                .setProjectCode(projectSaveDTO.getProjectCode())
                .setProjectManager(projectSaveDTO.getProjectManager())
                .setRemark(projectSaveDTO.getRemark())
                .setCreateBy(username)
                .setCreateTime(date);
        baseMapper.insert(mhProject);
        Long projectId = mhProject.getProjectId();
        this.createProjectHour(projectId, projectSaveDTO.getDuration());
        return projectId;
    }

    private void createProjectHour(Long projectId, BigDecimal manHour) {
        ProjectHour projectHour = new ProjectHour();
        projectHour.setProjectId(projectId);
        projectHour.setManHour(manHour);
        projectHourService.insert(projectHour);
    }

    @Override
    public List<MhProject> queryList(String projectStatus) {
        EntityWrapper<MhProject> ety = new EntityWrapper<>();
        if (StringUtils.isNotBlank(projectStatus)) {
            ety.eq(Columns.Project.projectStatus, projectStatus);
        }
        ety.eq(Columns.deleted, Boolean.FALSE);
        ety.orderDesc(Collections.singleton(Columns.Project.projectId));
        return baseMapper.selectList(ety);
    }

    @Override
    public List<MhProject> queryMyList(String projectStatus) {
        Long userId = SecurityUtils.getUserId();
        EntityWrapper<MhProject> ety = new EntityWrapper<>();
        if (StringUtils.isNotBlank(projectStatus)) {
            ety.eq(Columns.Project.projectStatus, projectStatus);
        }
        ety.eq(Columns.deleted, Boolean.FALSE);
        ety.eq(Columns.Project.projectManager, userId);
        ety.orderDesc(Collections.singleton(Columns.Project.projectId));
        return baseMapper.selectList(ety);
    }

    @Override
    public boolean modify(ProjectUpdateDTO projectUpdateDTO) {
        MhProject mhProject = baseMapper.selectById(projectUpdateDTO.getProjectId());
        if (Objects.isNull(mhProject)) {
            return Boolean.TRUE;
        }
        mhProject = new MhProject();
        mhProject.setProjectId(projectUpdateDTO.getProjectId())
                .setProjectName(projectUpdateDTO.getProjectName())
                .setProjectManager(projectUpdateDTO.getProjectManager())
                .setRemark(projectUpdateDTO.getRemark());
        return baseMapper.updateById(mhProject) > 0;
    }

    @Override
    public boolean delete(Long projectId) {
        MhProject mhProject = new MhProject();
        mhProject.setProjectId(projectId);
        mhProject.setDeleted(Boolean.TRUE);
        return baseMapper.updateById(mhProject) > 0;
    }


}
