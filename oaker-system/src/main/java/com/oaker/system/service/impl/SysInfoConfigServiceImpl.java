package com.oaker.system.service.impl;

import com.oaker.common.config.OakerConfig;
import com.oaker.common.core.redis.RedisCache;
import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.common.utils.file.FileUploadUtils;
import com.oaker.common.utils.file.MimeTypeUtils;
import com.oaker.system.domain.SysInfoConfigCache;
import com.oaker.system.domain.column.SysColumns;
import com.oaker.system.service.SysInfoConfigService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/3 11:33
 */
@Service
public class SysInfoConfigServiceImpl implements SysInfoConfigService {

    @Resource
    private RedisCache redisCache;

    @Override
    public SysInfoConfigCache getInfo() {
        SysInfoConfigCache cache = new SysInfoConfigCache();
        Map<String, String> cacheMap = redisCache.getCacheMap(SysColumns.SysInfoConfigCache.redisKey);
        cache.setLoginName(cacheMap.get(SysColumns.SysInfoConfigCache.loginName))
                .setAppName(cacheMap.get(SysColumns.SysInfoConfigCache.appName))
                .setLogoImg(cacheMap.get(SysColumns.SysInfoConfigCache.logoImg));
        return cache;
    }

    @Override
    public boolean updateInfo(String loginName, String appName) {
        if (StringUtils.isNotBlank(loginName)) {
            redisCache.setCacheMapValue(SysColumns.SysInfoConfigCache.redisKey
                    , SysColumns.SysInfoConfigCache.loginName
                    , loginName);
        }
        if (StringUtils.isNotBlank(appName)) {
            redisCache.setCacheMapValue(SysColumns.SysInfoConfigCache.redisKey
                    , SysColumns.SysInfoConfigCache
                            .appName, appName);
        }
        return Boolean.TRUE;
    }

    @Override
    public String uploadLogo(MultipartFile file) throws IOException, InvalidExtensionException {
        String filePath = OakerConfig.getLogoPath();
        FileUtils.deleteDirectory(new File(filePath));
        String upload = FileUploadUtils.upload(filePath, file, MimeTypeUtils.IMG_EXTENSION, false);
        redisCache.setCacheMapValue(SysColumns.SysInfoConfigCache.redisKey, SysColumns.SysInfoConfigCache.logoImg, upload);
        return upload;
    }
}
