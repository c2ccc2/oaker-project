package com.oaker.prototype.service;

import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.prototype.doman.entity.PrDoc;
import com.oaker.prototype.doman.vo.PrDocVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description : 原型文档
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/16 16:27
 */
public interface PrDocService {

    /**
     * 上传文件
     * @param file
     * @param prototypeId
     * @return 记录id
     */
    Long upload(MultipartFile file, Long prototypeId) throws IOException, InvalidExtensionException;

    /**
     * 根据文档id删除文档
     * @param id
     * @return
     */
    boolean deleteDoc(Long id);

    /**
     * 根据prototypeId 查询文档
     * @param protoId
     * @return
     */
    List<PrDoc> queryByProtoId(Long protoId);

    /**
     * 根据原型id查询文档列表
     * @param protoId
     * @return
     */
    List<PrDocVO> queryList(Long protoId);


}
