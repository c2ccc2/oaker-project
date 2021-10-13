package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Description : 工时详情
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/15 18:50
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourDetailVO", description = "工时详情")
public class UserHourDetailVO {

    /** id */
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("工时填报表id")
    private Long hourId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("项目id ")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("工时")
    private BigDecimal useHour;

    @ApiModelProperty("填报日期")
    private LocalDate fillDate;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;


}
