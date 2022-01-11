package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description : 首页上报信息
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 11:33
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "IndexFillVO", description = "首页上报信息")
public class IndexFillVO {

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6 调休")
    private Integer fillStatus;

    @ApiModelProperty("今日上报工时")
    private BigDecimal dayHour = BigDecimal.ZERO;

    @ApiModelProperty("本月上报次数")
    private int monthFill;

    @ApiModelProperty("本月缺报次数")
    private int monthMissFill;

    @ApiModelProperty("本月请假天数")
    private int leaveNum;

    @ApiModelProperty("本月倒休天数")
    private int offDutyNum;


}
