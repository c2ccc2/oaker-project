package com.oaker.prototype.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.config.OakerConfig;
import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.common.utils.file.FileUploadUtils;
import com.oaker.common.utils.file.FileUtils;
import com.oaker.common.utils.uuid.IdUtils;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.service.impl.UserServiceImpl;
import com.oaker.prototype.doman.columns.PrColumns;
import com.oaker.prototype.doman.entity.PrPrototype;
import com.oaker.prototype.doman.entity.PrSketch;
import com.oaker.prototype.doman.entity.PrSketchFile;
import com.oaker.prototype.doman.vo.PrSketchListVO;
import com.oaker.prototype.doman.vo.SketchFileVO;
import com.oaker.prototype.mapper.PrSketchFileMapper;
import com.oaker.prototype.mapper.PrSketchMapper;
import com.oaker.prototype.service.PrSketchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/17 11:08
 */
@Service
public class PrSketchServiceImpl extends ServiceImpl<PrSketchMapper, PrSketch> implements PrSketchService {

    private static final String[] PROTO_ALLOWED_EXTENSION = {"zip"};

    private static final String SEPARATOR = "/";

    @Resource
    private UserServiceImpl userService;

    @Resource
    private PrPrototypeServiceImpl prototypeService;

    @Resource
    private PrSketchFileMapper sketchFileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upload(MultipartFile file, Long prototypeId) throws IOException, InvalidExtensionException {
        // 文件路径 basepath + projectId + prototypeId + sketchId + UUID
        PrPrototype prototype = prototypeService.getById(prototypeId);
        Long projectId = prototype.getProjectId();
        String filePath = OakerConfig.getSketchPath();
        String uuid = IdUtils.fastSimpleUUID();
        String tmpPath = filePath + SEPARATOR + uuid + SEPARATOR;
        // 保存压缩文件
        FileUploadUtils.upload(tmpPath, file, PROTO_ALLOWED_EXTENSION, false);
        String fileName = file.getOriginalFilename();

        Long sketchId = this.save(prototypeId, fileName);
        filePath = filePath + SEPARATOR + projectId + SEPARATOR + prototypeId + SEPARATOR + sketchId + SEPARATOR + uuid;
        Map<String, String> fileMap = FileUtils.unzip(tmpPath, fileName, filePath);
        this.createSketchFile(fileMap, prototypeId, sketchId, filePath);
        this.updatePath(sketchId, filePath);
        prototypeService.setSketchId(prototypeId, sketchId);
        return Boolean.TRUE;
    }

    private Long save(Long prototypeId, String fileName) {
        Long userId = SecurityUtils.getUserId();
        PrSketch prSketch = new PrSketch();
        prSketch.setPrototypeId(prototypeId)
                .setFileName(fileName)
                .setCreateUser(userId)
                .setCreateTime(new Date());
        baseMapper.insert(prSketch);
        return prSketch.getId();
    }

    private void updatePath(Long id, String path) {
        PrSketch prSketch = new PrSketch();
        prSketch.setId(id)
                .setPath(path);
        baseMapper.updateById(prSketch);
    }

    private void createSketchFile(Map<String, String> fileMap, Long prototypeId, Long sketchId, String filePath) {
        PrSketchFile sketchFile;
        String fileUrl, fileName;
        Date date = new Date();
         Long userId = SecurityUtils.getUserId();
        for (Map.Entry<String, String> map : fileMap.entrySet()) {
            sketchFile = new PrSketchFile();
            fileUrl = FileUploadUtils.getPathFileName(filePath, map.getKey());
            fileName = map.getKey().substring(map.getKey().lastIndexOf("/") + 1);
            sketchFile.setPrototypeId(prototypeId)
                    .setSketchId(sketchId)
                    .setFileName(fileName)
                    .setPath(map.getValue())
                    .setFileUrl(fileUrl)
                    .setCreateUser(userId)
                    .setCreateTime(date);
            sketchFileMapper.insert(sketchFile);
        }
    }

    @Override
    public List<PrSketchListVO> queryByPrototypeId(Long prototypeId) {

        EntityWrapper<PrSketch> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrSketch.prototypeId, prototypeId);
        wrapper.orderDesc(Collections.singletonList(PrColumns.id));
        List<PrSketch> prSketches = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(prSketches)) {
            return Collections.emptyList();
        }
        List<PrSketchListVO> list = new ArrayList<>(prSketches.size());
        PrSketchListVO vo;
        SysUserEntity userEntity;
        for (PrSketch prSketch : prSketches) {
            vo = new PrSketchListVO();
            userEntity = userService.selectById(prSketch.getCreateUser());
            vo.setId(prSketch.getId())
                    .setFileName(prSketch.getFileName())
                    .setPrototypeId(prototypeId)
                    .setCreateUser(userEntity.getUserId())
                    .setCreateUserName(userEntity.getNickName())
                    .setCreateTime(prSketch.getCreateTime());
            list.add(vo);
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws IOException {
        PrSketch prSketch = baseMapper.selectById(id);
        org.apache.commons.io.FileUtils.deleteDirectory(new File(prSketch.getPath()));
        EntityWrapper<PrSketchFile> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrSketchFile.sketchId, id);
        sketchFileMapper.delete(wrapper);
        baseMapper.deleteById(id);
        PrPrototype prPrototype = prototypeService.selectById(prSketch.getPrototypeId());
        if (Objects.equals(prPrototype.getSketchId(), id)) {
            prototypeService.deleteSketch(prPrototype.getId(), id);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<PrSketchFile> selectSketchFile(Long sketchId) {
        EntityWrapper<PrSketchFile> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrSketchFile.sketchId, sketchId);
        return sketchFileMapper.selectList(wrapper);
    }

    @Override
    public List<SketchFileVO> querySketchFileBySketchId(Long sketchId) {
        List<PrSketchFile> prSketchFiles = this.selectSketchFile(sketchId);
        List<SketchFileVO> list = new ArrayList<>(prSketchFiles.size());
        SketchFileVO vo;
        for (PrSketchFile prSketchFile : prSketchFiles) {
            vo = new SketchFileVO();
            vo.setId(prSketchFile.getId())
                    .setFileName(prSketchFile.getFileName())
                    .setFileUrl(prSketchFile.getFileUrl());
            list.add(vo);
        }
        return list;
    }
}
