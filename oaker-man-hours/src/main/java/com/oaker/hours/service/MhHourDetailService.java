package com.oaker.hours.service;

import com.oaker.hours.doman.entity.MhHourDetail;

import java.time.LocalDate;

/**
 * @Description :  用户工时详情
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 15:13
 */
public interface MhHourDetailService {

    /**
     * 查询项目指定日期填报详情
     * @param projectId
     * @param userId
     * @param localDate
     * @return
     */
    MhHourDetail queryProjectUserHour(Long projectId, Long userId, LocalDate localDate);


}
