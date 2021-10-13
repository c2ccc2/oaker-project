package com.oaker.hours.doman.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @Description :  项目创建类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/6 16:44
 */
@Data
@ApiModel(value = "Project对象", description = "数据表：sys_project")
public class ProjectSaveDTO {

    @NotBlank(message = "项目名称不能为空")
    @ApiModelProperty(value = "项目名称(长度要在1~20之间)", required = true)
    @Size(min = 1, max = 20, message = "项目名称长度要在1~20之间")
    private String projectName;

    @NotBlank(message = "项目编码不能为空")
    @ApiModelProperty(value = "项目编码(长度要在4~64之间)", required = true)
    @Size(min = 4, max = 64, message = "项目名称长度要在4~64之间")
    private String projectCode;

    @NotNull(message = "预计投入工时不能为空")
    @ApiModelProperty(value = "预计投入工时", required = true)
    private BigDecimal duration;

    @NotNull(message = "项目经理不能为空")
    @ApiModelProperty(value = "项目经理", required = true)
    private Long projectManager;

    @Size(max = 500, message = "项目描述长度不能超过500")
    @ApiModelProperty(value = "项目描述(长度不能超过500)")
    private String remark;


}
