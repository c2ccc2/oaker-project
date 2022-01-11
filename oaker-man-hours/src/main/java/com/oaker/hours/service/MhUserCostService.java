package com.oaker.hours.service;

import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.dto.MhUserCostDTO;

import java.util.List;

/**
 * @Description : 人员成本
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 14:03
 */
public interface MhUserCostService {

    /**
     * 查询人员成本列表
     * @param nickName
     * @param pageStart
     * @param pageSize
     * @return
     */
    TableDataInfo query(String nickName, int pageStart, int pageSize);

    /**
     * 设置人员成本
     * @param userCosts
     * @return
     */
    boolean setCost(List<MhUserCostDTO> userCosts);
}
