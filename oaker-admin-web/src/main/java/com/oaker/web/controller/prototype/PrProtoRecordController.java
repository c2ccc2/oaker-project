package com.oaker.web.controller.prototype;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.prototype.doman.vo.PrProtoRecordVO;
import com.oaker.prototype.service.impl.PrProtoRecordServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/19 14:06
 */
@Validated
@RestController
@RequestMapping("/pr/proto/record")
public class PrProtoRecordController {

    @Resource
    private PrProtoRecordServiceImpl prProtoRecordService;

    /**
     * 查询原型历史记录
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('pr:proto:record:list')")
    public AjaxResult queryList(@NotNull(message = "原型id不能为空") Long protoId) {
        List<PrProtoRecordVO> list = prProtoRecordService.queryByProtoId(protoId);
        return AjaxResult.success(list);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermi('pr:proto:record:delete')")
    public AjaxResult delete(@NotNull(message = "id不能为空") Long id) {
        try {
            boolean result = prProtoRecordService.deleteRecord(id);
            if (result) {
                return AjaxResult.success();
            }
        } catch (IOException e) {
            AjaxResult.error("系统异常，删除失败！");
        }
        return AjaxResult.error("删除失败");
    }


}
