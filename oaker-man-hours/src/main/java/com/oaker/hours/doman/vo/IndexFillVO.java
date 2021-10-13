package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description : 首页上报信息
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 11:33
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "IndexFillVO", description = "首页上报信息")
public class IndexFillVO {

    @ApiModelProperty("今日是否上报(1 未上报  2 已上报 3 无需上报)")
    private int dayFill;

    @ApiModelProperty("本月上报次数")
    private int monthFill;

    @ApiModelProperty("本月缺报次数")
    private int monthMissFill;


}
