package com.oaker.hours.doman.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 人员成本
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 10:22
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MhUserCostVO", description = "人员成本")
public class MhUserCostVO {

    @ApiModelProperty("人员id")
    private Long userId;

    @ApiModelProperty("人员名称")
    private String nickName;

    @ApiModelProperty("人员成本")
    private BigDecimal cost = BigDecimal.ZERO;

    @ApiModelProperty("岗位id")
    private Long postId;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新人")
    private Long updateUser;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
