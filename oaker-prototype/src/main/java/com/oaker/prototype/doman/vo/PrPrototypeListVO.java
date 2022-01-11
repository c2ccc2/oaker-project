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

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("原型记录id")
    private Long recordId;

    @ApiModelProperty("url地址")
    private String prototypeUrl;

    @ApiModelProperty("备注说明")
    private String remark;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("文档集合")
    private List<Doc> docList;

    @ApiModelProperty("原型图记录Id")
    private Long sketchId;


    @Data
    @Accessors(chain = true)
    @ApiModel(value = "Doc", description = "原型文档")
    public static class Doc {

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

}
