package com.oaker.prototype.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.service.impl.UserServiceImpl;
import com.oaker.prototype.doman.columns.PrColumns;
import com.oaker.prototype.doman.entity.PrProtoRecord;
import com.oaker.prototype.doman.vo.PrProtoRecordVO;
import com.oaker.prototype.mapper.PrProtoRecordMapper;
import com.oaker.prototype.service.PrProtoRecordService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Description : 原型记录
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/18 13:51
 */
@Service
public class PrProtoRecordServiceImpl extends ServiceImpl<PrProtoRecordMapper, PrProtoRecord> implements PrProtoRecordService {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private PrPrototypeServiceImpl prPrototypeService;

    @Override
    public Long create(String fileName, String path, Long prototypeId, String prototypeUrl) {
        PrProtoRecord record = new PrProtoRecord();
        record.setPath(path)
                .setFileName(fileName)
                .setPrototypeId(prototypeId)
                .setPrototypeUrl(prototypeUrl)
                .setCreateUser(SecurityUtils.getUserId());
        baseMapper.insert(record);
       return record.getId();
    }

    @Override
    public List<PrProtoRecordVO> queryByProtoId(Long protoId) {
        EntityWrapper<PrProtoRecord> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrProtoRecord.prototypeId, protoId);
        wrapper.orderDesc(Collections.singletonList(PrColumns.id));
        List<PrProtoRecord> records = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        PrProtoRecordVO vo;
        SysUserEntity userEntity;
        List<PrProtoRecordVO> list = new ArrayList<>(records.size());
        for (PrProtoRecord record : records) {
            userEntity = userService.selectById(record.getCreateUser());
            vo = new PrProtoRecordVO();
            vo.setId(record.getId())
                    .setFileName(record.getFileName())
                    .setCreateTime(record.getCreateTime())
                    .setCreateUser(record.getCreateUser())
                    .setPrototypeUrl(record.getPrototypeUrl())
                    .setCreateUserName(userEntity.getNickName());
            list.add(vo);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRecord(Long id) throws IOException {
        PrProtoRecord record = baseMapper.selectById(id);
        if (Objects.isNull(record)) {
            return false;
        }
        FileUtils.deleteDirectory(new File(record.getPath()));
        prPrototypeService.deleteRecord(record.getPrototypeId(), id);
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public synchronized void deleteRecordLimit(Long protoId) throws IOException {
        EntityWrapper<PrProtoRecord> wrapper = new EntityWrapper<>();
        wrapper.eq(PrColumns.PrProtoRecord.prototypeId, protoId);
        wrapper.orderDesc(Collections.singleton(PrColumns.id));
        List<PrProtoRecord> records = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(records) || records.size() <= 10) {
            return;
        }
        for (int i = 10; i < records.size(); i++) {
            PrProtoRecord record = records.get(i);
            this.deleteRecord(record.getId());
        }
    }
}
