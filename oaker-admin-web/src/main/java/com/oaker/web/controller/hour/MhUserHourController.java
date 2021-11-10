package com.oaker.web.controller.hour;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.enums.BusinessType;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.vo.UserHourDetailVO;
import com.oaker.hours.doman.vo.UserHourListVO;
import com.oaker.hours.doman.vo.UserHourStatDestailVO;
import com.oaker.hours.doman.vo.UserHourStatVO;
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
    private MhUserHourServiceImpl userHourService;

    /**
     * 用户填报工时
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('mh:hour:add')")
    @Log(title = "用户工时", businessType = BusinessType.INSERT)
    public AjaxResult create(@Valid @RequestBody UserHourSaveDTO userHourSaveDTO) {
        boolean result = userHourService.create(userHourSaveDTO);
        return toAjax(result);
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
    @PreAuthorize("@ss.hasPermi('mh:hour:edit')")
    public AjaxResult updateHour(@Validated @RequestBody UserHourUpdateDTO userHourUpdateDTO) {
        boolean result = userHourService.updateUserHour(userHourUpdateDTO);
        return toAjax(result);
    }

    /**
     * 我的统计
     */
    @GetMapping("/stat")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat')")
    public AjaxResult getMyHourStat(
            @NotNull(message = "查询日期不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        UserHourStatVO myHourStat = userHourService.getMyHourStat(date);
        return AjaxResult.success(myHourStat);
    }

    /**
     * 我的统计-详细模式
     */
    @GetMapping("/stat/detail")
    @PreAuthorize("@ss.hasPermi('mh:hour:stat:detail')")
    public AjaxResult queryMyHourStatDetail(
            @NotNull(message = "工时填报id不能为空") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserHourStatDestailVO> vos = userHourService.queryMyHourStatDetail(date);
        return AjaxResult.success(vos);
    }

}
