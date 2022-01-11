package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.entity.MhPostCost;
import com.oaker.hours.doman.vo.MhPostCostVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description : 岗位成本
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 10:26
 */
public interface MhPostCostMapper extends BaseMapper<MhPostCost> {
    /**
     * 查询岗位成本
     * @param postName
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<MhPostCostVO> queryPostCost(@Param("postName") String postName, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
}
