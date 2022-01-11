package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Description : 成本统计列表查询类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/8 11:08
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MhCostListVO", description = "成本统计列表查询类")
public class MhCostListVO {

    @ApiModelProperty("记录id")
    private Long id;

    @ApiModelProperty("人员id")
    private Long userId;

    @ApiModelProperty("人员姓名/昵称")
    private String nickName;

    @ApiModelProperty("所用工时")
    private BigDecimal useHour;

    @ApiModelProperty("岗位成本")
    private BigDecimal postCost;

    @ApiModelProperty("人员成本")
    private BigDecimal userCost;

    @ApiModelProperty("日期")
    private LocalDate costDate;

    /** 项目状态：a 进行中，b运维，c 结束 */
    @ApiModelProperty("项目状态：a 进行中，b运维，c 结束")
    private String projectStatus;

    @ApiModelProperty("职位id")
    private Long postId;

    @ApiModelProperty("职位名称")
    private String postName;

    @ApiModelProperty("创建时间")
    private Date createTime;


}
