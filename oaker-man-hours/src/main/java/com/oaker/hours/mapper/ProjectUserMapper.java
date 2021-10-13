package com.oaker.hours.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oaker.hours.doman.entity.ProjectUser;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
            " WHERE pu.user_id = #{userId} AND pu.create_time < #{date} AND pu.status = 1")
    List<UserProjectShortVO> queryUserProject(@Param("userId") Long userId, @Param("date") Date date);

    @Select("SELECT user_id FROM sys_project_user WHERE status = 1")
    Set<Long> queryJoinUserIds();

    List<UserProjectShortVO> queryProjectByUserId(@Param("userId") Long userId, @Param("projectStatus") String projectStatus);
}
