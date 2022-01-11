package com.oaker.web.controller.system;

import com.oaker.common.annotation.Log;
import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.enums.BusinessType;
import com.oaker.common.exception.ServiceException;
import com.oaker.system.domain.vo.SysAppConfigVo;
import com.oaker.system.service.impl.SysAppConfigServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 10:42
 */
@RestController
@RequestMapping("/system/app/config")
public class SysAppConfigController extends BaseController {

    @Resource
    private SysAppConfigServiceImpl sysAppConfigService;

    /**
     * 获取应用配置信息
     * @return
     */
    @GetMapping
    public AjaxResult query() {
        SysAppConfigVo configVo = sysAppConfigService.get();
        return AjaxResult.success(configVo);
    }

    /**
     * 修改应用配置
     * @param overtimeAllow
     * @param workTime
     * @param workDay
     * @return
     */
    @PutMapping("/edit")
    @PreAuthorize("@ss.hasPermi('system:app:config:edit')")
    @Log(title = "应用配置修改", businessType = BusinessType.UPDATE)
    public AjaxResult update(Boolean overtimeAllow, BigDecimal workTime, Integer workDay) {
        if (Objects.isNull(overtimeAllow) && Objects.isNull(workTime) && Objects.isNull(workDay)) {
            return AjaxResult.error("必要参数不能为空");
        }
        if (!Objects.isNull(workTime)) {
            if (workTime.compareTo(BigDecimal.ZERO) < 1|| workTime.compareTo(new BigDecimal("24")) > 0) {
                throw new ServiceException("每日工作时数应大于0且小于24");
            }
        }
        return toAjax(sysAppConfigService.update(overtimeAllow, workTime, workDay));
    }

}
