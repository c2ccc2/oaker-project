package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description : 用户工时-按项目统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/23 17:07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "projectHour", description = "用户工时统计-项目")
public class UserHourProjectStat {

    @ApiModelProperty("项目id ")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("工时")
    private BigDecimal useHour;

}
