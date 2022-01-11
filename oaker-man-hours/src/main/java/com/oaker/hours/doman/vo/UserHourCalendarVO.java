package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/13 17:19
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourCalendarVO", description = "填报记录月历")
public class UserHourCalendarVO {

    @ApiModelProperty("填报/缺报/请假 记录id")
    private Long id;

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6 调休")
    private int status;

    @ApiModelProperty("填报工时")
    private BigDecimal hour = BigDecimal.ZERO;

}
