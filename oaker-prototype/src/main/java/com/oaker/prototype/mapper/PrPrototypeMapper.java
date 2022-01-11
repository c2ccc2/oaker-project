package com.oaker.prototype.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.prototype.doman.entity.PrPrototype;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Description : 原型
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/15 16:48
 */
public interface PrPrototypeMapper extends BaseMapper<PrPrototype> {

    @Update("UPDATE pr_prototype SET record_id=null WHERE id=#{id} AND record_id=#{recordId}")
    Integer deleteRecord(@Param("id") Long id, @Param("recordId") Long recordId);

    @Update("UPDATE pr_prototype SET sketch_id=null WHERE id=#{id} AND sketch_id=#{sketchId}")
    Integer deleteSketch(@Param("id") Long id, @Param("sketchId") Long sketchId);

}
