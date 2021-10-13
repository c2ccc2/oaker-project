package com.oaker.hours.service;

import com.oaker.hours.doman.entity.MhUserHourMiss;
import com.oaker.hours.doman.entity.MhUserHourMissDetail;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 16:50
 */
public interface MhUserHourMissService {

    /**
     * 删除缺报记录连同详情一同删除
     * @param id
     * @return
     */
    boolean deleteByIdAndDetail(Long id);

    /**
     * 查询用户缺报记录
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<MhUserHourMiss> queryUserList(Long userId, LocalDate startDate, LocalDate endDate);

    /**
     * 创建用户缺报记录及缺报详情
     * @param userId
     * @param localDate
     * @return
     */
    boolean createMiss(Long userId, LocalDate localDate);

    /**
     * 用户缺报定时任务
     */
    void userMissTask();

    /**
     * 统计项目
     * @param projectId
     * @param date
     * @return
     */
    int countProjectMiss(Long projectId, LocalDate date);

    /**
     * 获取项目得缺报记录
     * @param projectId
     * @param date
     * @return
     */
    List<MhUserHourMissDetail> queryProjectMiss(Long projectId, LocalDate date);

}
