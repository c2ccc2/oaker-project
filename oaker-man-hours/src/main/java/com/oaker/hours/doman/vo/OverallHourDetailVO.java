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
 * @Description : 总体统计详情类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/10 16:06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OverallHourDetailVO", description = "总体统计详情类")
public class OverallHourDetailVO {

    @ApiModelProperty("人员id")
    private Long userId;

    @ApiModelProperty("人员姓名/昵称")
    private String nickName;

    @ApiModelProperty("职位id")
    private Long postId;

    @ApiModelProperty("职位名称")
    private String postName;

    @ApiModelProperty("上报总工时")
    private BigDecimal totalHour;

    @ApiModelProperty("上报时间")
    private Date fillTime;

    @ApiModelProperty("项目工时详情")
    private List<ProjectDetail> projectDetails;

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "ProjectDetail", description = "项目工时详情")
    public static class ProjectDetail {

        @ApiModelProperty("项目id")
        private Long projectId;

        @ApiModelProperty("项目名称")
        private String projectName;

        @ApiModelProperty("填报工时")
        private BigDecimal useHour;

    }

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "OverallHourStat", description = "总体统计详情页面统计")
    public static class OverallHourStat {

        @ApiModelProperty("日期")
        private LocalDate date;

        @ApiModelProperty("上报人数")
        private int fillNum;

        @ApiModelProperty("缺报人数")
        private int unFillNum;

        @ApiModelProperty("请假人数")
        private int leaveNum;

        @ApiModelProperty("总工时")
        private BigDecimal totalHour;

    }

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "UserVO", description = "缺报和请假人员")
    public static class UserVO {

        @ApiModelProperty("人员id")
        private Long userId;

        @ApiModelProperty("人员姓名/昵称")
        private String nickName;

        @ApiModelProperty("职位id")
        private Long postId;

        @ApiModelProperty("职位名称")
        private String postName;

    }


}
