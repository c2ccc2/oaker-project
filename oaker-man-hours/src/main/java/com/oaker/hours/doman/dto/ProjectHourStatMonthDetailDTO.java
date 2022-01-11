package com.oaker.hours.doman.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Description : 项目工时统计按月统计列表详情查询参数类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/8 11:29
 */
@Data
@ApiModel(value = "项目工时统计按月统计列表详情查询参数类")
public class ProjectHourStatMonthDetailDTO {

    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id", required = true)
    private Long projectId;

    @ApiModelProperty(value = "月份  yyyy-MM 格式字符串")
    private String month;

    @ApiModelProperty(value = "日期")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @ApiModelProperty(value = "项目阶段")
    private String projectStatus;

    @ApiModelProperty(value = "人员id")
    private Long userId;

    @ApiModelProperty(value = "岗位id")
    private Long postId;

    @NotNull(message = "分页参数不能为空")
    private Integer pageNum;

    @NotNull(message = "分页参数不能为空")
    private Integer pageSize;

}
