package com.oaker.web.controller.tool.pr;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.prototype.doman.vo.PrProtoRecordVO;
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
 * @Description : 原型历史记录api
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/19 14:10
 */
@RestController
@RequestMapping("/doc/pr/proto/record")
@Api(tags = "原型模块-项目原型记录", description = "项目原型记录相关接口")
public class PrProtoRecordApi {

    /**
     * 查看原型历史记录
     */
    @GetMapping("/list")
    @ApiOperation("项目原型列表查询")
    @ApiImplicitParam(name = "protoId", value = "项目原型id", dataType = "Long", required = true)
    public List<PrProtoRecordVO> queryList(Long protoId) {
        return Collections.singletonList(new PrProtoRecordVO());
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除原型历史记录")
    @ApiImplicitParam(name = "id", value = "原型历史记录id", dataType = "Long", required = true)
    public AjaxResult delete(Long id) {
        return AjaxResult.success();
    }

}
