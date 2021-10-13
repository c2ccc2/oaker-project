package com.oaker.web.controller.tool.index;

import com.oaker.hours.doman.vo.IndexFillVO;
import com.oaker.hours.doman.vo.IndexProjectStatVO;
import com.oaker.hours.doman.vo.IndexloginInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Description :  首页api
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 11:11
 */
@RestController
@RequestMapping("/doc/index")
@Api(tags = "首页api", description = "首页相关接口")
public class IndexApi {

    /**
     * 项目统计
     */
    @GetMapping("/projectStat")
    @ApiOperation("首页项目统计")
    public List<IndexProjectStatVO> projectStat() {
        return Collections.singletonList(new IndexProjectStatVO());
    }

    /**
     * 上报统计
     */
    @GetMapping("/fillInfo")
    @ApiOperation("上报统计")
    public IndexFillVO fillInfo() {
        return new IndexFillVO();
    }

    /**
     * 用户登陆信息查询
     * @return
     */
    @GetMapping("/login/user")
    @ApiOperation("用户登陆信息查询")
    public List<IndexloginInfo> loginInfoList() {
        return Collections.singletonList(new IndexloginInfo());
    }


}
