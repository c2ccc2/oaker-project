package com.oaker.hours.doman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Description : 用户填报记录导出类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/15 14:15
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserHourExportVO", description = "用户填报记录导出类")
public class UserHourExportVO {

    @ApiModelProperty("姓名/昵称")
    private String nickName;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("项目id ")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("填报详情")
    private List<FillInfo> fillInfos;

    @Data
    @ApiModel(value = "FillInfo", description = "填报详情类")
    public static class FillInfo {

        @ApiModelProperty("日期")
        private LocalDate date;

        @ApiModelProperty("填报状态：1 已填报， 2 未填报， 3不需填报, 4 节假日, 5 请假， 6 调休")
        private int status;

        @ApiModelProperty("填报时间")
        private Date fillTime;

        @ApiModelProperty("工时")
        private BigDecimal useHour;

        @ApiModelProperty("日报内容")
        private String daily;

    }

}
