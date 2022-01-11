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

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("文档名称")
    private String docName;

    @ApiModelProperty("文档地址")
    private String docUrl;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
