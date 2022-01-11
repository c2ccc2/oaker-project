package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Description : 项目填报日历
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/14 15:57
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectFillCalendar", description = "项目填报日历返回类")
public class ProjectFillCalendar {

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("是否为节假日")
    private boolean festivals;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("上报人员id集合")
    private List<Long> users;

    @ApiModelProperty("上报总工时")
    private BigDecimal hours;


}
