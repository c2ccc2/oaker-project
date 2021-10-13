package com.oaker.hours.service;

import com.oaker.hours.doman.dto.ProjectUserSaveDTO;
import com.oaker.hours.doman.entity.ProjectUser;
import com.oaker.hours.doman.vo.ProjectUserVO;
import com.oaker.hours.doman.vo.UserProjectShortVO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description : 项目成员
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/9 14:59
 */
public interface ProjectUserService {

    /**
     * 添加项目成员
     * @param projectUserSaveDTO
     * @return
     */
    boolean create(ProjectUserSaveDTO projectUserSaveDTO);

    /**
     * 获取项目成员信息
     * @param projectId
     * @return
     */
    List<ProjectUserVO> queryList(Long projectId);

    /**
     * 查询项目成员信息
     * @param projectId
     * @return
     */
    List<ProjectUser> queryUserByProjectId(Long projectId);

    /**
     * 移除项目成员
     * @param userIds
     * @return
     */
    boolean removeUser(Long projectId, List<Long> userIds);

    /**
     * 获取项目可选成员信息
     * @param projectId
     * @param nickName
     * @return
     */
    List<ProjectUserVO> selectList(Long projectId, String nickName);


    /**
     * 用户查询参与的项目
     * @return
     */
    List<UserProjectShortVO> queryMyProject(Long userId, Date date);

    /**
     * 获取用户第一次加入项目时间
     * @param userId
     * @return
     */
    Date getUserFirstJoinTime(Long userId);

    /**
     * 查询所有参与项目的人员id集合
     * @return
     */
    Set<Long> queryJoinUserIds();


    /**
     * 用户查询参与的项目信息
     * @param userId
     * @param status
     * @return
     */
    List<UserProjectShortVO> userProjects(Long userId, String status);
}
