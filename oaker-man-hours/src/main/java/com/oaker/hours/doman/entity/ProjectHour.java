package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 13:41
 */
@Data
@TableName("mh_project_hour")
public class ProjectHour {

    /** 项目id */
    @TableId(value = "project_id")
    private Long projectId;

    /** 预估投入工时 */
    @TableField(value = "man_hour")
    private BigDecimal manHour;

    /** 已用工时 */
    @TableField(value = "use_hour")
    private BigDecimal useHour;

}
