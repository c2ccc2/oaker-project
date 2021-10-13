package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.entity.ProjectHour;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @Description : 项目工时
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 13:46
 */
public interface ProjectHourMapper extends BaseMapper<ProjectHour> {

    @Update("UPDATE mh_project_hour SET use_hour = use_hour + #{useHour} WHERE project_id = #{projectId}")
    int addUseHour(@Param("projectId") Long projectId, @Param("useHour") BigDecimal useHour);

}
