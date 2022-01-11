package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 项目成员
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 14:43
 */
@Data
@Accessors(chain = true)
@TableName("sys_project_user")
public class ProjectUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 项目id */
    @TableField(value = "project_id")
    private Long projectId;

    /** 人员id */
    @TableField(value = "user_id")
    private Long userId;

    /** 状态：1 正常  0 移除 */
    @TableField(value = "status")
    private Boolean status;

    /** 移除日期 */
    @TableField(value = "remove_time")
    private Date removeTime;

    /** 是否需要每日上报工时 0 不需要  1 需要 */
    @TableField(value = "everyday")
    private Boolean everyday;

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
