package com.oaker.system.service;

import com.oaker.system.domain.vo.SysAppConfigVo;

import java.math.BigDecimal;

/**
 * @Description : 应用配置
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 10:38
 */
public interface SysAppConfigService {

    /**
     * 查询应用配置
     * @return
     */
    SysAppConfigVo get();

    /**
     * 更新应用配置
     * @param overtimeAllow
     * @param workTime
     * @param workDay
     * @return
     */
    boolean update(Boolean overtimeAllow, BigDecimal workTime, Integer workDay);


}
