package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Description : 用户请假记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/8 16:40
 */
@Data
@Accessors(chain = true)
@TableName("mh_user_leave")
public class MhUserLeave {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户id */
    @TableField(value = "user_id")
    private Long userId;

    /** 请假日期 */
    @TableField(value = "leave_date")
    private LocalDate leaveDate;

    /** 类型：1请假  2倒休 */
    @TableField(value = "leave_type")
    private int leaveType;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;


}
