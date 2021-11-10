package com.oaker.prototype.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.config.OakerConfig;
import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.common.utils.file.FileUploadUtils;
import com.oaker.common.utils.uuid.IdUtils;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.service.impl.UserServiceImpl;
import com.oaker.prototype.doman.columns.PrColumns;
import com.oaker.prototype.doman.entity.PrDoc;
import com.oaker.prototype.doman.entity.PrPrototype;
import com.oaker.prototype.doman.vo.PrDocVO;
import com.oaker.prototype.mapper.PrDocMapper;
import com.oaker.prototype.service.PrDocService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/16 16:28
 */
@Service
public class PrDocServiceImpl extends ServiceImpl<PrDocMapper, PrDoc> implements PrDocService {

    private static final String[] DOC_ALLOWED_EXTENSION = {
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf"};

    @Resource
    private UserServiceImpl userService;

    @Resource
    private PrPrototypeServiceImpl prPrototypeService;

    @Override
    public Long upload(MultipartFile file, Long prototypeId) throws IOException, InvalidExtensionException {
        String fileName = file.getOriginalFilename();

        // 上传文件路径   basePath + "doc" + projectId + prototypeId + UUID + fileName
        String filePath = OakerConfig.getPrDocPath();
        PrPrototype prototype = prPrototypeService.getById(prototypeId);
        filePath = filePath
                + "/" + prototype.getProjectId()
                + "/" + prototypeId
                + "/" + IdUtils.fastSimpleUUID();
        // 上传并返回文件名称
        String docUrl = FileUploadUtils.upload(filePath, file, DOC_ALLOWED_EXTENSION, false);
        // 创建文档记录
        Long userId = SecurityUtils.getUserId();
        PrDoc prDoc = new PrDoc();
        prDoc.setPrototypeId(prototypeId)
                .setDocName(fileName)
                .setPath(filePath)
                .setDocUrl(docUrl)
                .setCreateUser(userId);
        baseMapper.insert(prDoc);
        return prDoc.getId();
    }

    @Override
    public boolean deleteDoc(Long id) {
        PrDoc prDoc = baseMapper.selectById(id);
        if (Objects.isNull(prDoc)) {
            return false;
        }
        boolean result = FileUtils.deleteQuietly(new File(prDoc.getPath()));
        baseMapper.deleteById(id);
        return result;
    }

    @Override
    public List<PrDoc> queryByProtoId(Long protoId) {
        EntityWrapper<PrDoc> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrDoc.prototypeId, protoId);
        wrapper.orderDesc(Collections.singleton(PrColumns.id));
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<PrDocVO> queryList(Long protoId) {
        List<PrDoc> prDocs = this.queryByProtoId(protoId);
        if (CollectionUtils.isEmpty(prDocs)) {
            return Collections.emptyList();
        }
        List<PrDocVO> docList = new ArrayList<>(prDocs.size());
        prDocs.forEach(prDoc -> {
            SysUserEntity user = userService.selectById(prDoc.getCreateUser());
            PrDocVO doc = new PrDocVO();
            doc.setId(prDoc.getId())
                    .setDocName(prDoc.getDocName())
                    .setDocUrl(prDoc.getDocUrl())
                    .setCreateUser(prDoc.getCreateUser())
                    .setCreateUserName(user.getNickName())
                    .setCreateTime(prDoc.getCreateTime());
            docList.add(doc);
        });
        return docList;
    }
}
