package com.oaker.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/3 11:29
 */
@Data
public class SysInfoConfigVo implements Serializable {

    /** 登陆名称 */
    private String loginName;
    /** 系统名称 */
    private String appName;
    /** logo图片地址 */
    private String logoImg;

}
