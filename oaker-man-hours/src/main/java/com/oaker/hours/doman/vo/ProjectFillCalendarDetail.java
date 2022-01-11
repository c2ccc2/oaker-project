package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/14 17:02
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectFillCalendarDetail", description = "项目填报日历详情返回类")
public class ProjectFillCalendarDetail {

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("是否为节假日")
    private boolean festivals;

    @ApiModelProperty("填报记录")
    private List<UserFill> fills;

    @ApiModelProperty("缺报记录")
    private List<UserFill> unFills;

    @Data
    @ApiModel
    public static class UserFill {

        @ApiModelProperty("用户id")
        private Long userId;

        @ApiModelProperty("用户昵称/姓名")
        private String nickName;

        @ApiModelProperty("工时")
        private BigDecimal useHour;

        @ApiModelProperty("岗位名称")
        private String postName;

        @ApiModelProperty("日报内容")
        private String daily;

        @ApiModelProperty("是否为每日上报组")
        private boolean everyday;

        @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6调休")
        private int fillStatus;

    }


}
