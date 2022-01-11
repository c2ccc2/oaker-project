package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description : 成本统计返回类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 15:42
 */
@Data
@ApiModel(value = "CostConfigVO", description = "成本统计返回类")
public class CostConfigVO {

    @ApiModelProperty("岗位统计开关")
    private Boolean postCost;

    @ApiModelProperty("人员统计开关")
    private Boolean userCost;

}
