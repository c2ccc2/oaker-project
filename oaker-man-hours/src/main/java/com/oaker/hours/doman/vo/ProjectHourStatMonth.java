package com.oaker.hours.doman.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description : 项目工时按月统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/18 11:47
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectHourStatMonth", description = "项目工时按月统计")
public class ProjectHourStatMonth {

    @ApiModelProperty("月份")
    private String month;

    @ApiModelProperty("用户id集合")
    private List<Long> users;

    @ApiModelProperty("总工时")
    private BigDecimal useHour;

    /** 用户id 计算过程中的临时变量 */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long userId;


}
