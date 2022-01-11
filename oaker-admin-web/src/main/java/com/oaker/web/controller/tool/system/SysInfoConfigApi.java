package com.oaker.web.controller.tool.system;

import com.oaker.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description : 系统信息设置
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/3 11:35
 */
@RestController
@RequestMapping("/doc/system/info/config")
@Api(tags = "系统信息", description = "系统信息相关接口")
public class SysInfoConfigApi {

    @GetMapping("/get")
    @ApiOperation("查询系统信息接口")
    public SysInfoConfigVo get() {
        return new SysInfoConfigVo();
    }

    @PutMapping("/edit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "系统名称", dataType = "String"),
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String")
    })
    @ApiOperation("修改系统信息接口")
    public AjaxResult update(String loginName, String appName) {
        return AjaxResult.success();
    }

    @PostMapping("/logo/upload")
    @ApiOperation("上传logo接口")
    @ApiImplicitParam(name = "file", value = "文件", dataType = "MultipartFile", required = true)
    public AjaxResult uploadLogo(MultipartFile file) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("url", "/file/logo/a.png");
        return ajax;
    }


    @ApiModel(value = "SysInfoConfigVo", description = "系统信息返回类")
    class SysInfoConfigVo {

        @ApiModelProperty("登陆名称")
        private String loginName;

        @ApiModelProperty("系统名称")
        private String appName;

        @ApiModelProperty("logo图片地址")
        private String logoImg;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getLogoImg() {
            return logoImg;
        }

        public void setLogoImg(String logoImg) {
            this.logoImg = logoImg;
        }
    }


}
