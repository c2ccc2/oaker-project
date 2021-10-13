package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/14 16:47
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserEntity {

    /** 用户ID */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /** 部门ID */
    @TableField(value = "dept_id")
    private Long deptId;

    /** 用户账号 */
    @TableField(value = "user_name")
    private String userName;

    /** 用户昵称/真实姓名 */
    @TableField(value = "nick_name")
    private String nickName;

    /** 用户类型（00系统用户） */
    @TableField(value = "user_type")
    private String userType;

    /** 用户邮箱 */
    @TableField(value = "email")
    private String email;

    /** 手机号码 */
    @TableField(value = "phonenumber")
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    @TableField(value = "sex")
    private String sex;

    /** 头像地址 */
    @TableField(value = "avatar")
    private String avatar;

    /** 密码 */
    @JsonIgnore
    @TableField(value = "password")
    private String password;

    /** 帐号状态（0正常 1停用） */
    @TableField(value = "status")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @TableField(value = "del_flag")
    private String delFlag;

    /** 最后登录IP */
    @TableField(value = "login_ip")
    private String loginIp;

    /** 最后登录时间 */
    @TableField(value = "login_date")
    private Date loginDate;

    /** 创建者 */
    @TableField(value = "create_by")
    private String createBy;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 更新者 */
    @TableField(value = "update_by")
    private String updateBy;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;

    /** 备注 */
    @TableField(value = "remark")
    private String remark;


}
