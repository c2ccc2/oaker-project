package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 首页登陆信息查询
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 14:35
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "IndexloginInfo", description = "首页登陆信息查询")
public class IndexloginInfo {

    @ApiModelProperty("用户名称")
    private String nickName;

    @ApiModelProperty("登陆时间")
    private Date loginDate;


}
