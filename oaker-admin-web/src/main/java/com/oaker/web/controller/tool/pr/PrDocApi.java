package com.oaker.web.controller.tool.pr;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.prototype.doman.vo.PrDocVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Description : 原型文档api
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/18 10:30
 */
@RestController
@RequestMapping("/doc/pr/doc")
@Api(tags = "原型模块-原型文档", description = "原型文档管理相关接口")
public class PrDocApi {

    @DeleteMapping("/delete")
    @ApiOperation("删除原型文档")
    @ApiImplicitParam(name = "id", value = "原型文档id", dataType = "Long", required = true)
    public AjaxResult delete(Long id) {
        return AjaxResult.success();
    }

    @GetMapping("/query")
    @ApiOperation("根据原型id查询文档")
    @ApiImplicitParam(name = "protoId", value = "原型数据id", dataType = "Long", required = true)
    public List<PrDocVO> queryList(Long protoId) {
        return Collections.singletonList(new PrDocVO());
    }

}
