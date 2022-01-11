package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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

    @ApiModelProperty("记录id")
    private Long id;

    @ApiModelProperty("人员id")
    private Long userId;

    @ApiModelProperty("人员姓名/昵称")
    private String nickName;

    @ApiModelProperty("所用工时")
    private BigDecimal useHour;

    @ApiModelProperty("日期")
    private LocalDate fillDate;

    /** 项目状态：a 进行中，b运维，c 结束 */
    @ApiModelProperty("项目状态：a 进行中，b运维，c 结束")
    private String projectStatus;

    @ApiModelProperty("职位id")
    private Long postId;

    @ApiModelProperty("职位名称")
    private String postName;

    @ApiModelProperty("所属工作组：是否每日上报人员")
    private Boolean everyday;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
