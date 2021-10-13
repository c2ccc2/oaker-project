package com.oaker.web.controller.tool.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Description : 字典查询
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 10:31
 */
@Api(tags = "字典接口", description = "字典查询相关接口")
@RestController
@RequestMapping("/doc/system/dict/data")
public class SysDictDataApi {
    
    @GetMapping(value = "/type/{dictType}")
    @ApiOperation("根据字典类型查询字典数据信息")
    @ApiImplicitParam(name = "dictType", value = "字典类型", dataType = "String", paramType = "path", required = true)
    public List<DictData> dictType(String dictType) {
        DictData dictData = new DictData();
        return Collections.singletonList(dictData);
    }


    @ApiModel(value = "DictData", description = "字典信息实体")
    class DictData {
       
        @ApiModelProperty("字典编码")
        private Long dictCode;
        
        @ApiModelProperty("字典排序")
        private Long dictSort;

        @ApiModelProperty("字典标签")
        private String dictLabel;

        @ApiModelProperty("字典键值")
        private String dictValue;

        @ApiModelProperty("字典类型")
        private String dictType;

        @ApiModelProperty("样式属性（其他样式扩展）")
        private String cssClass;

        @ApiModelProperty("表格字典样式")
        private String listClass;

        @ApiModelProperty("是否默认（Y是 N否）")
        private String isDefault;

        @ApiModelProperty("状态（0正常 1停用）")
        private String status;

        public Long getDictCode() {
            return dictCode;
        }

        public void setDictCode(Long dictCode) {
            this.dictCode = dictCode;
        }

        public Long getDictSort() {
            return dictSort;
        }

        public void setDictSort(Long dictSort) {
            this.dictSort = dictSort;
        }

        public String getDictLabel() {
            return dictLabel;
        }

        public void setDictLabel(String dictLabel) {
            this.dictLabel = dictLabel;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }

        public String getCssClass() {
            return cssClass;
        }

        public void setCssClass(String cssClass) {
            this.cssClass = cssClass;
        }

        public String getListClass() {
            return listClass;
        }

        public void setListClass(String listClass) {
            this.listClass = listClass;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    
    
}
