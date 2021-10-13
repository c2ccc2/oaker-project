package com.oaker.hours.service;

import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.ProjectHourStatUser;
import com.oaker.hours.doman.vo.ProjectUserFillVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 12:25
 */
public interface ProjectHourService {

    /**
     * 增加项目总工时
     * @param projectId
     * @param useHour
     * @return
     */
    boolean addTotalHour(Long projectId, BigDecimal useHour);

    /**
     * 项目工时统计列表
     * @param projectStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo queryProjectHourStat(String projectStatus, int pageNum, int pageSize);

    /**
     * 获取项目填报详情
     * @param projectId
     * @param date
     * @return
     */
    ProjectUserFillVO queryFillDetail(Long projectId, LocalDate date);

    /**
     * 项目工时按月统计
     * @param projectId
     * @return
     */
    List<ProjectHourStatMonth> queryProjectHourMonthStat(Long projectId);

    /**
     * 项目工时按人统计
     * @param projectId
     * @param yearMonth
     * @return
     */
    List<ProjectHourStatUser> queryProjectHourUserStat(Long projectId, String yearMonth);

    /**
     * 项目工时按月统计详情
     * @param projectId
     * @param yearMonth
     * @return
     */
    List<ProjectHourStatMonthDetail> queryProjectMonthUserDetail(Long projectId, String yearMonth);


}
