package com.oaker.hours.service;

import com.oaker.common.core.domain.IdName;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.hours.doman.dto.MhCostListDTO;
import com.oaker.hours.doman.dto.MhPostCostDTO;
import com.oaker.hours.doman.dto.MhUserCostDTO;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.vo.CostConfigVO;

import java.util.List;

/**
 * @Description : 成本统计
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 16:09
 */
public interface MhCostService {

    /**
     * 查询成本统计配置
     * @return
     */
    CostConfigVO get();

    /**
     * 更改成本统计设置
     * @param postCost
     * @param userCost
     * @return
     */
    boolean update(Boolean postCost, Boolean userCost);

    /**
     * 查询岗位成本列表
     * @param postName
     * @param pageStart
     * @param pageSize
     * @return
     */
    TableDataInfo queryPost(String postName, int pageStart, int pageSize);

    /**
     * 设置岗位成本
     * @param postCosts
     * @return
     */
    boolean setPostCost(List<MhPostCostDTO> postCosts);

    /**
     * 查询人员成本列表
     * @param nickName
     * @param pageStart
     * @param pageSize
     * @return
     */
    TableDataInfo queryUser(String nickName, int pageStart, int pageSize);

    /**
     * 设置人员成本
     * @param userCosts
     * @return
     */
    boolean setUserCost(List<MhUserCostDTO> userCosts);

    /**
     * 创建人员成本记录
     * @param userHourSaveDTO
     * @return
     */
    boolean create(UserHourSaveDTO userHourSaveDTO);

    /**
     * 修改人员成本统计
     * @param userHourUpdateDTO
     * @return
     */
    boolean updateCost(UserHourUpdateDTO userHourUpdateDTO);

    /**
     * 成本统计列表查询
     * @param mhCostDTO
     * @param pageStart
     * @param pageSize
     * @return
     */
    TableDataInfo query(MhCostListDTO mhCostDTO, int pageStart, int pageSize);


    /**
     * 查询所有参与过项目的人员下拉框
     * @param projectId
     * @return
     */
    List<IdName> queryUserAll(Long projectId);

    /**
     * 查询所有参与过项目的职位下拉框
     * @param projectId
     * @return
     */
    List<IdName> queryPostAll(Long projectId);


}
