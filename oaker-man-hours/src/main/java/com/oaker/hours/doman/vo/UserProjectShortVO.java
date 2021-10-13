package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description : 用户参与项目
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 12:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserProjectShortVO", description = "成员参与的项目")
public class UserProjectShortVO {

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目状态")
    private String projectStatus;

}
