package com.oaker.hours.doman.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @Description :  项目修改类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/8 14:15
 */
@Data
@ApiModel(value = "Project对象", description = "数据表：sys_project")
public class ProjectUpdateDTO {

    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id", required = true)
    private Long projectId;

    @NotBlank(message = "项目名称不能为空")
    @ApiModelProperty(value = "项目名称(长度要在1~20之间)", required = true)
    @Size(min = 1, max = 20, message = "项目名称长度要在1~20之间")
    private String projectName;

    @NotNull(message = "项目经理不能为空")
    @ApiModelProperty(value = "项目经理")
    private Long projectManager;

    @ApiModelProperty(value = "项目描述(长度不能超过500)")
    @Size(max = 500, message = "项目描述长度不能超过500")
    private String remark;

    @ApiModelProperty(value = "开始日期")
    @NotNull(message = "项目开始时间不能为空")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束日期")
    private LocalDate endDate;


}
