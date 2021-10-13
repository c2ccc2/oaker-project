package com.oaker.hours.doman.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/8 11:07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MhProjectListVO", description = "项目管理列表返回类")
public class MhProjectListVO {

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目编码")
    private String projectCode;

    @ApiModelProperty("项目经理id")
    private Long projectManager;

    @ApiModelProperty("项目经理名称")
    private String projectManagerName;

    @ApiModelProperty("项目状态")
    private String projectStatus;

    @ApiModelProperty("项目状态名称")
    private String projectStatusName;

    @ApiModelProperty("预估投入工时")
    private BigDecimal manHour;

    @ApiModelProperty("项目简介")
    private String remark;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
