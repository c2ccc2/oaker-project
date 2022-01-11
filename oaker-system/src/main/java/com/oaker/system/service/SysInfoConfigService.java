package com.oaker.system.service;

import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.system.domain.SysInfoConfigCache;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description : 系统信息
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/3 11:32
 */
public interface SysInfoConfigService {

    /**
     * 查询系统信息
     * @return
     */
    SysInfoConfigCache getInfo();

    /**
     * 修改系统信息
     * @param loginName
     * @param appName
     * @return
     */
    boolean updateInfo(String loginName, String appName);

    /**
     * 上传logo
     * @param file
     * @return
     */
    String uploadLogo(MultipartFile file) throws IOException, InvalidExtensionException;

}
