package com.oaker.prototype.service;

import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.prototype.doman.entity.PrPrototype;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/15 16:50
 */
public interface PrPrototypeService {

    /**
     * 创建原型
     * @param name
     * @param projectId
     * @return
     */
    Long create(String name, Long projectId);

    /**
     * 修改项目原型数据名称
     * @param id
     * @param name
     * @return
     */
    boolean modify(Long id, String name);

    /**
     * 根据id获取原型信息
     * @param id
     * @return
     */
    PrPrototype getById(Long id);

    /**
     * 上传原型文件
     * @param file
     * @param prototypeId
     * @return 记录id
     */
    Long upload(MultipartFile file, Long prototypeId) throws IOException, InvalidExtensionException;

    /**
     * 查询项目原型列表
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @return
     */
    TableDataInfo query(Long projectId, int pageNum, int pageSize);

    /**
     * 删除项目原型
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 删除原型数据对原型的引用
     * @param id
     * @param recordId
     * @return
     */
    boolean deleteRecord(Long id, Long recordId);

    /**
     * 根据原型公开id获取原型信息
     * @param pubId
     * @return
     */
    PrPrototype getByPubId(String pubId);


}
