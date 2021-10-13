package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourStatDestailVO;
import com.oaker.hours.doman.vo.UserHourStatVO;
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
@Api(tags = "用户工时", description = "用户工时相关接口")
@RestController
@RequestMapping("/doc/mh/hour")
public class MhUserHourApi {

    /**
     * 新增项目
     */
    @PostMapping("/create")
    @ApiOperation("用户填报工时接口")
    public AjaxResult create(@RequestBody UserHourSaveDTO userHourSaveDTO) {
        return AjaxResult.success();
    }

    /**
     * 用户查看我的工时列表
     */
    @GetMapping("/list")
    @ApiOperation("查询我的工时列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "LocalDate", required = true),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "LocalDate", required = true)
    })
    public List<UserHourListVO> create( LocalDate startDate, LocalDate endDate) {
        return Collections.singletonList(new UserHourListVO());
    }

    /**
     * 用户查看我的工时列表详情
     */
    @GetMapping("/detail")
    @ApiOperation("查询我的工时详情")
    @ApiImplicitParam(name = "hourId", value = "工时填报表id", dataType = "Long", required = true)
    public List<UserHourDetailVO> queryDetail(Long hourId) {
        return Collections.singletonList(new UserHourDetailVO());
    }

    /**
     * 用户修改填报工时
     */
    @PutMapping("/edit")
    @ApiOperation("用户修改填报工时")
    public AjaxResult updateHour(@RequestBody UserHourUpdateDTO userHourUpdateDTO) {
        return AjaxResult.success();
    }

    /**
     * 我的统计
     */
    @GetMapping("/stat")
    @ApiOperation("我的统计")
    @ApiImplicitParam(name = "date", value = "查询月份", dataType = "LocalDate", required = true)
    public UserHourStatVO getMyHourStat(LocalDate date) {
        return new UserHourStatVO();
    }

    /**
     * 我的统计
     */
    @GetMapping("/stat/detail")
    @ApiOperation("我的统计-详细模式")
    @ApiImplicitParam(name = "date", value = "查询月份", dataType = "LocalDate", required = true)
    public List<UserHourStatDestailVO> queryMyHourStatDetail(LocalDate date) {
        return Collections.singletonList(new UserHourStatDestailVO());
    }

}
