package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
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
@Api(tags = "项目工时", description = "项目工时相关接口")
@RestController
@RequestMapping("/doc/system/project/hour")
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

    /**
     * 项目工时统计列表
     * @param projectStatus
     * @return
     */
    @GetMapping(value = "/stat")
    @ApiOperation("项目工时统计列表")
    @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String")
    public List<ProjectHourStatListVO> queryProjectHourStat(String projectStatus){
        return Collections.singletonList(new ProjectHourStatListVO());
    }

    /**
     * 项目工时统计列表详情
     * @param projectId
     * @param date
     * @return
     */
    @GetMapping(value = "/stat/fill/detail")
    @ApiOperation("项目工时统计列表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "date", value = "查询日期", dataType = "LocalDate", required = true)
    })
    public ProjectUserFillVO queryFillDetail(Long projectId, LocalDate date){
        return new ProjectUserFillVO();
    }


    /**
     * 项目工时统计列表详情-按月统计
     * @param projectId
     * @return
     */
    @GetMapping(value = "/stat/hour/month")
    @ApiOperation("项目工时统计列表详情-按月统计")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public List<ProjectHourStatMonth> queryFillDetail(Long projectId){
        return Collections.singletonList(new ProjectHourStatMonth());
    }

    /**
     * 项目工时统计列表详情-按月统计详情
     * @param projectId
     * @param yearMonth
     * @return
     */
    @GetMapping(value = "/stat/hour/month/detail")
    @ApiOperation("项目工时统计列表详情-按月统计详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "yearMonth", value = "查询月份('yyyy-MM 格式的字符串')", dataType = "String", required = true)
    })
    public List<ProjectHourStatMonthDetail> queryProjectHourMonthStat(Long projectId, String yearMonth){
        return Collections.singletonList(new ProjectHourStatMonthDetail());
    }

    /**
     * 项目工时统计列表详情-按人统计
     * @param projectId
     * @return
     */
    @GetMapping(value = "/stat/hour/user")
    @ApiOperation("项目工时统计列表详情-按人统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "yearMonth", value = "查询月份('yyyy-MM 格式的字符串')", dataType = "String")
    })
    public List<ProjectHourStatUser> queryProjectHourUserStat(Long projectId, String yearMonth){
        return Collections.singletonList(new ProjectHourStatUser());
    }

}
