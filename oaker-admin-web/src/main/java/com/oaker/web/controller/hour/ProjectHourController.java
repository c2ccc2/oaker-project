package com.oaker.web.controller.hour;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.page.PageDomain;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.enums.BusinessType;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.vo.OverallHourDetailVO;
import com.oaker.hours.doman.vo.OverallHourListVO;
import com.oaker.hours.doman.vo.ProjectFillCalendar;
import com.oaker.hours.doman.vo.ProjectFillCalendarDetail;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
import com.oaker.hours.doman.vo.ProjectHourStatUser;
import com.oaker.hours.doman.vo.ProjectUserFillVO;
import com.oaker.hours.service.impl.MhCostServiceImpl;
import com.oaker.hours.service.impl.ProjectHourServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * @Description : 项目工时
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 11:41
 */
@Validated
@RestController
@RequestMapping("/system/project/hour")
public class ProjectHourController extends BaseController {

    @Resource
    private MhCostServiceImpl costService;

    @Resource
    private ProjectHourServiceImpl projectHourService;

    @PutMapping("/edit")
    @PreAuthorize("@ss.hasPermi('system:project:hour:edit')")
    @Log(title = "项目工时管理", businessType = BusinessType.UPDATE)
    public AjaxResult setHour(@NotNull(message = "项目id不能为空") Long projectId
            ,@NotNull(message = "预估工时不能为空") BigDecimal manHour) {
        ProjectHour projectHour = new ProjectHour();
        projectHour.setProjectId(projectId);
        projectHour.setManHour(manHour);
        boolean result = projectHourService.updateById(projectHour);
        return toAjax(result);
    }

    /**
     * 项目工时统计列表
     * @param projectStatus
     * @return
     */
    @GetMapping(value = "/stat")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public TableDataInfo queryProjectHourStat(String projectStatus){
        PageDomain pageDomain = getPageDomain();
        return projectHourService.queryProjectHourStat(projectStatus, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 查询上报记录统计-日历
     * @param projectId
     * @param localDate
     * @return
     */
    @GetMapping(value = "/stat/calendar")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public AjaxResult queryProjectHourFillCalendar(@NotNull(message = "项目id不能为空") Long projectId
            , @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull(message = "开始日期不能为空") LocalDate localDate) {
        List<ProjectFillCalendar> calendars = projectHourService.queryProjectFillCalendar(localDate, projectId);
        return AjaxResult.success(calendars);
    }

    /**
     * 查询上报记录统计
     * @param projectId
     * @param localDate
     * @return
     */
    @GetMapping(value = "/stat/fill")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public AjaxResult queryProjectHourFillStat(@NotNull(message = "项目id不能为空") Long projectId
            , @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull(message = "开始日期不能为空") LocalDate localDate) {
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return AjaxResult.success(projectHourService.queryProjectHourFillStat(projectId, startDate, endDate));
    }

    /**
     * 项目工时统计列表详情
     * @param projectId
     * @param date
     * @return
     */
    @GetMapping(value = "/stat/fill/detail")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public AjaxResult queryFillDetail(@NotNull(message = "项目id不能为空") Long projectId
            ,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull(message = "日期不能为空") LocalDate date){
        ProjectUserFillVO projectUserFillVO = projectHourService.queryFillDetail(projectId, date);
        return AjaxResult.success(projectUserFillVO);
    }


    /**
     * 项目工时统计列表详情-按月统计
     * @param projectId
     * @return
     */
    @GetMapping(value = "/stat/hour/month")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public AjaxResult queryProjectHourMonthStat(@NotNull(message = "项目id不能为空") Long projectId){
        List<ProjectHourStatMonth> hourStatMonths = projectHourService.queryProjectHourMonthStat(projectId);
        return AjaxResult.success(hourStatMonths);
    }

    /**
     * 项目工时统计列表详情-按月统计详情
     * @param detailDTO
     * @return
     */
    @GetMapping(value = "/stat/hour/month/detail")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public TableDataInfo queryProjectHourMonthStat(@Valid ProjectHourStatMonthDetailDTO detailDTO){
        int pageStart = pageNumToStart(detailDTO.getPageNum(), detailDTO.getPageSize());
        return projectHourService.queryProjectMonthUserDetail(detailDTO, pageStart, detailDTO.getPageSize());
    }

    /**
     * 项目工时统计列表详情-按月统计详情 页面参与人员下拉框
     * @param projectId
     * @return
     */
    @GetMapping("/project/users")
    @PreAuthorize("@ss.hasPermi('mh:cost:query')")
    public AjaxResult queryUserAll(@NotNull(message = "项目id不能为空") Long projectId) {
        return AjaxResult.success(costService.queryUserAll(projectId));
    }

    /**
     * 项目工时统计列表详情-按月统计详情 页面参与职位下拉框
     * @param projectId
     * @return
     */
    @GetMapping("/project/posts")
    @PreAuthorize("@ss.hasPermi('mh:cost:query')")
    public AjaxResult queryPostAll(@NotNull(message = "项目id不能为空") Long projectId) {
        return AjaxResult.success(costService.queryPostAll(projectId));
    }

    /**
     * 项目工时统计列表详情-按人统计
     * @param projectId
     * @param yearMonth
     * @return
     */
    @GetMapping(value = "/stat/hour/user")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public AjaxResult queryProjectHourUserStat(@NotNull(message = "项目id不能为空") Long projectId, String yearMonth){
        List<ProjectHourStatUser> projectHourStatUsers = projectHourService.queryProjectHourUserStat(projectId, yearMonth);
        return AjaxResult.success(projectHourStatUsers);
    }

    /**
     * 总体统计列表查询
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping(value = "/overall")
    @PreAuthorize("@ss.hasPermi('system:project:overall:detail')")
    public AjaxResult queryOverallList(@NotNull(message = "开始日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
            , @NotNull(message = "结束日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OverallHourListVO> vos = projectHourService.queryOverallList(startDate, endDate);
        return AjaxResult.success(vos);
    }

    /**
     * 查询总体统计详情-已上报
     * @param localDate
     * @return
     */
    @GetMapping(value = "/overall/detail")
    @PreAuthorize("@ss.hasPermi('system:project:overall:detail')")
    public TableDataInfo queryOverallDetail(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        PageDomain pageDomain = getPageDomain();
        return projectHourService.queryOverallDetail(localDate, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 查询总体统计详情-详情页统计
     * @param localDate
     * @return
     */
    @GetMapping(value = "/overall/detail/stat")
    @PreAuthorize("@ss.hasPermi('system:project:overall:detail')")
    public AjaxResult queryOverallDetailStat(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        OverallHourDetailVO.OverallHourStat stat = projectHourService.queryOverallDetailStat(localDate);
        return AjaxResult.success(stat);
    }

    /**
     * 查询总体统计详情-请假人员详情(分页)
     * @param localDate
     * @return
     */
    @GetMapping(value = "/overall/leave")
    @PreAuthorize("@ss.hasPermi('system:project:overall:detail')")
    public TableDataInfo overallLeaveDetail(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        PageDomain pageDomain = getPageDomain();
        return projectHourService.overallLeaveDetail(localDate, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 查询总体统计详情-缺报人员详情(分页)
     * @param localDate
     * @return
     */
    @GetMapping(value = "/overall/miss")
    @PreAuthorize("@ss.hasPermi('system:project:overall:detail')")
    public TableDataInfo overallMissDetail(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        PageDomain pageDomain = getPageDomain();
        return projectHourService.overallMissDetail(localDate, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 项目日报-日历
     * @param localDate
     * @param projectId
     * @return
     */
    @GetMapping("/daily/calendar")
    @PreAuthorize("@ss.hasPermi('system:project:daily')")
    public AjaxResult queryProjectDailyCalendar(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate localDate
            ,@NotNull(message = "项目id不能为空") Long projectId) {
        List<ProjectFillCalendar> calendars = projectHourService.queryProjectFillCalendar(localDate, projectId);
        return AjaxResult.success(calendars);
    }

    /**
     * 项目日报-列表
     * @param localDate
     * @param projectId
     * @return
     */
    @GetMapping("/daily/list")
    @PreAuthorize("@ss.hasPermi('system:project:daily')")
    public AjaxResult queryProjectDailyList(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate localDate
            ,@NotNull(message = "项目id不能为空") Long projectId) {
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return AjaxResult.success(projectHourService.queryProjectHourFillStat(projectId, startDate, endDate));
    }

    /**
     * 项目日报日历详情
     * @param localDate
     * @param projectId
     * @return
     */
    @GetMapping("/daily/calendar/detail")
    @PreAuthorize("@ss.hasPermi('system:project:daily')")
    public AjaxResult getProjectFillCalendarDetail(@NotNull(message = "日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate localDate
            ,@NotNull(message = "项目id不能为空") Long projectId) {
        ProjectFillCalendarDetail detail = projectHourService.getProjectFillCalendarDetail(localDate, projectId);
        return AjaxResult.success(detail);
    }





}
