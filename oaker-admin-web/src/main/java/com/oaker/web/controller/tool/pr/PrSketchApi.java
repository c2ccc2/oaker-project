package com.oaker.web.controller.tool.pr;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.prototype.doman.vo.PrSketchListVO;
import com.oaker.prototype.doman.vo.SketchFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

/**
 * @Description : 效果图
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/17 16:17
 */
@RestController
@RequestMapping("/doc/pr/sketch")
@Api(tags = "原型模块-效果图", description = "效果图相关接口")
public class PrSketchApi {


    @PostMapping("/upload")
    @ApiOperation("效果图 上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "MultipartFile", required = true),
            @ApiImplicitParam(name = "prototypeId", value = "原型数据id", dataType = "Long", required = true)
    })
    public AjaxResult uploadFile(MultipartFile file, Long prototypeId) {
        return AjaxResult.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询原型图下的效果图记录")
    @ApiImplicitParam(name = "prototypeId", value = "原型数据id", dataType = "Long", required = true)
    public List<PrSketchListVO> query(Long prototypeId) {
        return Collections.singletonList(new PrSketchListVO());
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除效果图")
    @ApiImplicitParam(name = "id", value = "效果图id", dataType = "Long", required = true)
    public AjaxResult delete(Long id) {
        return AjaxResult.success();
    }

    @GetMapping("/files")
    @ApiOperation("效果图记录id获取效果图文件")
    @ApiImplicitParam(name = "sketchId", value = "效果图记录id", dataType = "Long", required = true)
    public List<SketchFileVO> querySketchFiles(Long sketchId) {
        return Collections.singletonList(new SketchFileVO());
    }


}
