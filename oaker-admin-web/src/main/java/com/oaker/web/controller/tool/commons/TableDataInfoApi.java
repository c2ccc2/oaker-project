package com.oaker.web.controller.tool.commons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Description :  分页返回对象说明类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/8 14:48
 */
@ApiModel(value = "分页对象统一返回类")
public class TableDataInfoApi<T> {

    /**
     * 总记录数
     */
    @ApiModelProperty(required = true,
            value = "总记录数",
            dataType = "int",
            example = "200", position = 2)
    private long total;

    /**
     * 列表数据
     */
    @ApiModelProperty(required = true,
            value = "列表数据",
            dataType = "String",
            example = "data", position = 3)
    private List<T> rows;

    /**
     * 消息状态码
     */
    @ApiModelProperty(required = true,
            value = "消息状态码",
            dataType = "int",
            example = "200", position = 0)
    private int code;

    /**
     * 消息内容
     */
    @ApiModelProperty(required = true,
            value = "消息内容",
            dataType = "String",
            example = "成功", position = 1)
    private String msg;


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
