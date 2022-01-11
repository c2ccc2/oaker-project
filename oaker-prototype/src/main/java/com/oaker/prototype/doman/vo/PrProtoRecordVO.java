package com.oaker.prototype.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 原型历史记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/19 13:53
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PrProtoRecordVO", description = "原型历史记录")
public class PrProtoRecordVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("原型地址")
    private String prototypeUrl;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @ApiModelProperty("创建时间")
    private Date createTime;


}
