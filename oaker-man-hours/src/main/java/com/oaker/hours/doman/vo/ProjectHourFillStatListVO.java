package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Description : 工时上报记录统计返回类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/9 16:06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectHourFillStatListVO", description = "工时上报记录统计返回类")
public class ProjectHourFillStatListVO {

    @ApiModelProperty("日期")
    private LocalDate statDate;

    @ApiModelProperty("每日上报组上报数量")
    private int everydayFill;

    @ApiModelProperty("每日上报组缺报人数")
    private int everydayMissNum;

    @ApiModelProperty("全部上报数量")
    private int allFill;

    @ApiModelProperty("总上报工时")
    private BigDecimal totalHour;


}
