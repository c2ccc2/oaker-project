package com.oaker.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author `` 须尽欢 _____
 */
@Component
@ConfigurationProperties(prefix = "oaker")
public class OakerConfig {
    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;

    /**
     * 发布日期
     */
    private String date;
    /**
     * 版权年份
     */
    private String copyrightYear;
    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        OakerConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        OakerConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取原型模块基础上传路径
     */
    private static String getPrototypePath() {
        return getProfile() + "/prototype";
    }

    /**
     * 获取原型文档上传基础路径
     * @return
     */
    public static String getPrDocPath() {
        return getPrototypePath() + "/doc";
    }

    /**
     * 获取原型文件上传基础路径
     * @return
     */
    public static String getPrPrototypePath() {
        return getPrototypePath() + "/pr";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled() {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }
}
