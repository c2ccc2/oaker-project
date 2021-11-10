package com.oaker.prototype.service;

import com.oaker.prototype.doman.vo.PrProtoRecordVO;

import java.io.IOException;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/18 13:51
 */
public interface PrProtoRecordService {

    /**
     * 创建原型记录
     * @param path
     * @param fileName
     * @param prototypeId
     * @param prototypeUrl
     * @return
     */
    Long create(String fileName, String path, Long prototypeId, String prototypeUrl);

    /**
     * 由原型信息id查询所有原型历史记录
     * @param protoId
     * @return
     */
    List<PrProtoRecordVO> queryByProtoId(Long protoId);

    /**
     * 根据id删除原型历史记录
     * @param id
     * @return
     */
    boolean deleteRecord(Long id) throws IOException;

    /**
     * 删除第11条记录（最多保留十条原型记录）
     * @param protoId
     * @return
     */
    void deleteRecordLimit(Long protoId) throws IOException;

}
