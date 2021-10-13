package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @Description : 用户缺报详情表
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 17:07
 */
@Data
@Accessors(chain = true)
@TableName("mh_user_hour_miss_detail")
public class MhUserHourMissDetail {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "miss_id")
    private Long missId;

    /** 项目id */
    @TableField(value = "project_id")
    private Long projectId;

    /** 人员id */
    @TableField(value = "user_id")
    private Long userId;

    /** 缺报日期 */
    @TableField(value = "miss_date")
    private LocalDate missDate;


}
