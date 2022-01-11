package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description : 首页项目统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 9:33
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "IndexProjectStatVO", description = "首页项目统计")
public class IndexProjectStatVO {

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目编码")
    private String projectCode;

    @ApiModelProperty("项目经理id")
    private Long projectManager;

    @ApiModelProperty("项目经理名称")
    private String projectManagerName;

    @ApiModelProperty("项目状态")
    private String projectStatus;

    @ApiModelProperty("预估投入工时")
    private BigDecimal manHour;

    @ApiModelProperty("已投入工时")
    private BigDecimal useHour;

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6 调休")
    private Integer fillStatus;

    @ApiModelProperty("今日上报工时")
    private BigDecimal dayHour = BigDecimal.ZERO;


}
