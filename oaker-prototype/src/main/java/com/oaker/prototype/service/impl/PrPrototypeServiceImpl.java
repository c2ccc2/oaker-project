package com.oaker.prototype.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.config.OakerConfig;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.exception.ServiceException;
import com.oaker.common.exception.file.InvalidExtensionException;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.common.utils.file.FileUploadUtils;
import com.oaker.common.utils.file.FileUtils;
import com.oaker.common.utils.uuid.IdUtils;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.UserProjectShortVO;
import com.oaker.hours.service.ProjectUserService;
import com.oaker.hours.service.impl.UserServiceImpl;
import com.oaker.prototype.constant.PrConstants;
import com.oaker.prototype.doman.columns.PrColumns;
import com.oaker.prototype.doman.entity.PrDoc;
import com.oaker.prototype.doman.entity.PrPrototype;
import com.oaker.prototype.doman.vo.PrPrototypeListVO;
import com.oaker.prototype.mapper.PrPrototypeMapper;
import com.oaker.prototype.service.PrPrototypeService;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/15 16:50
 */
@Service
public class PrPrototypeServiceImpl extends ServiceImpl<PrPrototypeMapper, PrPrototype> implements PrPrototypeService {

    private static final String[] PROTO_ALLOWED_EXTENSION = { "rar", "zip"};

    @Resource
    private UserServiceImpl userService;

    @Resource
    private PrDocServiceImpl prDocService;

    @Resource
    private ProjectUserService projectUserService;

    @Resource
    private PrProtoRecordServiceImpl prProtoRecordService;

    @Override
    public Long create(String name, Long projectId) {
        PrPrototype prPrototype = new PrPrototype();
        prPrototype.setName(name)
                .setProjectId(projectId)
                .setPubId(IdUtils.fastSimpleUUID())
                 .setCreateUser(SecurityUtils.getUserId());
        baseMapper.insert(prPrototype);
        return prPrototype.getId();
    }

    @Override
    public boolean modify(Long id, String name) {
        EntityWrapper<PrPrototype> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.id, id);
        PrPrototype prPrototype = new PrPrototype();
        prPrototype.setName(name)
                .setUpdateUser(SecurityUtils.getUserId())
                .setUpdateTime(new Date());
        Integer update = baseMapper.update(prPrototype, wrapper);
        return update > 0;
    }

    @Override
    public PrPrototype getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long upload(MultipartFile file, Long prototypeId) throws IOException, InvalidExtensionException {

        // 上传文件路径   basePath + "pr" + projectId + prototypeId + UUID + fileName
        String filePath = OakerConfig.getPrPrototypePath();
        String uuid = IdUtils.fastSimpleUUID();
        String tmpPath = filePath + "/" + uuid + "/";
        // 保存压缩文件
        FileUploadUtils.upload(tmpPath, file, PROTO_ALLOWED_EXTENSION, false);
        String fileName = file.getOriginalFilename();
        String fileNameNoEx = FileUploadUtils.getFileNameNoEx(fileName);
        try {
            PrPrototype prPrototype = baseMapper.selectById(prototypeId);
            filePath = filePath + "/"
                    + prPrototype.getProjectId() + "/"
                    + prototypeId + "/"
                    + uuid;
            // 如果已经上传过的情况
            if (!Objects.isNull(prPrototype.getRecordId())) {
                FileUtils.upzipFile(new File(tmpPath + fileName), filePath);
                String url = FileUploadUtils.getPathFileName(filePath, fileNameNoEx) + "/" + "start.html";
                Long recordId = prProtoRecordService.create(fileNameNoEx, filePath, prototypeId, url);
                prPrototype.setRecordId(recordId);
                baseMapper.updateById(prPrototype);
                prProtoRecordService.deleteRecordLimit(prototypeId);
                return recordId;
            }
            File zipFile = new File(tmpPath + fileName);
            FileUtils.upzipFile(zipFile, filePath);
            String url = FileUploadUtils.getPathFileName(filePath, FileUploadUtils.getFileNameNoEx(fileName)) + "/" + "start.html";
            Long recordId = prProtoRecordService.create(fileNameNoEx, filePath, prototypeId, url);
            prPrototype.setRecordId(recordId);
            baseMapper.updateById(prPrototype);
            prProtoRecordService.deleteRecordLimit(prototypeId);
            return recordId;
        } finally {
            org.apache.commons.io.FileUtils.deleteDirectory(new File(tmpPath));
        }
    }

    @Override
    public TableDataInfo query(Long projectId, int pageNum, int pageSize) {
        TableDataInfo tableDataInfo = new TableDataInfo();
        EntityWrapper<PrPrototype> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrPrototype.projectId, projectId)
                .and()
                .eq(PrColumns.deleted, Boolean.FALSE);
        Integer count = baseMapper.selectCount(wrapper);
        if (count <= 0) {
            tableDataInfo.setCode(HttpStatus.SUCCESS);
            tableDataInfo.setRows(Collections.emptyList());
            return tableDataInfo;
        }
        wrapper.orderDesc(Collections.singleton(PrColumns.id));
        Page<PrPrototype> page = new Page<>(pageNum, pageSize);
        List<PrPrototype> prPrototypes = baseMapper.selectPage(page, wrapper);
        List<PrDoc> prDocs;
        PrPrototypeListVO vo;
        String prototypeUrl = null;
        SysUserEntity sysUserEntity;
        List<PrPrototypeListVO> list = new ArrayList<>(prPrototypes.size());
        for (PrPrototype prPrototype : prPrototypes) {
            vo = new PrPrototypeListVO();
            Long userId = prPrototype.getCreateUser();
            sysUserEntity = userService.selectById(userId);
            if (!Objects.isNull(prPrototype.getRecordId())) {
                prototypeUrl = PrConstants.PROTOTYPE_URL_BASE + prPrototype.getPubId();
            }
            prDocs = prDocService.queryByProtoId(prPrototype.getId());
            List<PrPrototypeListVO.Doc> docList = new ArrayList<>(prDocs.size());
            if (!CollectionUtils.isEmpty(prDocs)) {
                prDocs.forEach(prDoc -> {
                    SysUserEntity user = userService.selectById(prDoc.getCreateUser());
                    PrPrototypeListVO.Doc doc = new PrPrototypeListVO.Doc();
                    doc.setId(prDoc.getId())
                            .setDocName(prDoc.getDocName())
                            .setDocUrl(prDoc.getDocUrl())
                            .setCreateUser(prDoc.getCreateUser())
                            .setCreateUserName(user.getNickName())
                            .setCreateTime(prDoc.getCreateTime());
                    docList.add(doc);
                });
            }
            vo.setId(prPrototype.getId())
                    .setName(prPrototype.getName())
                    .setProjectId(prPrototype.getProjectId())
                    .setRecordId(prPrototype.getRecordId())
                    .setRemark(prPrototype.getRemark())
                    .setCreateTime(prPrototype.getCreateTime())
                    .setCreateUser(userId)
                    .setCreateUserName(sysUserEntity.getNickName())
                    .setPrototypeUrl(prototypeUrl)
                    .setDocList(docList);
            list.add(vo);
        }
        tableDataInfo.setTotal(count);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setRows(list);
        return tableDataInfo;
    }

    @Override
    public boolean delete(Long id) {
        PrPrototype prPrototype = baseMapper.selectById(id);
        if (Objects.isNull(prPrototype)) {
            return false;
        }
        Long userId = SecurityUtils.getUserId();
        List<UserProjectShortVO> vos = projectUserService.queryMyProject(userId, null);
        Set<Long> collect = vos.stream().map(UserProjectShortVO::getProjectId).collect(Collectors.toSet());
        if (!collect.contains(prPrototype.getProjectId())) {
            throw new ServiceException("当前用户未参与此项目，不能删除原型信息");
        }
        prPrototype.setDeleted(true)
                .setUpdateUser(userId)
                .setUpdateTime(new Date());
        return baseMapper.updateById(prPrototype) > 0;
    }

    @Override
    public boolean deleteRecord(Long id, Long recordId) {
        return baseMapper.deleteRecord(id, recordId) > 0;
    }

    @Override
    public PrPrototype getByPubId(String pubId) {
        PrPrototype prototype = new PrPrototype();
        prototype.setPubId(pubId);
        return baseMapper.selectOne(prototype);
    }
}
