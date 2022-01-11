package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 岗位成本
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 10:11
 */
@Data
@Accessors(chain = true)
@TableName("mh_post_cost")
public class MhPostCost {

    /** 岗位id */
    @TableId(value = "post_id")
    private Long postId;

    /** 岗位成本 */
    @TableField(value = "cost")
    private BigDecimal cost;

    /** 创建人 */
    @TableField(value = "create_user")
    private Long createUser;

    /** 创建时间 */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新人 */
    @TableField(value = "update_user")
    private Long updateUser;

    /** 更新时间 */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
