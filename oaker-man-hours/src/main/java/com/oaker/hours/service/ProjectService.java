package com.oaker.hours.service;


import com.oaker.hours.doman.dto.ProjectSaveDTO;
import com.oaker.hours.doman.dto.ProjectUpdateDTO;
import com.oaker.hours.doman.entity.MhProject;

import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 13:27
 */
public interface ProjectService {

    /**
     * 创建项目
     * @param projectSaveDTO
     * @return
     */
    Long create(ProjectSaveDTO projectSaveDTO);

    /**
     * 查询列表
     * @param projectStatus
     * @return
     */
    List<MhProject> queryList(String projectStatus);

    /**
     * 项目经理查询列表
     * @param projectStatus
     * @return
     */
    List<MhProject> queryMyList(String projectStatus);

    /**
     * 修改项目信息
     * @param projectUpdateDTO
     * @return
     */
    boolean modify(ProjectUpdateDTO projectUpdateDTO);

    /**
     * 删除项目
     * @param projectId
     * @return
     */
    boolean delete(Long projectId);

    /**
     * 设置项目 暂停/启用 状态
     * @param projectId
     * @param enable
     * @return
     */
    boolean enable(Long projectId, Boolean enable);

}
