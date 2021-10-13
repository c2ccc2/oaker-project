package com.oaker.common.enums;

/**
 * 数据状态状态
 *
 * @author `` 须尽欢 _____
 */
public enum DataStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    DataStatus(String code, String info) {
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
