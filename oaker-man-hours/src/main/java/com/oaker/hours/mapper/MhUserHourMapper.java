package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.dto.ProjectHourStatMonthDetailDTO;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.vo.ProjectHourStatMonthDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 14:22
 */
public interface MhUserHourMapper extends BaseMapper<MhUserHour> {


    /**
     * 查询项目月份统计详情
     * @param monthDetailDTO
     * @return
     */
    int countProjectMonthUserDetail(@Param("dto") ProjectHourStatMonthDetailDTO monthDetailDTO);

    /**
     * 查询项目月份统计详情
     * @param monthDetailDTO
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<ProjectHourStatMonthDetail> queryProjectMonthUserDetail(@Param("dto") ProjectHourStatMonthDetailDTO monthDetailDTO
            , @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
}
