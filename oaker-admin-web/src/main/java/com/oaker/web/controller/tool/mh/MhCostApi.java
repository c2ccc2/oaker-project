package com.oaker.web.controller.tool.mh;

import com.oaker.common.core.domain.AjaxResult;
import com.oaker.common.core.domain.IdName;
import com.oaker.hours.doman.dto.MhCostListDTO;
import com.oaker.hours.doman.dto.MhPostCostDTO;
import com.oaker.hours.doman.dto.MhUserCostDTO;
import com.oaker.hours.doman.vo.CostConfigVO;
import com.oaker.hours.doman.vo.MhCostListVO;
import com.oaker.hours.doman.vo.MhPostCostVO;
import com.oaker.hours.doman.vo.MhUserCostVO;
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

import java.util.Collections;
import java.util.List;

/**
 * @Description : 成本统计api
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 16:15
 */
@RestController
@RequestMapping("/doc/mh/cost")
@Api(tags = "成本统计", description = "成本统计相关接口")
public class MhCostApi {

    @GetMapping("/conf/query")
    @ApiOperation("获取成本统计配置")
    public CostConfigVO getCostConfig() {
        return new CostConfigVO();
    }

    @PutMapping("/edit")
    @ApiOperation("修改成本统计设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postCost", value = "岗位统计", dataType = "Boolean"),
            @ApiImplicitParam(name = "userCost", value = "人员统计", dataType = "Boolean")
    })
    public AjaxResult update(Boolean postCost, Boolean userCost) {
        return AjaxResult.success();
    }

    @GetMapping("/post/query")
    @ApiOperation("查询岗位成本列表-分页")
    @ApiImplicitParam(name = "postName", value = "岗位名称", dataType = "String")
    public List<MhPostCostVO> queryPostList(String postName) {
        return Collections.singletonList(new MhPostCostVO());
    }

    @PostMapping("/post/set")
    @ApiOperation("设置岗位成本")
    @ApiImplicitParam(name = "postCosts", value = "岗位成本设置类", dataType = "MhPostCostDTO")
    public AjaxResult setPostCost(@RequestBody List<MhPostCostDTO> postCosts) {
        return AjaxResult.success();
    }

    @GetMapping("/user/query")
    @ApiOperation("查询人员成本列表-分页")
    @ApiImplicitParam(name = "nickName", value = "用户名称", dataType = "String")
    public List<MhUserCostVO> queryUserList(String nickName) {
        return Collections.singletonList(new MhUserCostVO());
    }

    @PostMapping("/user/set")
    @ApiOperation("设置人员成本")
    @ApiImplicitParam(name = "userCosts", value = "人员成本设置类", dataType = "MhUserCostDTO")
    public AjaxResult setUserCost(@RequestBody List<MhUserCostDTO> userCosts) {
        return AjaxResult.success();
    }

    @GetMapping("/query")
    @ApiOperation("查询成本统计列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "LocalDate"),
            @ApiImplicitParam(name = "date", value = "日期", dataType = "LocalDate"),
            @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "人员id", dataType = "Long"),
            @ApiImplicitParam(name = "postId", value = "岗位id", dataType = "Long"),
    })
    public List<MhCostListVO> query(MhCostListDTO mhCostDTO) {
        return Collections.singletonList(new MhCostListVO());
    }

    @GetMapping("/project/users")
    @ApiOperation("查询成本统计页面参与人员下拉框")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public List<IdName> queryUserAll(Long projectId) {
        return Collections.singletonList(new IdName());
    }

    @GetMapping("/project/posts")
    @ApiOperation("查询成本统计页面参与职位下拉框")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "Long", required = true)
    public List<IdName> queryPostAll(Long projectId) {
        return Collections.singletonList(new IdName());
    }

}
