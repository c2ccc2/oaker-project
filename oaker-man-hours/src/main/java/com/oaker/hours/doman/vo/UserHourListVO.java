package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Description :  用户工时列表
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/15 10:36
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourListVO", description = "用户工时列表")
public class UserHourListVO {

    @ApiModelProperty("填报/缺报 记录id")
    private Long id;

    @ApiModelProperty("填报时间")
    private Date createTime;

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日")
    private int status;


    @Getter
    public enum StatusEnum {

        /** 已填报 */
        FILLED(1),
        /** 未填报 */
        NOT_FILLED(2),
        /** 不需填报 */
        NO_NEED(3),
        /** 节假日 */
        FESTIVAL(4);

        private int status;

        StatusEnum(int status) {
            this.status = status;

        }
    }


}
