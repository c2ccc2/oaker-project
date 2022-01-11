package com.oaker.web.controller.hour;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.enums.BusinessType;
import com.oaker.hours.doman.dto.MhCostListDTO;
import com.oaker.hours.doman.dto.MhPostCostDTO;
import com.oaker.hours.doman.dto.MhUserCostDTO;
import com.oaker.hours.doman.vo.CostConfigVO;
import com.oaker.hours.service.impl.MhCostServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @Description : 成本统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 16:12
 */
@Validated
@RestController
@RequestMapping("/mh/cost")
public class MhCostController extends BaseController {

    @Resource
    private MhCostServiceImpl costService;

    /**
     * 获取成本统计配置
     * @return
     */
    @GetMapping("/conf/query")
    @PreAuthorize("@ss.hasPermi('mh:cost:set')")
    public AjaxResult getCostConfig() {
        CostConfigVO costConfigVO = costService.get();
        return AjaxResult.success(costConfigVO);
    }

    /**
     * 修改成本统计设置
     * @param postCost
     * @param userCost
     * @return
     */
    @PutMapping("/edit")
    @PreAuthorize("@ss.hasPermi('mh:cost:set')")
    @Log(title = "成本统计设置", businessType = BusinessType.UPDATE)
    public AjaxResult update(Boolean postCost, Boolean userCost) {
        if (Objects.isNull(postCost) && Objects.isNull(userCost)) {
            return AjaxResult.error("必要参数不能为空");
        }
        return toAjax(costService.update(postCost, userCost));
    }

    /**
     * 查询岗位成本列表
     * @param postName
     * @return
     */
    @GetMapping("/post/query")
    @PreAuthorize("@ss.hasPermi('mh:cost:set')")
    public TableDataInfo queryPostList(String postName) {
        Page page = super.getPage();
        return costService.queryPost(postName, page.getPageStart(), page.getPageSize());
    }

    /**
     * 设置岗位成本
     * @param postCosts
     * @return
     */
    @PostMapping("/post/set")
    @PreAuthorize("@ss.hasPermi('mh:cost:set')")
    @Log(title = "岗位成本设置", businessType = BusinessType.INSERT)
    public AjaxResult setPostCost(@RequestBody @NotEmpty(message = "设置参数不能为空") @Valid List<MhPostCostDTO> postCosts) {
        return toAjax(costService.setPostCost(postCosts));
    }

    /**
     * 查询人员成本列表
     * @param nickName
     * @return
     */
    @GetMapping("/user/query")
    @PreAuthorize("@ss.hasPermi('mh:cost:set')")
    public TableDataInfo queryUser(String nickName) {
        Page page = super.getPage();
        return costService.queryUser(nickName, page.getPageStart(), page.getPageSize());
    }

    /**
     * 设置人员成本
     * @param userCosts
     * @return
     */
    @PostMapping("/user/set")
    @PreAuthorize("@ss.hasPermi('mh:cost:set')")
    @Log(title = "人员成本设置", businessType = BusinessType.INSERT)
    public AjaxResult setUserCost(@RequestBody @NotEmpty(message = "设置参数不能为空") @Valid List<MhUserCostDTO> userCosts) {
        return toAjax(costService.setUserCost(userCosts));
    }

    /**
     * 查询成本统计列表
     * @param mhCostDTO
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("@ss.hasPermi('mh:cost:query')")
    public TableDataInfo query(MhCostListDTO mhCostDTO) {
        Page page = super.getPage();
        return costService.query(mhCostDTO, page.getPageStart(), page.getPageSize());
    }

    /**
     * 查询页面成本统计页面参与人员下拉框
     * @param projectId
     * @return
     */
    @GetMapping("/project/users")
    @PreAuthorize("@ss.hasPermi('mh:cost:query')")
    public AjaxResult queryUserAll(@NotNull(message = "项目id不能为空") Long projectId) {
        return AjaxResult.success(costService.queryUserAll(projectId));
    }

    /**
     * 查询页面成本统计页面参与职位下拉框
     * @param projectId
     * @return
     */
    @GetMapping("/project/posts")
    @PreAuthorize("@ss.hasPermi('mh:cost:query')")
    public AjaxResult queryPostAll(@NotNull(message = "项目id不能为空") Long projectId) {
        return AjaxResult.success(costService.queryPostAll(projectId));
    }

}
