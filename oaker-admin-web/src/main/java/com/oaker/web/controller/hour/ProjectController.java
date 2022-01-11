package com.oaker.web.controller.hour;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oaker.common.annotation.Log;
import com.oaker.common.constant.DictTypeConstants;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.domain.entity.SysUser;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.enums.BusinessType;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.dto.ProjectSaveDTO;
import com.oaker.hours.doman.dto.ProjectUpdateDTO;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.vo.MhProjectListVO;
import com.oaker.hours.service.impl.ProjectHourServiceImpl;
import com.oaker.hours.service.impl.ProjectServiceImpl;
import com.oaker.system.service.ISysDictDataService;
import com.oaker.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description :  项目管理
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 14:02
 */
@Validated
@RestController
@RequestMapping("/system/project")
public class ProjectController extends BaseController {

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private ProjectHourServiceImpl projectHourService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDictDataService sysDictDataService;

    /**
     * 新增项目
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('system:project:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    public AjaxResult create(@Validated @RequestBody ProjectSaveDTO sysProject) {
        boolean success = projectService.create(sysProject) > 0;
        if (success) {
            return AjaxResult.success();
        }
        return AjaxResult.error("创建项目失败");
    }

    /**
     * 查询项目列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('system:project:list')")
    public TableDataInfo list(String projectStatus) {
        startPage();
        List<MhProject> mhProjects = projectService.queryList(projectStatus);
        TableDataInfo dataTable = getDataTable(mhProjects);
        if (CollectionUtils.isEmpty(dataTable.getRows())) {
            return dataTable;
        }
        List<MhProjectListVO> mhProjectList = new ArrayList<>(mhProjects.size());
        mhProjects.forEach(mhProject -> {
            MhProjectListVO vo = new MhProjectListVO();
            BeanUtils.copyProperties(mhProject, vo);
            SysUser sysUser = userService.selectUserById(mhProject.getProjectManager());
            vo.setProjectManagerName(sysUser.getNickName());
            String status = sysDictDataService.selectDictLabel(DictTypeConstants.PROJECT_STATUS_TYPE, mhProject.getProjectStatus());
            vo.setProjectStatusName(status);
            vo.setManHour(this.getProjectManHour(mhProject.getProjectId()));
            mhProjectList.add(vo);
        });
        dataTable.setRows(mhProjectList);
        return dataTable;
    }

    private BigDecimal getProjectManHour(Long projectId) {
        EntityWrapper<ProjectHour> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.ProjectHour.projectId, projectId);
        ProjectHour projectHour = projectHourService.selectOne(wrapper);
        return projectHour.getManHour();
    }

    /**
     * 项目经理查询项目列表
     */
    @GetMapping("/list/manager")
    @PreAuthorize("@ss.hasPermi('system:project:list:manager')")
    public TableDataInfo managerList(String projectStatus) {
        startPage();
        List<MhProject> mhProjects = projectService.queryMyList(projectStatus);
        TableDataInfo dataTable = getDataTable(mhProjects);
        if (CollectionUtils.isEmpty(dataTable.getRows())) {
            return dataTable;
        }
        List<MhProjectListVO> mhProjectList = new ArrayList<>(mhProjects.size());
        mhProjects.forEach(mhProject -> {
            MhProjectListVO vo = new MhProjectListVO();
            BeanUtils.copyProperties(mhProject, vo);
            SysUser sysUser = userService.selectUserById(mhProject.getProjectManager());
            vo.setProjectManagerName(sysUser.getNickName());
            String status = sysDictDataService.selectDictLabel(DictTypeConstants.PROJECT_STATUS_TYPE, mhProject.getProjectStatus());
            vo.setProjectStatusName(status);
            vo.setManHour(this.getProjectManHour(mhProject.getProjectId()));
            mhProjectList.add(vo);
        });
        dataTable.setRows(mhProjectList);
        return dataTable;
    }


    /**
     * 根据项目id获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:project:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@NotNull(message = "项目id不能为空") @PathVariable Long projectId) {
        MhProject mhProject = projectService.selectById(projectId);
        if (Objects.isNull(mhProject)) {
            return AjaxResult.success();
        }
        MhProjectListVO vo = new MhProjectListVO();
        BeanUtils.copyProperties(mhProject, vo);
        SysUser sysUser = userService.selectUserById(mhProject.getProjectManager());
        vo.setProjectManagerName(sysUser.getNickName());
        String status = sysDictDataService.selectDictLabel(DictTypeConstants.PROJECT_STATUS_TYPE, mhProject.getProjectStatus());
        vo.setProjectStatusName(status);
        vo.setManHour(this.getProjectManHour(mhProject.getProjectId()));
        return AjaxResult.success(vo);
    }


    /**
     * 修改项目信息
     */
    @PutMapping("/modify")
    @PreAuthorize("@ss.hasPermi('system:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    public AjaxResult modify(@Validated @RequestBody ProjectUpdateDTO projectUpdateDTO) {
        return toAjax(projectService.modify(projectUpdateDTO));
    }

    /**
     * 删除项目信息
     */
    @DeleteMapping("/remove")
    @PreAuthorize("@ss.hasPermi('system:project:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    public AjaxResult remove(@NotNull(message = "项目id不能为空") Long projectId) {
        return toAjax(projectService.delete(projectId));
    }

    /**
     * 修改项目状态
     */
    @PutMapping("/status")
    @PreAuthorize("@ss.hasPermi('system:project:status:edit')")
    @Log(title = "项目状态设置", businessType = BusinessType.UPDATE)
    public AjaxResult setStatus(@NotNull(message = "项目id不能为空") Long projectId,
                                @NotBlank(message = "项目状态不能为空") String projectStatus) {
        MhProject mhProject = new MhProject();
        mhProject.setProjectId(projectId);
        mhProject.setProjectStatus(projectStatus);
        boolean result = projectService.updateById(mhProject);
        return toAjax(result);
    }

    /**
     * 设置项目 暂停/启用 状态
     */
    @PutMapping("/enable")
    @PreAuthorize("@ss.hasPermi('system:project:status:edit')")
    @Log(title = "设置项目 暂停/启用 状态", businessType = BusinessType.UPDATE)
    public AjaxResult enable(@NotNull(message = "项目id不能为空") Long projectId
            ,@NotNull(message = "状态不能为空不能为空") Boolean enable) {
        return toAjax(projectService.enable(projectId, enable));
    }

}
