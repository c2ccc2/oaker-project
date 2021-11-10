package com.oaker.prototype.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 原型文档
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/16 16:23
 */
@Data
@Accessors(chain = true)
@TableName("pr_doc")
public class PrDoc {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 文档名称 */
    @TableField(value = "doc_name")
    private String docName;

    /** 原型id */
    @TableField(value = "prototype_id")
    private Long prototypeId;

    /** 文档地址 */
    @TableField(value = "doc_url")
    private String docUrl;

    /** 创建人 */
    @TableField(value = "create_user")
    private Long createUser;

    /** 文件存放路径 */
    @JsonIgnore
    @TableField(value = "path")
    private String path;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

}
