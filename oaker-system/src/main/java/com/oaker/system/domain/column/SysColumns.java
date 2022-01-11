package com.oaker.system.domain.column;

/**
 * @Description : 属性名常量类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/3 13:48
 */
public interface SysColumns {


    interface SysInfoConfigCache {

        String redisKey = "sys:info:config";

        String loginName = "loginName";
        String appName = "appName";
        String logoImg = "logoImg";
    }

}
