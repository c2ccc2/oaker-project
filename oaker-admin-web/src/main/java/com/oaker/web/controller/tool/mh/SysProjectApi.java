package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.hours.doman.dto.ProjectSaveDTO;
import com.oaker.hours.doman.dto.ProjectUpdateDTO;
import com.oaker.hours.doman.vo.MhProjectListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/6 15:52
 */
@Api(tags = "项目管理", description = "项目管理相关接口")
@RestController
@RequestMapping("/doc/system/project")
public class SysProjectApi {

    /**
     * 新增项目
     */
    @PostMapping("/create")
    @ApiOperation("新增项目")
    public AjaxResult add(@RequestBody ProjectSaveDTO sysProject) {
        return AjaxResult.success();
    }

    /**
     * 查询项目列表
     */
    @GetMapping("/list")
    @ApiOperation("查询项目列表")
    @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String")
    public MhProjectListVO list(String projectStatus) {
        MhProjectListVO vo = new MhProjectListVO();
        return vo;
    }

    /**
     * 项目经理查询项目列表
     */
    @GetMapping("/list/manager")
    @ApiOperation("项目经理查询项目列表")
    @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String")
    public MhProjectListVO managerList(String projectStatus) {
        MhProjectListVO vo = new MhProjectListVO();
        return vo;
    }

    /**
     * 查询项目列表
     */
    @GetMapping("/{projectId}")
    @ApiOperation("查询项目详情")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", paramType = "path")
    public MhProjectListVO getInfo(Long projectId) {
        MhProjectListVO vo = new MhProjectListVO();
        return vo;
    }

    /**
     * 修改项目信息
     */
    @PutMapping("/modify")
    @ApiOperation("项目概要编辑")
    public AjaxResult modify(@RequestBody ProjectUpdateDTO projectUpdateDTO) {
        return AjaxResult.success();
    }

    /**
     * 删除项目信息
     */
    @DeleteMapping("/remove")
    @ApiOperation("删除项目")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public AjaxResult remove(Long projectId) {
        return AjaxResult.success();
    }


    /**
     * 修改项目状态
     */
    @PutMapping("/status")
    @ApiOperation("设置项目状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String", required = true)
    })
    public AjaxResult setStatus(Long projectId, String projectStatus) {
        return AjaxResult.success();
    }


}
