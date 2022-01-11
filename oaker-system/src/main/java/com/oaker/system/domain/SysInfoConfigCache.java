package com.oaker.system.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统信息设置(SysInfoConfig)实体类
 *
 * @author makejava
 * @since 2021-11-03 11:20:16
 */
@Data
@Accessors(chain = true)
public class SysInfoConfigCache implements Serializable {

    /** 登陆名称 */
    private String loginName;
    /** 系统名称 */
    private String appName;
    /** logo图片地址 */
    private String logoImg;


}