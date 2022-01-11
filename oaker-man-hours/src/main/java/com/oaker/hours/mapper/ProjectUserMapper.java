package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.common.core.domain.IdName;
import com.oaker.hours.doman.entity.ProjectUser;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description : 项目人员
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 14:58
 */
public interface ProjectUserMapper extends BaseMapper<ProjectUser> {

    @Select("SELECT pu.project_id AS projectId, p.project_name AS projectName, p.project_status AS projectStatus" +
            " FROM sys_project_user AS pu LEFT JOIN sys_project AS p ON pu.project_id = p.project_id " +
            " WHERE pu.user_id = #{userId} AND pu.create_time < #{date} AND pu.status = 1 AND p.deleted = 0 " +
            " AND p.start_date <= #{date} AND p.enable = 1")
    List<UserProjectShortVO> queryUserProject(@Param("userId") Long userId, @Param("date") Date date);

    @Select("SELECT user_id FROM sys_project_user AS pu LEFT JOIN sys_project AS p ON pu.project_id = p.project_id " +
            "WHERE pu.status = 1 AND p.start_date <= #{localDate} AND p.enable = 1 AND pu.everyday=true")
    Set<Long> queryJoinUserIds(@Param("localDate") LocalDate localDate);

    List<UserProjectShortVO> queryProjectByUserId(@Param("userId") Long userId, @Param("projectStatus") String projectStatus, @Param("everyday") Boolean everyday);

    @Select("SELECT su.user_id AS id, su.nick_name AS name FROM sys_project_user AS spu " +
            " LEFT JOIN sys_user AS su ON spu.user_id=su.user_id WHERE spu.project_id=#{projectId}")
    List<IdName> queryUserAll(@Param("projectId") Long projectId);

    @Select("SELECT sp.post_id AS id, sp.post_name AS name FROM sys_project_user AS spu " +
            " LEFT JOIN sys_user_post AS sup ON spu.user_id=sup.user_id LEFT JOIN sys_post AS sp ON sup.post_id=sp.post_id " +
            " WHERE spu.project_id=#{projectId}")
    List<IdName> queryPostAll(@Param("projectId") Long projectId);
}
