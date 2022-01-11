package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Description : 用户工时统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/16 12:07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourStatVO", description = "用户工时统计")
public class UserHourStatVO {

    @ApiModelProperty("填报/缺报/请假 记录id")
    private Long id;

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("姓名/昵称")
    private String nickName;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6 调休")
    private int status;

    @ApiModelProperty("填报时间")
    private Date fillTime;

    @ApiModelProperty("项目详情")
    private List<ProjectHour> projectHours;

    @Data
    @ApiModel(value = "projectHour", description = "用户工时统计-项目")
    public static class ProjectHour {

        @ApiModelProperty("项目id ")
        private Long projectId;

        @ApiModelProperty("项目名称")
        private String projectName;

        @ApiModelProperty("工时")
        private BigDecimal useHour;

        @ApiModelProperty("日报内容")
        private String daily;

    }


}
