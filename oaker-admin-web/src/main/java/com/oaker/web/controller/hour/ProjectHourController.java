package com.oaker.web.controller.hour;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.enums.BusinessType;
import com.oaker.hours.doman.entity.ProjectHour;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.ProjectHourStatUser;
import com.oaker.hours.doman.vo.ProjectUserFillVO;
import com.oaker.hours.service.impl.ProjectHourServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
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
        Page page = getPage();
        return projectHourService.queryProjectHourStat(projectStatus, page.getPageStart(), page.getPageSize());
    }

    /**
     * 项目工时统计列表详情
     * @param projectId
     * @param date
     * @return
     */
    @GetMapping(value = "/stat/fill/detail")
    @PreAuthorize("@ss.hasPermi('system:project:stat:fill:detail')")
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
     * @param projectId
     * @return
     */
    @GetMapping(value = "/stat/hour/month/detail")
    @PreAuthorize("@ss.hasPermi('system:project:stat:query')")
    public AjaxResult queryProjectHourMonthStat(@NotNull(message = "项目id不能为空") Long projectId
            ,@NotBlank(message = "查询月份不能为空") String yearMonth){
        List<ProjectHourStatMonthDetail> monthDetails = projectHourService.queryProjectMonthUserDetail(projectId, yearMonth);
        return AjaxResult.success(monthDetails);
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

}
