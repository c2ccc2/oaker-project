package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description :  用户工时详细模式
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/16 15:56
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourStatDestailVO", description = "用户工时统计详细模式")
public class UserHourStatDestailVO {

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报")
    private int status;

    @ApiModelProperty("项目详情")
    private List<UserHourStatVO.ProjectHour> projectHours;


}
