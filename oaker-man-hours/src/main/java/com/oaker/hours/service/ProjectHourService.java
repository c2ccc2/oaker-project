package com.oaker.hours.service;

import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.vo.OverallHourDetailVO;
import com.oaker.hours.doman.vo.OverallHourListVO;
import com.oaker.hours.doman.vo.ProjectFillCalendar;
import com.oaker.hours.doman.vo.ProjectFillCalendarDetail;
import com.oaker.hours.doman.vo.ProjectHourFillStatListVO;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
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
     * 项目统计列表上报记录
     * @param projectId
     * @param startDate
     * @param endDate
     * @return
     */
    List<ProjectHourFillStatListVO> queryProjectHourFillStat(Long projectId, LocalDate startDate, LocalDate endDate);

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
     * @param monthDetailDTO
     * @param pageStart
     * @param pageSize
     * @return
     */
    TableDataInfo queryProjectMonthUserDetail(ProjectHourStatMonthDetailDTO monthDetailDTO, int pageStart, int pageSize);

    /**
     * 总体统计列表查询
     * @param startDate
     * @param endDate
     * @return
     */
    List<OverallHourListVO> queryOverallList(LocalDate startDate, LocalDate endDate);

    /**
     * 查询总体统计详情-已上报
     * @param localDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo queryOverallDetail(LocalDate localDate, int pageNum, int pageSize);

    /**
     * 总体统计指定日期统计
     * @param localDate
     * @return
     */
    OverallHourDetailVO.OverallHourStat queryOverallDetailStat(LocalDate localDate);

    /**
     * 总体统计指定日期请假人员
     * @param localDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo overallLeaveDetail(LocalDate localDate, int pageNum, int pageSize);

    /**
     * 总体统计指定日期缺报人员查询
     * @param localDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo overallMissDetail(LocalDate localDate, int pageNum, int pageSize);

    /**
     * 查询项目上报日历
     * @param localDate
     * @param projectId
     * @return
     */
    List<ProjectFillCalendar> queryProjectFillCalendar(LocalDate localDate, Long projectId);

    /**
     * 查询查询项目上报日历详情
     * @param localDate
     * @param projectId
     * @return
     */
    ProjectFillCalendarDetail getProjectFillCalendarDetail(LocalDate localDate, Long projectId);


}
