package com.oaker.system.service.impl;

import com.oaker.common.config.AppConfig;
import com.oaker.common.core.redis.RedisCache;
import com.oaker.system.domain.vo.SysAppConfigVo;
import com.oaker.system.service.SysAppConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 10:38
 */
@Service
public class SysAppConfigServiceImpl implements SysAppConfigService {

    @Resource
    private RedisCache redisCache;

    @Override
    public SysAppConfigVo get() {
        SysAppConfigVo configVo = new SysAppConfigVo();
        configVo.setWorkDay(AppConfig.WORK_DAY);
        configVo.setWorkTime(AppConfig.WORK_TIME);
        configVo.setOvertimeAllow(AppConfig.OVERTIME_ALLOW);
        return configVo;
    }

    @Override
    public boolean update(Boolean overtimeAllow, BigDecimal workTime, Integer workDay) {
        if (!Objects.isNull(overtimeAllow)) {
            redisCache.setCacheMapValue(AppConfig.REDIS_KEY, AppConfig.REDIS_KEY_OVERTIME_ALLOW, overtimeAllow);
            AppConfig.OVERTIME_ALLOW = overtimeAllow;
        }

        if (!Objects.isNull(workTime)) {
            redisCache.setCacheMapValue(AppConfig.REDIS_KEY, AppConfig.REDIS_KEY_WORK_TIME, String.valueOf(workTime));
            AppConfig.WORK_TIME = workTime;
        }

        if (!Objects.isNull(workDay)) {
            redisCache.setCacheMapValue(AppConfig.REDIS_KEY, AppConfig.REDIS_KEY_WORK_DAY, workDay);
            AppConfig.WORK_DAY = workDay;
        }
        return true;
    }
}
