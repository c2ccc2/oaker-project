package com.oaker.hours.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @Description : 请假类型枚举类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/13 10:52
 */
@Getter
public enum  LeaveTypeEnum {

    /**  */
    LEAVE(1, "请假"),
    OFF_DUTY(2, "倒休")

    ;

    private final int code;
    private final String desc;

    LeaveTypeEnum(int leaveType, String desc) {
        this.code = leaveType;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (LeaveTypeEnum value : LeaveTypeEnum.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value.getDesc();
            }
        }
        return null;
    }

    /**
     * 判断数值是否属于枚举类的值
     * @param code
     * @return
     */
    public static boolean isInclude(int code){
        boolean include = false;
        for (LeaveTypeEnum e: LeaveTypeEnum.values()){
            if(e.getCode() == code){
                include = true;
                break;
            }
        }
        return include;
    }

}
