package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Description : 总体统计列表发返回类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/10 14:21
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OverallHourListVO", description = "总体统计列表返回类")
public class OverallHourListVO {

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("上报人数")
    private int fillNum;

    @ApiModelProperty("缺报人数")
    private int unFillNum;

    @ApiModelProperty("请假人数")
    private int leaveNum;

    @ApiModelProperty("总工时")
    private BigDecimal totalHour;


}
