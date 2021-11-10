package com.oaker.prototype.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 原型记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/18 13:47
 */
@Data
@Accessors(chain = true)
@TableName("pr_prototype_record")
public class PrProtoRecord {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 文件名称 */
    @TableField(value = "file_name")
    private String fileName;

    /** 项目原型id */
    @TableField(value = "prototype_id")
    private Long prototypeId;

    /** 原型地址 */
    @TableField(value = "prototype_url")
    private String prototypeUrl;

    /** 文件存放路径 */
    @TableField(value = "path")
    private String path;

    /** 创建人 */
    @TableField(value = "create_user")
    private Long createUser;

    /** 创建人 */
    @TableField(value = "create_time")
    private Date createTime;

}
