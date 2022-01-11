package com.oaker.web.controller.hour;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.page.PageDomain;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.enums.BusinessType;
import com.oaker.hours.config.CostConfig;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.vo.UserHourCalendarVO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourExportVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourProjectStat;
import com.oaker.hours.doman.vo.UserHourStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.enums.LeaveTypeEnum;
import com.oaker.hours.service.impl.MhCostServiceImpl;
import com.oaker.hours.service.impl.MhUserHourServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @Description : 工时
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 17:22
 */
@Validated
@RestController
@RequestMapping("/mh/hour")
public class MhUserHourController extends BaseController {

    @Resource
    private MhCostServiceImpl mhCostService;

    @Resource
    private MhUserHourServiceImpl userHourService;

    /**
     * 用户填报工时
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('mh:hour:add')")
    @Log(title = "用户工时", businessType = BusinessType.INSERT)
    public AjaxResult create(@Valid @RequestBody UserHourSaveDTO userHourSaveDTO) {
        boolean result = userHourService.create(userHourSaveDTO);
        if (CostConfig.POST_COST || CostConfig.USER_COST) {
            mhCostService.create(userHourSaveDTO);
        }
        return toAjax(result);
    }

    /**
     * 用户请假/调休
     * @param leaveDate
     * @return
     */
    @PostMapping("/leave")
    @PreAuthorize("@ss.hasPermi('mh:hour:add')")
    @Log(title = "用户请假", businessType = BusinessType.INSERT)
    public AjaxResult leave(@NotNull(message = "请假日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate leaveDate
            ,@NotNull(message = "请假类型不能为空") int leaveType) {
        boolean include = LeaveTypeEnum.isInclude(leaveType);
        if (!include) {
            return AjaxResult.error("请假类型参数不正确");
        }
        return toAjax(userHourService.userLeave(leaveDate, leaveType));
    }

    /**
     * 用户取消请假/调休
     * @param id 请假/调休 记录id
     * @return
     */
    @PostMapping("/unLeave")
    @PreAuthorize("@ss.hasPermi('mh:hour:add')")
    @Log(title = "用户请假", businessType = BusinessType.INSERT)
    public AjaxResult unLeave(@NotNull(message = "请假日期不能为空") Long id) {
        return toAjax(userHourService.unLeave(id));
    }


    /**
     * 用户查看我的工时列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('mh:hour:list')")
    public AjaxResult queryList(@NotNull(message = "开始日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
            ,@NotNull(message = "结束日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<UserHourListVO> list = userHourService.queryList(startDate, endDate);
        return AjaxResult.success(list);
    }

    /**
     * 查询用户缺报记录
     * @return
     */
    @GetMapping("/list/miss")
    @PreAuthorize("@ss.hasPermi('mh:hour:list')")
    public TableDataInfo missList() {
        PageDomain pageDomain = getPageDomain();
        return userHourService.missList(pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 用户查看我的工时列表详情
     */
    @GetMapping("/detail")
    @PreAuthorize("@ss.hasPermi('mh:hour:detail')")
    public AjaxResult queryDetail(@NotNull(message = "工时填报id不能为空") Long hourId) {
        List<UserHourDetailVO> list = userHourService.queryDetail(hourId);
        return AjaxResult.success(list);
    }

    /**
     * 用户修改填报工时
     */
    @PutMapping("/edit")
    @PreAuthorize("@ss.hasPermi('mh:hour:add')")
    public AjaxResult updateHour(@Validated @RequestBody UserHourUpdateDTO userHourUpdateDTO) {
        boolean result = userHourService.updateUserHour(userHourUpdateDTO);
        if (result) {
            mhCostService.updateCost(userHourUpdateDTO);
        }
        return toAjax(result);
    }

    /**
     * 填报记录-表格
     */
    @GetMapping("/stat")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat')")
    public AjaxResult getMyHourStat(
            @NotNull(message = "查询日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserHourStatVO> myHourStat = userHourService.getMyHourStat(date);
        return AjaxResult.success(myHourStat);
    }

    /**
     * 填报记录-按项目统计
     */
    @GetMapping("/stat/project")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat')")
    public AjaxResult queryUserHourProjectStat(
            @NotNull(message = "查询日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserHourProjectStat> myHourStat = userHourService.queryUserHourProjectStat(date);
        return AjaxResult.success(myHourStat);
    }

    /**
     * 用户填报记录导出
     * @param localDate
     * @param projectId
     * @return
     */
    @GetMapping("/stat/export")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat')")
    public AjaxResult userHourExport(
            @NotNull(message = "查询日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate
            ,@NotNull(message = "项目id不能为空") Long projectId) {
        UserHourExportVO hourExportVO = userHourService.userHourExport(localDate, projectId);
        return AjaxResult.success(hourExportVO);
    }

    /**
     * 填报记录-日历
     * @param date
     * @return
     */
    @GetMapping("/calendar")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat')")
    public AjaxResult queryUserHourCalendar(
            @NotNull(message = "查询日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserHourCalendarVO> vos = userHourService.queryUserHourCalendar(date);
        return AjaxResult.success(vos);
    }

    /**
     * 我的工时-详细模式
     */
    @GetMapping("/stat/detail")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat')")
    public TableDataInfo queryMyHourStatDetail(
            @NotNull(message = "查询日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        PageDomain pageDomain = getPageDomain();
        return userHourService.queryMyHourStatDetail(date, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 用户查询缺报项目
     * @param missId
     * @return
     */
    @GetMapping("/miss/project")
    public AjaxResult queryMyMissProject(Long missId) {
        List<UserProjectShortVO> list = userHourService.queryMyMissProject(missId);
        return AjaxResult.success(list);
    }


}
