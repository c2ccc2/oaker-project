package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Description :  用户工时列表
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/15 10:36
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourListVO", description = "用户工时列表")
public class UserHourListVO {

    @ApiModelProperty("填报/缺报/请假 记录id")
    private Long id;

    @ApiModelProperty("填报时间")
    private Date createTime;

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6 调休")
    private int status;


}
