package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * 项目对象 sys_project
 *
 * @author `` 须尽欢 _____
 * @date 2021-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_project")
public class MhProject {
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @TableId(value = "project_id", type = IdType.AUTO)
    private Long projectId;

    /**
     * 项目名称
     */
    @TableField(value = "project_name")
    private String projectName;

    /**
     * 项目编码
     */
    @TableField(value = "project_code")
    private String projectCode;

    /**
     * 项目经理id
     */
    @TableField(value = "project_manager")
    private Long projectManager;

    /**
     * 项目状态 1 启用   0暂停
     */
    @TableField(value = "enable")
    private Boolean enable;

    /**
     * 项目状态：a 进行中，b运维，c 结束
     */
    @TableField(value = "project_status")
    private String projectStatus;

    /**
     * 项目简介
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 开始日期
     */
    @TableField(value = "start_date")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @TableField(value = "end_date")
    private LocalDate endDate;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
