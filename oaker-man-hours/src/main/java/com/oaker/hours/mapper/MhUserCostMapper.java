package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.entity.MhUserCost;
import com.oaker.hours.doman.vo.MhUserCostVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 14:01
 */
public interface MhUserCostMapper extends BaseMapper<MhUserCost> {

    /**
     * 查询人员成本列表
     * @param nickName
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<MhUserCostVO> queryUserCost(@Param("nickName") String nickName, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

}
