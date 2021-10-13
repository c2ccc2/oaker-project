package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/17 14:06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectHourStatListVO", description = "项目工时统计列表")
public class ProjectHourStatListVO {

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目状态")
    private String projectStatus;

    @ApiModelProperty("项目经理id")
    private Long projectManager;

    @ApiModelProperty("项目经理名称")
    private String projectManagerName;

    @ApiModelProperty("已用工时")
    private BigDecimal useHour;

    @ApiModelProperty("预估工时")
    private BigDecimal totalHour;

    @ApiModelProperty("昨天应报数量")
    private int yesMustFill;

    @ApiModelProperty("昨天上报数量")
    private int yesFill;

    @ApiModelProperty("今天应报数量")
    private int todayMustFill;

    @ApiModelProperty("今天上报数量")
    private int todayFill;


}
