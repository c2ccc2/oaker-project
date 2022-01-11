package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.vo.OverallHourDetailVO;
import com.oaker.hours.doman.vo.OverallHourListVO;
import com.oaker.hours.doman.vo.ProjectFillCalendar;
import com.oaker.hours.doman.vo.ProjectFillCalendarDetail;
import com.oaker.hours.doman.vo.ProjectHourFillStatListVO;
import com.oaker.hours.doman.vo.ProjectHourStatListVO;
import com.oaker.hours.doman.vo.ProjectHourStatMonth;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import com.oaker.hours.doman.vo.ProjectHourStatUser;
import com.oaker.hours.doman.vo.ProjectUserFillVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @Description : 项目工时接口文档
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 11:53
 */
@RestController
@RequestMapping("/doc/system/project/hour")
@Api(tags = "项目工时", description = "项目工时相关接口")
public class ProjectHourApi {

    @PutMapping("/edit")
    @ApiOperation("项目工时设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "manHour", value = "项目预估工时", dataType = "BigDecimal", required = true)
    })
    public AjaxResult setHour(Long projectId, BigDecimal manHour) {
        return AjaxResult.success();
    }

    @GetMapping(value = "/stat")
    @ApiOperation("项目工时统计列表(分页)")
    @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String")
    public List<ProjectHourStatListVO> queryProjectHourStat(String projectStatus){
        return Collections.singletonList(new ProjectHourStatListVO());
    }

    @ApiOperation("上报记录统计-列表")
    @GetMapping(value = "/stat/fill")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "localDate", value = "查询月份('yyyy-MM-dd 格式日期')", dataType = "LocalDate")
    })
    public List<ProjectHourFillStatListVO> queryProjectHourFillStat(Long projectId, LocalDate startDate, LocalDate endDate) {
        return Collections.singletonList(new ProjectHourFillStatListVO());
    }

    @GetMapping(value = "/stat/calendar")
    @ApiOperation("查询上报记录统计-日历")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "localDate", value = "查询月份('yyyy-MM-dd 格式日期')", dataType = "LocalDate")
    })
    public List<ProjectFillCalendar> queryProjectHourFillCalendar(Long projectId, LocalDate localDate) {
        return Collections.singletonList(new ProjectFillCalendar());
    }

    @GetMapping(value = "/stat/fill/detail")
    @ApiOperation("项目工时统计列表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "date", value = "查询日期", dataType = "LocalDate", required = true)
    })
    public ProjectUserFillVO queryFillDetail(Long projectId, LocalDate date){
        return new ProjectUserFillVO();
    }


    @GetMapping(value = "/stat/hour/month")
    @ApiOperation("项目工时统计列表详情-按月统计")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public List<ProjectHourStatMonth> queryFillDetail(Long projectId){
        return Collections.singletonList(new ProjectHourStatMonth());
    }

    @GetMapping(value = "/stat/hour/month/detail")
    @ApiOperation("项目工时统计列表详情-按月统计详情")
    public List<ProjectHourStatMonthDetail> queryProjectHourMonthStat(ProjectHourStatMonthDetailDTO detailDTO){
        return Collections.singletonList(new ProjectHourStatMonthDetail());
    }

    @GetMapping(value = "/stat/hour/user")
    @ApiOperation("项目工时统计列表详情-按人统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "yearMonth", value = "查询月份('yyyy-MM 格式的字符串')", dataType = "String")
    })
    public List<ProjectHourStatUser> queryProjectHourUserStat(Long projectId, String yearMonth){
        return Collections.singletonList(new ProjectHourStatUser());
    }

    @ApiOperation("总体统计列表查询")
    @GetMapping(value = "/overall")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "LocalDate", required = true),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "LocalDate", required = true)
    })
    public List<OverallHourListVO> queryOverallList(LocalDate startDate, LocalDate endDate) {
        return Collections.singletonList(new OverallHourListVO());
    }

    @GetMapping(value = "/overall/detail")
    @ApiOperation("查询总体统计详情-已上报(分页)")
    @ApiImplicitParam(name = "localDate", value = "查询日期", dataType = "LocalDate", required = true)
    public List<OverallHourDetailVO> queryOverallDetail(LocalDate localDate) {
        return Collections.singletonList(new OverallHourDetailVO());
    }

    /**
     * 查询总体统计详情-详情页统计
     * @param localDate
     * @return
     */
    @ApiOperation("查询总体统计详情-详情页统计")
    @GetMapping(value = "/overall/detail/stat")
    @ApiImplicitParam(name = "localDate", value = "查询日期", dataType = "LocalDate", required = true)
    public OverallHourDetailVO.OverallHourStat queryOverallDetailStat(LocalDate localDate) {
        return new OverallHourDetailVO.OverallHourStat();
    }

    @GetMapping(value = "/overall/leave")
    @ApiOperation("查询总体统计详情-请假人员详情(分页)")
    @ApiImplicitParam(name = "localDate", value = "查询日期", dataType = "LocalDate", required = true)
    public List<OverallHourDetailVO.UserVO> overallLeaveDetail(LocalDate localDate) {
        return Collections.singletonList(new OverallHourDetailVO.UserVO());
    }

    @GetMapping(value = "/overall/miss")
    @ApiOperation("查询总体统计详情-缺报人员详情(分页)")
    @ApiImplicitParam(name = "localDate", value = "查询日期", dataType = "LocalDate", required = true)
    public List<OverallHourDetailVO.UserVO> overallMissDetail(LocalDate localDate) {
        return Collections.singletonList(new OverallHourDetailVO.UserVO());
    }

    @GetMapping("/daily/calendar")
    @ApiOperation("项目日报-日历")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "localDate", value = "查询月份('yyyy-MM-dd 格式日期')", dataType = "LocalDate")
    })
    public List<ProjectFillCalendar> queryProjectDailyCalendar(LocalDate localDate, Long projectId) {
        return Collections.singletonList(new ProjectFillCalendar());
    }

    @GetMapping("/daily/list")
    @ApiOperation("项目日报-列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "localDate", value = "查询月份('yyyy-MM-dd 格式日期')", dataType = "LocalDate")
    })
    public List<ProjectHourFillStatListVO> queryProjectDailyList(LocalDate localDate, Long projectId) {
        return Collections.singletonList(new ProjectHourFillStatListVO());
    }

    @GetMapping("/daily/calendar/detail")
    @ApiOperation("项目日报日历详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "localDate", value = "查询月份('yyyy-MM-dd 格式日期')", dataType = "LocalDate")
    })
    public ProjectFillCalendarDetail getProjectFillCalendarDetail(LocalDate localDate, Long projectId) {
        return new ProjectFillCalendarDetail();
    }

}
