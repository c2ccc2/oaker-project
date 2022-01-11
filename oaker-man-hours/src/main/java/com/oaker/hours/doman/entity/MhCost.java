package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Description : 人员成本记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 17:23
 */
@Data
@Accessors(chain = true)
@TableName("mh_cost")
public class MhCost {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 项目id */
    @TableField(value = "project_id")
    private Long projectId;

    /** 用户id */
    @TableField(value = "user_id")
    private Long userId;

    /** 所用工时 */
    @TableField(value = "use_hour")
    private BigDecimal useHour;

    /** 岗位成本 */
    @TableField(value = "post_cost")
    private BigDecimal postCost;

    /** 人员成本 */
    @TableField(value = "user_cost")
    private BigDecimal userCost;

    /** 日期 */
    @TableField(value = "cost_date")
    private LocalDate costDate;

    /** 项目状态：a 进行中，b运维，c 结束 */
    @TableField(value = "project_status")
    private String projectStatus;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

}
