package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.entity.MhProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * 项目Mapper接口
 *
 * @author " 须尽欢 _____
 * @date 2021-09-03
 */
@Mapper
public interface ProjectMapper extends BaseMapper<MhProject> {



}
