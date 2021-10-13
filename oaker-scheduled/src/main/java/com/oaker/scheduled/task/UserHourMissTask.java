package com.oaker.scheduled.task;

import com.oaker.hours.service.MhUserHourMissService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/15 17:31
 */
@Component("userHourMiss")
public class UserHourMissTask {

    @Resource
    private MhUserHourMissService userHourMissService;

    public void userHourMissTask() {
        System.out.println("定时任务被调用了...");
        userHourMissService.userMissTask();
    }


}
