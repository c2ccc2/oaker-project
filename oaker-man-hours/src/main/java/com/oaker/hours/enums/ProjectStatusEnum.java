package com.oaker.hours.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @Description : 项目状态枚举类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 12:21
 */
@Getter
public enum ProjectStatusEnum {


    /** 项目状态 进行中*/
    DEVELOP ("a", "进行中"),
    /** 项目状态 运维*/
    MAINTAIN("b", "运维"),
    /** 项目状态 归档*/
    COMPLETE("c", "结束");

    private final String code;
    private final String desc;

    ProjectStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String projectStatus) {
        for (ProjectStatusEnum value : ProjectStatusEnum.values()) {
            if (Objects.equals(projectStatus, value.getCode())) {
                return value.getDesc();
            }
        }
        return null;
    }

    public static String getCodeByDesc(String desc) {
        for (ProjectStatusEnum value : ProjectStatusEnum.values()) {
            if (Objects.equals(desc, value.getDesc())) {
                return value.getCode();
            }
        }
        return null;
    }


}
