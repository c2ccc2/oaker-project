package com.oaker.hours.doman.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description : 人员成本设置类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 11:39
 */
@Data
@ApiModel(value = "MhUserCostDTO 设置人员成本对象")
public class MhUserCostDTO {

    @NotNull(message = "人员id不能为空")
    @ApiModelProperty(value = "岗位id", required = true)
    private Long userId;

    @NotNull(message = "人员成本不能为空")
    @ApiModelProperty(value = "岗位成本", required = true)
    private BigDecimal cost;

}
