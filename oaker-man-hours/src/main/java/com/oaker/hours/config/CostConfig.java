package com.oaker.hours.config;

import com.oaker.common.core.redis.RedisCache;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @Description : 成本统计配置类
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 15:32
 */
@Component
public class CostConfig implements ApplicationRunner {

    @Resource
    private RedisCache redisCache;

    public static final String REDIS_KEY = "mh:config:cost";

    public static final String REDIS_KEY_POST_COST = "post_cost";
    public static final String REDIS_KEY_USER_COST = "user_cost";


    /** 岗位统计 */
    public static boolean POST_COST = false;

    /** 人员统计 */
    public static boolean USER_COST = false;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Boolean> cacheMap = redisCache.getCacheMap(REDIS_KEY);
        Boolean postCost = cacheMap.get(REDIS_KEY_POST_COST);
        if (!Objects.isNull(postCost)) {
            POST_COST = postCost;
        }
        Boolean userCost = cacheMap.get(REDIS_KEY_USER_COST);
        if (!Objects.isNull(userCost)) {
            USER_COST = userCost;
        }
    }
}
