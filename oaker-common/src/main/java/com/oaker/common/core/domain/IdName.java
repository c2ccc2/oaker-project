package com.oaker.common.core.domain;

/**
 * @Description : 用于下拉框等  系统id 和 名的映射
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/8 10:04
 */
public class IdName {

    /** id */
    private Long id;

    /** 名 */
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
