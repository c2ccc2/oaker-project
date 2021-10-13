package com.oaker.hours.doman.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description : 项目成员创建类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 15:03
 */
@Data
@ApiModel(value = "ProjectUser对象", description = "数据表：sys_project_user")
public class ProjectUserSaveDTO {

    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id", required = true)
    private Long projectId;

    @NotEmpty(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id集合", required = true)
    private List<Long> users;

}
