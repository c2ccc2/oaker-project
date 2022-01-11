package com.oaker.hours.enums;

import lombok.Getter;

/**
 * @Description : 用户工时上报状态枚举类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/10 11:40
 */
@Getter
public enum  UserHourFillStatusEnum {

    /** 已填报 */
    FILLED(1),
    /** 未填报 */
    NOT_FILLED(2),
    /** 不需填报 */
    NO_NEED(3),
    /** 节假日 */
    FESTIVAL(4),
    /** 请假 */
    LEAVE(5),
    /** 调休 */
    OFF_DUTY(6)

    ;

    private int status;

    UserHourFillStatusEnum(int status) {
        this.status = status;
    }

    public static int getCodeByLeaveType(int leaveType) {
        return LeaveTypeEnum.LEAVE.getCode() == leaveType ? LEAVE.getStatus() : OFF_DUTY.getStatus();
    }

}
