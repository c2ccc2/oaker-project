package com.oaker.web.controller.prototype;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.enums.BusinessType;
import com.oaker.prototype.doman.vo.PrSketchListVO;
import com.oaker.prototype.doman.vo.SketchFileVO;
import com.oaker.prototype.service.impl.PrSketchServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @Description : 效果图
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/17 10:59
 */
@Validated
@RestController
@RequestMapping("/pr/sketch")
public class PrSketchController extends BaseController {

    @Resource
    private PrSketchServiceImpl sketchService;

    /**
     * 上传效果图
     * @param file
     * @param prototypeId
     * @return
     */
    @PostMapping("/upload")
    @PreAuthorize("@ss.hasPermi('pr:sketch:upload')")
    @Log(title = "效果图", businessType = BusinessType.INSERT)
    public AjaxResult uploadFile(@NotNull(message = "上传文件不能为空") MultipartFile file
            , @NotNull(message = "原型id不能为空") Long prototypeId) {
        try {
            sketchService.upload(file, prototypeId);
        } catch (Exception e) {
            logger.error("【上传效果图失败，失败原因：】" + e.getMessage());
            return AjaxResult.error("文件上传失败");
        }
        return AjaxResult.success();
    }

    /**
     * 查询原型图下的效果图记录
     * @param prototypeId
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('pr:sketch:upload')")
    public AjaxResult query(@NotNull(message = "原型id不能为空") Long prototypeId) {
        List<PrSketchListVO> sketchListVOS = sketchService.queryByPrototypeId(prototypeId);
        return AjaxResult.success(sketchListVOS);
    }

    /**
     * 删除效果图
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermi('pr:sketch:upload')")
    public AjaxResult delete(@NotNull(message = "id不能为空")Long id) {
        try {
            boolean delete = sketchService.delete(id);
            return toAjax(delete);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("【效果图-删除 失败原因：】" + e.getMessage());
        }
        return AjaxResult.error();
    }


    /**
     * 效果图记录id获取效果图文件
     * @param sketchId
     * @return
     */
    @GetMapping("/files")
    @PreAuthorize("@ss.hasPermi('pr:proto:list')")
    public AjaxResult querySketchFiles(@NotNull(message = "效果图记录id不能为空") Long sketchId) {
        List<SketchFileVO> sketchFileVOS = sketchService.querySketchFileBySketchId(sketchId);
        return AjaxResult.success(sketchFileVOS);
    }

}
