package com.oaker.prototype.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 文档列表查询类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/22 9:25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PrDocVO", description = "文档列表查询类")
public class PrDocVO {

    /** id */
    @ApiModelProperty("id")
    private Long id;

    /** 文档名称 */
    @ApiModelProperty("文档名称")
    private String docName;

    /** 文档地址 */
    @ApiModelProperty("文档地址")
    private String docUrl;

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
