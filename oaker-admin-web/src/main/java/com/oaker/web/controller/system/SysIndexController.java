package com.oaker.web.controller.system;

import com.oaker.common.config.OakerConfig;
import com.oaker.common.core.redis.RedisCache;
import com.oaker.system.domain.column.SysColumns;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 首页
 *
 * @author `` 须尽欢 _____
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private OakerConfig oakerConfig;

    @Resource
    private RedisCache redisCache;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public SystemInfo index() {
        Map<String, String> cacheMap = redisCache.getCacheMap(SysColumns.SysInfoConfigCache.redisKey);
        SystemInfo info = new SystemInfo();
        info.setName(oakerConfig.getName());
        info.setVersion(oakerConfig.getVersion());
        info.setDate(oakerConfig.getDate());
        info.setLoginName(cacheMap.get(SysColumns.SysInfoConfigCache.loginName));
        info.setAppName(cacheMap.get(SysColumns.SysInfoConfigCache.appName));
        info.setLogoImg(cacheMap.get(SysColumns.SysInfoConfigCache.logoImg));
        return info;
    }

    @ApiModel(value = "SystemInfo", description = "系统信息")
    public static class SystemInfo {

        @ApiModelProperty("项目名称")
        private String name;

        @ApiModelProperty("版本号")
        private String version;

        @ApiModelProperty("build日期")
        private String date;

        @ApiModelProperty("系统名称")
        private String loginName;

        @ApiModelProperty("应用名称")
        private String appName;

        @ApiModelProperty("logo图片地址")
        private String logoImg;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

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
