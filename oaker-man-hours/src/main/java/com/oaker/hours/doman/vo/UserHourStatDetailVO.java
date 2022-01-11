package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Description :  用户工时详细模式
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/16 15:56
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourStatDetailVO", description = "用户工时统计详细模式")
public class UserHourStatDetailVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名称")
    private String nickName;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("工时")
    private BigDecimal useHour;

    @ApiModelProperty("填报日期")
    private LocalDate fillDate;

    @ApiModelProperty("项目状态")
    private String projectStatus;

    @ApiModelProperty("是否为每日上报工时记录")
    private Boolean everyday;

    @ApiModelProperty("填报时间")
    private Date createTime;


}
