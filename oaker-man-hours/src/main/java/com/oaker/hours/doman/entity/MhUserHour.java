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
 * 工时填报表(MhUserHour)实体类
 *
 * @author makejava
 * @since 2021-09-13 14:06:55
 */
@Data
@Accessors(chain = true)
@TableName("mh_user_hour")
public class MhUserHour implements Serializable {
    

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 人员id */
    @TableField(value = "user_id")
    private Long userId;

    /** 填报日期 */
    @TableField(value = "fill_date")
    private LocalDate fillDate;

    /** 总工时 */
    @TableField(value = "total_hour")
    private BigDecimal totalHour;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

}