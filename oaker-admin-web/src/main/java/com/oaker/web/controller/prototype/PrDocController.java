package com.oaker.web.controller.prototype;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.enums.BusinessType;
import com.oaker.prototype.service.impl.PrDocServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/16 17:52
 */
@Validated
@RestController
@RequestMapping("/pr/doc")
public class PrDocController extends BaseController {

    @Resource
    private PrDocServiceImpl prDocService;

    /**
     * 删除原型文档
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermi('pr:doc:delete')")
    @Log(title = "原型文档", businessType = BusinessType.INSERT)
    public AjaxResult delete(@NotNull(message = "id不能为空") Long id) {
        boolean result = prDocService.deleteDoc(id);
        if (result) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 根据原型id查询文档
     * @param protoId
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("@ss.hasPermi('pr:doc:query')")
    public AjaxResult queryList(@NotNull(message = "原型id不能为空") Long protoId) {
        return AjaxResult.success(prDocService.queryList(protoId));
    }

}
