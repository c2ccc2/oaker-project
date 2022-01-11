package com.oaker.hours.doman.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @Description : 用户请假详情
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/15 10:05
 */
@Data
@Accessors(chain = true)
@TableName("mh_user_leave_detail")
public class MhUserLeaveDetail {

    /** id */
    private Long id;
    /** 缺报记录表id */
    private Long leaveId;
    /** 项目id */
    private Long projectId;
    /** 项目状态：a 进行中，b运维，c 结束 */
    private String projectStatus;
    /** 用户id */
    private Long userId;
    /** 缺报日期 */
    private LocalDate leaveDate;
    /** 类型：1请假  2倒休 */
    private Integer leaveType;


}
