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

    /** id */
    @ApiModelProperty("id")
    private Long id;

    /** 文件名称 */
    @ApiModelProperty("文件名称")
    private String fileName;

    /** 原型地址 */
    @ApiModelProperty("原型地址")
    private String prototypeUrl;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private Long createUser;

    /** 创建人名称 */
    @ApiModelProperty("创建人名称")
    private String createUserName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;


}
