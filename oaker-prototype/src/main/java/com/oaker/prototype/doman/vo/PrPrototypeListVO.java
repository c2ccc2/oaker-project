package com.oaker.prototype.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Description : 原型列表类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/19 10:12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PrPrototypeListVO", description = "项目原型列表")
public class PrPrototypeListVO {

    /** id */
    @ApiModelProperty("id")
    private Long id;

    /** 名称 */
    @ApiModelProperty("名称")
    private String name;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Long projectId;

    /** 原型记录id */
    @ApiModelProperty("原型记录id")
    private Long recordId;

    /** url地址 */
    @ApiModelProperty("url地址")
    private String prototypeUrl;

    /** 备注说明 */
    @ApiModelProperty("备注说明")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private Long createUser;

    /** 创建人名称 */
    @ApiModelProperty("创建人名称")
    private String createUserName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 文档集合 */
    @ApiModelProperty("文档集合")
    private List<Doc> docList;


    @Data
    @Accessors(chain = true)
    @ApiModel(value = "Doc", description = "原型文档")
    public static class Doc {

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


}
