package com.oaker.prototype.service;

import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.prototype.doman.entity.PrSketchFile;
import com.oaker.prototype.doman.vo.PrSketchListVO;
import com.oaker.prototype.doman.vo.SketchFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description : 效果图记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/17 11:08
 */
public interface PrSketchService {

    /**
     * 上传效果图文件
     * @param file
     * @param prototypeId
     * @return 记录id
     */
    boolean upload(MultipartFile file, Long prototypeId) throws IOException, InvalidExtensionException;


    /**
     * 查询原型图下的效果图记录
     * @param prototypeId
     * @return
     */
    List<PrSketchListVO> queryByPrototypeId(Long prototypeId);


    /**
     * 删除效果图记录
     * @param id
     * @returni
     */
    boolean delete(Long id) throws IOException;

    /**
     * 获取效果图记录下所有效果图文件
     * @param sketchId
     * @return
     */
    List<PrSketchFile> selectSketchFile(Long sketchId);

    /**
     * 根据效果图记录id获取效果图集合
     * @param sketchId
     * @return
     */
    List<SketchFileVO> querySketchFileBySketchId(Long sketchId);
}
