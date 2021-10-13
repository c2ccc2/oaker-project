package com.oaker.hours.service;

import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourStatDestailVO;
import com.oaker.hours.doman.vo.UserHourStatVO;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description :  用户工时
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 14:24
 */
public interface MhUserHourService {

    /**
     * 用户填报工时
     * @param userHourSaveDTO
     * @return
     */
    boolean create(UserHourSaveDTO userHourSaveDTO);

    /**
     * 用户查看填报列表
     * @param startDate
     * @param endDate
     * @return
     */
    List<UserHourListVO> queryList(LocalDate startDate, LocalDate endDate);

    /**
     * 查看工时填报详情
     * @param hourId
     * @return
     */
    List<UserHourDetailVO> queryDetail(Long hourId);

    /**
     * 用户修改工时填报
     * @param userHourUpdateDTO
     * @return
     */
    boolean updateUserHour(UserHourUpdateDTO userHourUpdateDTO);

    /**
     * 用户我的工时统计
     * @param localDate
     * @return
     */
    UserHourStatVO getMyHourStat(LocalDate localDate);

    /**
     * 查询用户工时统计详细模式
     * @param localDate
     * @return
     */
    List<UserHourStatDestailVO> queryMyHourStatDetail(LocalDate localDate);

    /**
     * 统计项目填报总数
     * @param projectId
     * @param date
     * @return
     */
    int countFillByProject(Long projectId, LocalDate date);

    /**
     * 获取项目工时填报记录
     * @param projectId
     * @param date
     * @return
     */
    List<MhHourDetail> queryProjectUserHour(Long projectId, LocalDate date);

    /**
     * 获取项目工时填报记录-月份查询
     * @param projectId
     * @param yearMonth
     * @return
     */
    List<MhHourDetail> queryProjectUserHourMonth(Long projectId, String yearMonth);

    /**
     * 查询用户指定日期填报信息
     * @param userId
     * @param localDate
     * @return
     */
    MhUserHour getByUserId(Long userId, LocalDate localDate);


    /**
     * 获取用户月份上报次数
     * @param userId
     * @param localDate
     * @return
     */
    int getMonthFill(Long userId, LocalDate localDate);

    /**
     * 获取用户月份缺报次数
     * @param userId
     * @param localDate
     * @return
     */
    int getMonthMissFill(Long userId, LocalDate localDate);


}
