package com.oaker.web.controller.prototype;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.page.PageDomain;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.enums.BusinessType;
import com.oaker.prototype.doman.columns.PrColumns;
import com.oaker.prototype.service.impl.PrDocServiceImpl;
import com.oaker.prototype.service.impl.PrPrototypeServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @Description : 原型
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/16 15:28
 */
@Validated
@RestController
@RequestMapping("/pr/proto")
public class PrototypeController extends BaseController {

    @Resource
    private PrDocServiceImpl prDocService;

    @Resource
    private PrPrototypeServiceImpl prPrototypeService;

    /**
     * 新增项目原型
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('pr:proto:add')")
    @Log(title = "原型", businessType = BusinessType.INSERT)
    public AjaxResult create(@NotBlank(message = "名称不能为空") String name
            , @NotNull(message = "项目id不能为空") Long projectId) {
        Long id = prPrototypeService.create(name, projectId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("id", id);
        return ajax;
    }

    /**
     * 修改项目原型
     */
    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermi('pr:proto:update')")
    @Log(title = "原型", businessType = BusinessType.UPDATE)
    public AjaxResult update(@NotNull(message = "id不能为空") Long id, @NotBlank(message = "名称不能为空") String name) {
        return toAjax(prPrototypeService.modify(id, name));
    }



    /**
     * 原型&文档 上传接口
     */
    @PostMapping("/upload")
    @PreAuthorize("@ss.hasPermi('pr:proto:upload')")
    @Log(title = "原型", businessType = BusinessType.INSERT)
    public AjaxResult uploadFile(@NotNull(message = "上传文件不能为空") MultipartFile file
            ,@NotNull(message = "原型id不能为空") Long prototypeId
            ,@NotBlank(message = "上传类型不能为空") String upType) {
        Long id = null;
        try {
            // 上传原型文档文件
            if (Objects.equals(upType, "doc")) {
                id = prDocService.upload(file, prototypeId);
            }
            // 上传原型文件
            if (Objects.equals(upType, "proto")) {
                id = prPrototypeService.upload(file, prototypeId);
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put(PrColumns.id, id);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 项目原型列表查询
     * @param projectId
     * @return
     */
    @GetMapping(value = "/list")
    @PreAuthorize("@ss.hasPermi('pr:proto:list')")
    public TableDataInfo query(@NotNull(message = "项目id不能为空") Long projectId){
        PageDomain pageDomain = getPageDomain();
        return prPrototypeService.query(projectId, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 删除原型信息
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    @PreAuthorize("@ss.hasPermi('pr:proto:delete')")
    @Log(title = "原型", businessType = BusinessType.DELETE)
    public AjaxResult delete(@NotNull(message = "id不能为空") Long id) {
        boolean delete = prPrototypeService.delete(id);
        if (delete) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }


}
