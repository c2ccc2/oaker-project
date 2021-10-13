package com.oaker.common.enums;

/**
 * @Description :  用户类型枚举类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/10 12:18
 */
public enum  UserTypeEnum {

    SYS_USER ("00", "平台用户"),
    ADMIN_USER("01", "系统管理员类型账户");

    private final String code;
    private final String info;

    UserTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
