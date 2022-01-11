package com.oaker.hours.service;

import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.dto.MhPostCostDTO;

import java.util.List;

/**
 * @Description : 岗位成本
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 10:27
 */
public interface MhPostCostService {

    /**
     * 查询岗位成本列表
     * @param postName
     * @param pageStart
     * @param pageSize
     * @return
     */
    TableDataInfo query(String postName, int pageStart, int pageSize);

    /**
     * 设置岗位成本
     * @param postCosts
     * @return
     */
    boolean setCost(List<MhPostCostDTO> postCosts);
}
