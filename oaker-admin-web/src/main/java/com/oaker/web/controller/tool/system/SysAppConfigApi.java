package com.oaker.web.controller.tool.system;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.system.domain.vo.SysAppConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 11:03
 */
@RestController
@RequestMapping("/doc/system/app/config")
@Api(tags = "应用配置", description = "应用配置相关接口")
public class SysAppConfigApi {

    /**
     * 获取应用配置信息
     * @return
     */
    @GetMapping
    @ApiOperation("获取应用配置信息")
    public SysAppConfigVo get() {
        return new SysAppConfigVo();
    }

    @PutMapping("/edit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "overtimeAllow", value = "是否允许加班", dataType = "Boolean"),
            @ApiImplicitParam(name = "workTime", value = "每日工时数", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "workDay", value = "每月工作日数", dataType = "Integer")
    })
    @ApiOperation("修改应用配置")
    public AjaxResult update(Boolean overtimeAllow, BigDecimal workTime, Integer workDay) {
        return AjaxResult.success();
    }

}
