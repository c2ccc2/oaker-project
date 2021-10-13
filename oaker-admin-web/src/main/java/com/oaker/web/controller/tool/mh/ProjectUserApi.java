package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.hours.doman.dto.ProjectUserSaveDTO;
import com.oaker.hours.doman.vo.ProjectUserVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 15:21
 */
@Api(tags = "项目人员管理", description = "项目人员管理相关接口")
@RestController
@RequestMapping("/doc/system/project/user")
public class ProjectUserApi {

    /**
     * 增加项目成员
     */
    @PostMapping("/create")
    @ApiOperation("项目人员增加")
    public AjaxResult save(@RequestBody ProjectUserSaveDTO projectUserSaveDTO) {
        return AjaxResult.success();
    }

    /**
     * 查询项目成员
     */
    @GetMapping("/list")
    @ApiOperation("查询项目成员")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public ProjectUserVO queryList(Long projectId) {
        return new ProjectUserVO();
    }

    /**
     * 移除项目成员
     * @param userIds
     * @return
     */
    @PutMapping("/remove")
    @ApiOperation("移除项目成员 （注：前端传参方式参看， 角色管理 --> 批量选择用户授权）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "userIds", value = "用户id集合", dataType = "List", required = true)
    })
    public AjaxResult removeUser(Long projectId, Long[] userIds) {
        return AjaxResult.success();
    }


    /**
     * 查询项目可选成员
     */
    @GetMapping("/select")
    @ApiOperation("查询项目可选成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "nickName", value = "用户昵称/姓名", dataType = "String")
    })
    public ProjectUserVO selectList(Long projectId, String nickName) {
        return new ProjectUserVO();
    }

    /**
     * 成员查询参与的项目不包含归档状态的项目
     * @return
     */
    @GetMapping("/my/project")
    @ApiOperation("用户查询参与项目不包含归档项目")
    public UserProjectShortVO queryMyProject() {
        return new UserProjectShortVO();
    }

    /**
     * 用户查询自己参与的项目
     * @return
     */
    @GetMapping("/my/project/all")
    @ApiOperation("用户查询自己参与的项目")
    @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String")
    public List<UserProjectShortVO> userProjects(String status) {
        return Collections.singletonList(new UserProjectShortVO());
    }


}
