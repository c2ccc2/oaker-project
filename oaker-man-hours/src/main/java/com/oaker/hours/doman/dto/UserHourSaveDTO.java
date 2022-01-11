package com.oaker.hours.doman.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Description : 用户填报工时
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 13:55
 */
@Data
@ApiModel(value = "UserHourSaveDTO对象", description = "用户填报工时")
public class UserHourSaveDTO {

    @ApiModelProperty(value = "填报日期")
    private LocalDate date = LocalDate.now();

    @Valid
    @NotEmpty(message = "工时详情不能为空")
    @ApiModelProperty(value = "工时详情")
    private List<ProjectHour> projectHours;

    @Data
    @Validated
    @ApiModel(description = "用户填报工时详情")
    public static class ProjectHour {

        @NotNull(message = "项目id不能为空")
        @ApiModelProperty(value = "项目id", required = true)
        private Long projectId;

        @NotNull(message = "填报工时不能为空")
        @ApiModelProperty(value = "填报工时", required = true)
        private BigDecimal hour;

        @ApiModelProperty(value = "日报内容")
        private String daily;

    }


}
