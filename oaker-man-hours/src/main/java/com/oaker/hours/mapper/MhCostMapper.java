package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.dto.MhCostListDTO;
import com.oaker.hours.doman.entity.MhCost;
import com.oaker.hours.doman.vo.MhCostListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description : 人员成本记录表
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 17:29
 */
public interface MhCostMapper extends BaseMapper<MhCost> {

    /**
     * 成本列表查询统计
     * @param mhCostListDTO
     * @return
     */
    int count(@Param("dto") MhCostListDTO mhCostListDTO);

    /**
     * 查询成本统计列表
     * @param mhCostListDTO
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<MhCostListVO> query(@Param("dto") MhCostListDTO mhCostListDTO, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
}
