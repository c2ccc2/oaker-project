package com.oaker.hours.service;

import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.UserHourCalendarVO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourExportVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourProjectStat;
import com.oaker.hours.doman.vo.UserHourStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;

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
     * 用户查询缺报记录
     * @return
     */
    TableDataInfo missList(int pageNum, int pageSize);


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
     * 填报记录-列表
     * @param localDate
     * @return
     */
    List<UserHourStatVO> getMyHourStat(LocalDate localDate);

    /**
     * 用户工时-按项目统计
     * @param localDate
     * @return
     */
    List<UserHourProjectStat> queryUserHourProjectStat(LocalDate localDate);

    /**
     * 用户填报记录导出
     * @param localDate
     * @param projectId
     * @return
     */
    UserHourExportVO userHourExport(LocalDate localDate, Long projectId);


    /**
     * 查询用户填报记录-日历
     * @param localDate
     * @return
     */
    List<UserHourCalendarVO> queryUserHourCalendar(LocalDate localDate);

    /**
     * 查询用户工时统计详细模式
     * @param localDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo queryMyHourStatDetail(LocalDate localDate, int pageNum, int pageSize);

    /**
     * 统计项目填报总数
     * @param projectId
     * @param date
     * @return
     */
    List<MhHourDetail> queryFillByProject(Long projectId, LocalDate date);

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
     * 获取项目工时填报记录-月份查询详情
     * @param monthDetailDTO
     * @return
     */
    int countProjectMonthUserDetail(ProjectHourStatMonthDetailDTO monthDetailDTO);

    /**
     * 获取项目工时填报记录-月份查询详情
     * @param monthDetailDTO
     * @return
     */
    List<ProjectHourStatMonthDetail> queryProjectMonthUserDetail(ProjectHourStatMonthDetailDTO monthDetailDTO, int pageStart, int pageSize);

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


    /**
     * 用户请假
     * @param leaveDate
     * @param leaveType {@link com.oaker.hours.enums.LeaveTypeEnum}
     * @return
     */
    boolean userLeave(LocalDate leaveDate, int leaveType);

    /**
     * 取消请假/调休
     * @param id
     * @return
     */
    boolean unLeave(Long id);

    /**
     * 获取项目指定日工时填报详情
     * @param projectId
     * @param localDate
     */
    List<MhHourDetail> queryProjectFillDetail(Long projectId, LocalDate localDate);

    /**
     * 用户查询缺报项目
     * @param missId
     * @return
     */
    List<UserProjectShortVO> queryMyMissProject(Long missId);
}
