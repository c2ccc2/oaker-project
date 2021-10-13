package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description : 项目用户工时填报详情
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/17 17:27
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectUserFillVO", description = "项目人员填报详情")
public class ProjectUserFillVO {

    @ApiModelProperty("填报记录")
    private List<UserFill> fills;

    @ApiModelProperty("缺报记录")
    private List<UserFill> unFills;


    @Data
    @ApiModel
    public static class UserFill {

        @ApiModelProperty("用户id")
        private Long userId;

        @ApiModelProperty("用户昵称/姓名")
        private String nickName;

        @ApiModelProperty("工时")
        private BigDecimal useHour;

        @ApiModelProperty("岗位名称")
        private String postName;

        @ApiModelProperty("创建时间")
        private Date createTime;

    }

}
