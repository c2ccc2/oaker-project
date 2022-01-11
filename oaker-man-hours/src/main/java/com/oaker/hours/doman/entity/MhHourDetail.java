package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 工时填报详情表(MhHourDetail)实体类
 *
 * @author makejava
 * @since 2021-09-13 14:08:26
 */
@Data
@Accessors(chain = true)
@TableName("mh_hour_detail")
public class MhHourDetail implements Serializable {
    
    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 工时填报表id */
    @TableField(value = "hour_id")
    private Long hourId;

    /** 用户id */
    @TableField(value = "user_id")
    private Long userId;

    /** 项目id */
    @TableField(value = "project_id")
    private Long projectId;

    /** 工时 */
    @TableField(value = "use_hour")
    private BigDecimal useHour;

    /** 填报日期 */
    @TableField(value = "fill_date")
    private LocalDate fillDate;

    /** 项目状态：a 进行中，b运维，c 结束 */
    @TableField(value = "project_status")
    private String projectStatus;

    /** 是否为每日上报工时记录 */
    @TableField(value = "everyday")
    private Boolean everyday;

    /** 日报内容 */
    @TableField(value = "daily")
    private String daily;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

}