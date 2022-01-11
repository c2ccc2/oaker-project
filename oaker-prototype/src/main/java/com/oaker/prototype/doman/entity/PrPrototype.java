package com.oaker.prototype.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 原型
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/15 15:59
 */
@Data
@Accessors(chain = true)
@TableName("pr_prototype")
public class PrPrototype {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 名称 */
    @TableField(value = "name")
    private String name;

    /** 项目id */
    @TableField(value = "project_id")
    private Long projectId;

    /** 原型记录id */
    @TableField(value = "record_id")
    private Long recordId;

    /** 效果图id */
    @TableField(value = "sketch_id")
    private Long sketchId;

    /** 原型公开id */
    @TableField(value = "pub_id")
    private String pubId;

    /** 备注说明 */
    @TableField(value = "remark")
    private String remark;

    /** 是否删除：0未删除， 1已删除 */
    @TableField(value = "deleted")
    private Boolean deleted;

    /** 创建人 */
    @TableField(value = "create_user")
    private Long createUser;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 更新人 */
    @TableField(value = "update_user")
    private Long updateUser;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;

}
