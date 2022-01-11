package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 人员成本
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 13:49
 */
@Data
@Accessors(chain = true)
@TableName("mh_user_cost")
public class MhUserCost {

    /** 人员id */
    @TableId(value = "user_id")
    private Long userId;

    /** 人员成本 */
    @TableField(value = "cost")
    private BigDecimal cost;

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
