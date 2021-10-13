package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description : 项目工时按人统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/18 14:22
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectHourStatUser", description = "项目工时按人统计")
public class ProjectHourStatUser {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户昵称/姓名")
    private String nickName;

    @ApiModelProperty("工时")
    private BigDecimal useHour;

    @ApiModelProperty("岗位名称")
    private String postName;


}
