package com.oaker.web.controller.tool.pr;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.prototype.doman.vo.PrPrototypeListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

/**
 * @Description : 原型文档
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/16 15:32
 */
@RestController
@RequestMapping("/doc/pr/proto")
@Api(tags = "原型模块-原型管理", description = "原型管理相关接口")
public class PrototypeApi {

    /**
     * 新增原型数据
     */
    @PostMapping("/create")
    @ApiOperation("新增原型数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = true)
    })
    public AjaxResult create(String name, Long projectId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("id", 2);
        return ajax;
    }

    /**
     * 修改项目原型
     */
    @PutMapping("/update")
    @ApiOperation("修改项目原型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目原型id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = true)
    })
    public AjaxResult update(Long id, String name) {
        return AjaxResult.success();
    }

    /**
     * 原型&文档 上传接口
     */
    @PostMapping("/upload")
    @ApiOperation("原型&文档 上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "MultipartFile", required = true),
            @ApiImplicitParam(name = "prototypeId", value = "原型数据id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "upType", value = "上传类型：doc为文档， proto为原型文件", dataType = "String", required = true)
    })
    public AjaxResult uploadFile(MultipartFile file, Long prototypeId, String upType) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("id", 2);
        return ajax;
    }

    /**
     * 项目原型列表查询
     * @param projectId
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation("项目原型列表查询")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public List<PrPrototypeListVO> query(Long projectId){
        return Collections.singletonList(new PrPrototypeListVO());
    }


    /**
     * 删除原型信息
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    @ApiOperation("删除原型信息")
    @ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = true)
    public AjaxResult delete(Long id) {
        return AjaxResult.success();
    }

}
