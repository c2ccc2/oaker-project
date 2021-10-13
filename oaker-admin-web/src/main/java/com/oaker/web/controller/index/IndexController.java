package com.oaker.web.controller.index;

import com.oaker.common.core.controller.BaseController;
import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.domain.entity.SysUser;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.vo.IndexFillVO;
import com.oaker.hours.doman.vo.IndexProjectStatVO;
import com.oaker.hours.doman.vo.IndexloginInfo;
import com.oaker.hours.service.IndexService;
import com.oaker.system.domain.SysLogininfor;
import com.oaker.system.service.ISysLogininforService;
import com.oaker.system.service.ISysUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 首页
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 11:07
 */
@Validated
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private IndexService indexService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysLogininforService loginInfoService;

    /**
     * 项目统计
     */
    @GetMapping("/projectStat")
    public AjaxResult projectStat() {
        List<IndexProjectStatVO> vos = indexService.projectStat();
        return AjaxResult.success(vos);
    }

    /**
     * 上报统计
     */
    @GetMapping("/fillInfo")
    public AjaxResult fillInfo() {
        IndexFillVO indexFillVO = indexService.fillInfo();
        return AjaxResult.success(indexFillVO);
    }

    /**
     * 用户登陆信息查询
     * @return
     */
    @GetMapping("/login/user")
    public TableDataInfo loginInfoList() {
        startPage();
        List<SysLogininfor> list = loginInfoService.selectLogininforList(new SysLogininfor());
        TableDataInfo dataTable = getDataTable(list);
        if (CollectionUtils.isEmpty(list)) {
            return dataTable;
        }
        IndexloginInfo info;
        List<IndexloginInfo> resultList = new ArrayList<>(list.size());
        for (SysLogininfor sysLogininfor : list) {
            info = new IndexloginInfo();
            SysUser sysUser = userService.selectUserByUserName(sysLogininfor.getUserName());
            info.setNickName(sysUser.getNickName())
                    .setLoginDate(sysLogininfor.getLoginTime());
            resultList.add(info);
        }
        dataTable.setRows(resultList);
        return dataTable;
    }


}
