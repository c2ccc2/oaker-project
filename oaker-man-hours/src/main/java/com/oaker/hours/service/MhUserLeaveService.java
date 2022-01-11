package com.oaker.hours.service;

import com.oaker.hours.doman.entity.MhUserLeave;
import com.oaker.hours.doman.entity.MhUserLeaveDetail;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description : 用户请假记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/8 16:43
 */
public interface MhUserLeaveService {

    /**
     * 用户请假
     * @param leaveDate
     * @param leaveType {@link com.oaker.hours.enums.LeaveTypeEnum}
     * @return
     */
    boolean create(LocalDate leaveDate, int leaveType);

    /**
     * 查询用户请假记录
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<MhUserLeave> queryLeave(Long userId, LocalDate startDate, LocalDate endDate);

    /**
     * 取消用户请假/调休
     * @param id
     * @return
     */
    boolean unLeave(Long id);

    /**
     * 请假记录id获取请假详情
     * @param id
     * @return
     */
    List<MhUserLeaveDetail> queryLeaveDetail(Long id);

    /**
     * 查询项目请假记录
     * @param projectId
     * @param date
     * @return
     */
    List<MhUserLeaveDetail> queryByProjectId(Long projectId, LocalDate date);

    /**
     * 查询用户对项目请假详情
     * @param projectId
     * @param userId
     * @param now
     * @return
     */
    MhUserLeaveDetail queryProjectUserLeave(Long projectId, Long userId, LocalDate now);
}
