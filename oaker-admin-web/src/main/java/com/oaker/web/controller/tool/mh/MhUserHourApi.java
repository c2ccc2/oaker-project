package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.vo.UserHourCalendarVO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourExportVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourProjectStat;
import com.oaker.hours.doman.vo.UserHourStatDetailVO;
import com.oaker.hours.doman.vo.UserHourStatVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 17:28
 */
@RestController
@RequestMapping("/doc/mh/hour")
@Api(tags = "用户工时", description = "用户工时相关接口")
public class MhUserHourApi {

    @PostMapping("/create")
    @ApiOperation("用户填报工时接口")
    public AjaxResult create(@RequestBody UserHourSaveDTO userHourSaveDTO) {
        return AjaxResult.success();
    }

    @PostMapping("/leave")
    @ApiOperation("请假接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "leaveDate", value = "请假日期", dataType = "LocalDate", required = true),
            @ApiImplicitParam(name = "leaveType", value = "类型：1请假  2倒休", dataType = "int", required = true)
    })
    public AjaxResult leave(LocalDate leaveDate, int leaveType) {
        return AjaxResult.success();
    }

    @PostMapping("/unLeave")
    @ApiOperation("用户取消请假/调休")
    @ApiImplicitParam(name = "id", value = "请假记录id", dataType = "Long", required = true)
    public AjaxResult unLeave(Long id) {
        return AjaxResult.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询我的工时列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "LocalDate", required = true),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "LocalDate", required = true)
    })
    public List<UserHourListVO> create( LocalDate startDate, LocalDate endDate) {
        return Collections.singletonList(new UserHourListVO());
    }

    @GetMapping("/list/miss")
    @ApiOperation("查询用户缺报记录-分页")
    public List<UserHourListVO> missList() {
        return Collections.singletonList(new UserHourListVO());
    }

    @GetMapping("/detail")
    @ApiOperation("查询我的工时详情")
    @ApiImplicitParam(name = "hourId", value = "工时填报表id", dataType = "Long", required = true)
    public List<UserHourDetailVO> queryDetail(Long hourId) {
        return Collections.singletonList(new UserHourDetailVO());
    }

    @PutMapping("/edit")
    @ApiOperation("用户修改填报工时")
    public AjaxResult updateHour(@RequestBody UserHourUpdateDTO userHourUpdateDTO) {
        return AjaxResult.success();
    }

    @GetMapping("/stat")
    @ApiOperation("填报记录-表格")
    @ApiImplicitParam(name = "date", value = "查询月份", dataType = "LocalDate", required = true)
    public List<UserHourStatVO> getMyHourStat(LocalDate date) {
        return Collections.singletonList(new UserHourStatVO());
    }

    @GetMapping("/stat/project")
    @ApiOperation("填报记录-按项目统计")
    @ApiImplicitParam(name = "date", value = "查询月份", dataType = "LocalDate", required = true)
    public List<UserHourProjectStat> queryUserHourProjectStat( LocalDate date) {
        return Collections.singletonList(new UserHourProjectStat());
    }

    @GetMapping("/stat/export")
    @ApiOperation("用户填报记录导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "localDate", value = "日期 yyyy-MM-dd 格式(月份第一天)", dataType = "LocalDate", required = true),
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    })
    public UserHourExportVO userHourExport(LocalDate localDate, Long projectId) {
        return new UserHourExportVO();
    }

    @GetMapping("/calendar")
    @ApiOperation("填报记录-日历")
    @ApiImplicitParam(name = "date", value = "查询月份", dataType = "LocalDate", required = true)
    public List<UserHourCalendarVO> queryUserHourCalendar(LocalDate date) {
        return Collections.singletonList(new UserHourCalendarVO());
    }

    @GetMapping("/stat/detail")
    @ApiOperation("我的统计-详细模式(分页)")
    @ApiImplicitParam(name = "date", value = "查询月份", dataType = "LocalDate", required = true)
    public List<UserHourStatDetailVO> queryMyHourStatDetail(LocalDate date) {
        return Collections.singletonList(new UserHourStatDetailVO());
    }

    @ApiOperation("用户查询缺报项目)")
    @GetMapping("/miss/project")
    public List<UserProjectShortVO> queryMyMissProject(Long missId) {
        return Collections.singletonList(new UserProjectShortVO());
    }

}
