package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.dto.MhUserCostDTO;
import com.oaker.hours.doman.entity.MhUserCost;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.MhUserCostVO;
import com.oaker.hours.mapper.MhUserCostMapper;
import com.oaker.hours.service.MhUserCostService;
import com.oaker.system.domain.SysPost;
import com.oaker.system.service.impl.SysPostServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/5 14:03
 */
@Service
public class MhUserCostServiceImpl extends ServiceImpl<MhUserCostMapper, MhUserCost> implements MhUserCostService {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private SysPostServiceImpl sysPostService;

    @Override
    public TableDataInfo query(String nickName, int pageStart, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        EntityWrapper<SysUserEntity> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.delFlag, '0');
        if (StringUtils.isNotBlank(nickName)) {
            wrapper.like(Columns.SysUserEntity.nickName, nickName);
        }
        int count = userService.selectCount(wrapper);
        if (count == 0) {
            return dataInfo;
        }
        List<MhUserCostVO> list = baseMapper.queryUserCost(nickName, pageStart, pageSize);
        SysPost post;
        for (MhUserCostVO mhUserCostVO : list) {
            post = sysPostService.getByUserId(mhUserCostVO.getUserId());
            mhUserCostVO.setPostId(post.getPostId());
            mhUserCostVO.setPostName(post.getPostName());
        }
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(count);
        dataInfo.setRows(list);
        return dataInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setCost(List<MhUserCostDTO> userCosts) {
        MhUserCost cost;
        Date date = new Date();
        Long userId = SecurityUtils.getUserId();
        for (MhUserCostDTO userCost : userCosts) {
            cost = baseMapper.selectById(userCost.getUserId());
            if (!Objects.isNull(cost)) {
                cost.setCost(userCost.getCost())
                        .setUpdateUser(userId)
                        .setUpdateTime(date);
                baseMapper.updateById(cost);
                continue;
            }
            cost = new MhUserCost();
            cost.setUserId(userCost.getUserId())
                    .setCost(userCost.getCost())
                    .setCreateUser(userId)
                    .setCreateTime(date);
            baseMapper.insert(cost);
        }
        return true;
    }
}
