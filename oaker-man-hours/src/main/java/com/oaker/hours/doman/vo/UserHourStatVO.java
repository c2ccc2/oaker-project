package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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

    @ApiModelProperty("上报天数")
    private int fillNum;

    @ApiModelProperty("应报天数")
    private int mustFillNum;

    @ApiModelProperty("缺报天数")
    private int missFillNum;

    @ApiModelProperty("总工时")
    private BigDecimal totalHour;

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

    }


}
