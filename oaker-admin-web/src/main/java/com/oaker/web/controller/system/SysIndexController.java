package com.oaker.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.oaker.common.config.OakerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("系统名称", oakerConfig.getName());
        hashMap.put("version", oakerConfig.getVersion());
        hashMap.put("date", oakerConfig.getDate());
        String jsonString = JSONObject.toJSONString(hashMap);
        return jsonString;
    }
}
