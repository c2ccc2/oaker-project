package com.oaker.hours.service;

import com.oaker.hours.doman.vo.IndexFillVO;
import com.oaker.hours.doman.vo.IndexProjectStatVO;

import java.util.List;

/**
 * @Description :  首页
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/29 9:37
 */
public interface IndexService {

    /**
     * 首页项目上报统计
     * @return
     */
    List<IndexProjectStatVO> projectStat();

    /**
     * 上报情况
     * @return
     */
    IndexFillVO fillInfo();

}
