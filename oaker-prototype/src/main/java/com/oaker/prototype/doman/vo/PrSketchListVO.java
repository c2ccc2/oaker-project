package com.oaker.prototype.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 效果图列表查询返回类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/20 10:28
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PrSketchListVO", description = "效果图列表查询返回类")
public class PrSketchListVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("原型id")
    private Long prototypeId;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
