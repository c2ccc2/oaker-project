package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Description : 项目工时按月统计详情
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/18 15:22
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectHourStatMonthDetail", description = "项目工时按月统计详情")
public class ProjectHourStatMonthDetail {

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("用户工时统计")
    private List<UserStatHour> userStatHours;

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "ProjectHourStatUser", description = "项目工时按月统计人员详情")
    public static class UserStatHour {

        @ApiModelProperty("用户id")
        private Long userId;

        @ApiModelProperty("用户昵称/姓名")
        private String nickName;

        @ApiModelProperty("工时")
        private BigDecimal useHour;

    }



}
