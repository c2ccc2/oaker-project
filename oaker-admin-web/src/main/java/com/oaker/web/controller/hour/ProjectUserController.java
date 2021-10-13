package com.oaker.web.controller.hour;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.enums.BusinessType;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.dto.ProjectUserSaveDTO;
import com.oaker.hours.doman.vo.ProjectUserVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.service.impl.ProjectUserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description : 项目成员
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 15:15
 */
@Validated
@RestController
@RequestMapping("/system/project/user")
public class ProjectUserController extends BaseController {

    @Resource
    private ProjectUserServiceImpl projectUserService;

    /**
     * 增加项目成员
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('mh:project:edit')")
    @Log(title = "项目人员管理", businessType = BusinessType.INSERT)
    public AjaxResult save(@Validated @RequestBody ProjectUserSaveDTO projectUserSaveDTO) {
        return toAjax(projectUserService.create(projectUserSaveDTO));
    }

    /**
     * 查询项目成员
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('mh:project:user:list')")
    public AjaxResult queryList(@NotNull(message = "项目id不能为空") Long projectId) {
        List<ProjectUserVO> queryList = projectUserService.queryList(projectId);
        return AjaxResult.success(queryList);
    }

    /**
     * 移除项目成员
     * @param userIds
     * @return
     */
    @PutMapping("/remove")
    @PreAuthorize("@ss.hasPermi('mh:project:edit')")
    public AjaxResult removeUser(@NotNull(message = "项目id不能为空") Long projectId
            ,@NotNull(message = "人员id不能为空") Long[] userIds) {
        boolean result = projectUserService.removeUser(projectId, Arrays.asList(userIds));
        return toAjax(result);
    }

    /**
     * 查询项目可选成员
     * @param projectId
     * @param nickName
     * @return
     */
    @GetMapping("/select")
    @PreAuthorize("@ss.hasPermi('mh:project:edit')")
    public AjaxResult selectList(@NotNull(message = "项目id不能为空") Long projectId, String nickName) {
        return AjaxResult.success(projectUserService.selectList(projectId, nickName));
    }

    /**
     * 成员查询参与的项目不包含归档状态的项目
     * @return
     */
    @GetMapping("/my/project")
    @PreAuthorize("@ss.hasPermi('mh:project:hour')")
    public AjaxResult queryMyProject(Date date) {
        Long userId = SecurityUtils.getUserId();
        return AjaxResult.success(projectUserService.queryMyProject(userId, date));
    }

    /**
     * 用户查询自己参与的项目
     * @return
     */
    @GetMapping("/my/project/all")
    @PreAuthorize("@ss.hasPermi('mh:hour:list')")
    public AjaxResult userProjects(String projectStatus) {
        Long userId = SecurityUtils.getUserId();
        List<UserProjectShortVO> vos = projectUserService.userProjects(userId, projectStatus);
        return AjaxResult.success(vos);
    }

}
