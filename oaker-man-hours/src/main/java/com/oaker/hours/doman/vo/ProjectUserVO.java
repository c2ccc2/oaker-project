package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 项目成员
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/10 13:45
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectUserVO", description = "项目人员")
public class ProjectUserVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("人员id")
    private Long userId;

    @ApiModelProperty("用户昵称/姓名")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("加入时间")
    private Date joinTime;

    @ApiModelProperty("岗位ID")
    private Long postId;

    @ApiModelProperty("岗位名称")
    private String postName;


}
