package com.oaker.common.config;

import com.oaker.common.core.redis.RedisCache;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * @Description : 应用设置类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/3 17:40
 */
@Component
public class AppConfig implements ApplicationRunner {

    @Resource
    private RedisCache redisCache;

    public static final String REDIS_KEY = "sys:info:app";

    public static final String REDIS_KEY_OVERTIME_ALLOW = "overtime_allow";
    public static final String REDIS_KEY_WORK_TIME = "work_time";
    public static final String REDIS_KEY_WORK_DAY = "work_day";

    /** 是否允许加班 */
    public static boolean OVERTIME_ALLOW = false;

    /** 每日工时数 */
    public static BigDecimal WORK_TIME = new BigDecimal("8");

    /** 每月工作日数 */
    public static int WORK_DAY = 22;

    @Override
    public void run(ApplicationArguments args) {

        Map<String, Object> cacheMap = redisCache.getCacheMap(REDIS_KEY);
        Boolean overtimeAllowStr = (Boolean) cacheMap.get(REDIS_KEY_OVERTIME_ALLOW);
        if (!Objects.isNull(overtimeAllowStr)) {
            OVERTIME_ALLOW = overtimeAllowStr;
        }
        String workTime = (String) cacheMap.get(REDIS_KEY_WORK_TIME);
        if (!Objects.isNull(workTime)) {
            WORK_TIME = new BigDecimal(workTime);
        }
        Integer workDay = (Integer) cacheMap.get(REDIS_KEY_WORK_DAY);
        if (!Objects.isNull(workDay)) {
            WORK_DAY = workDay;
        }

    }


}
