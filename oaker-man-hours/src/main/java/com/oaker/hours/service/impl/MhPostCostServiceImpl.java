package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.doman.dto.MhPostCostDTO;
import com.oaker.hours.doman.entity.MhPostCost;
import com.oaker.hours.doman.vo.MhPostCostVO;
import com.oaker.hours.mapper.MhPostCostMapper;
import com.oaker.hours.service.MhPostCostService;
import com.oaker.system.service.impl.SysPostServiceImpl;
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
 * @Data : 2021/11/5 10:28
 */
@Service
public class MhPostCostServiceImpl extends ServiceImpl<MhPostCostMapper, MhPostCost> implements MhPostCostService {

    @Resource
    private SysPostServiceImpl sysPostService;

    @Override
    public TableDataInfo query(String postName, int pageStart, int pageSize) {
        TableDataInfo info = new TableDataInfo();
        int count = sysPostService.countPost(postName);
        if (count <= 0) {
            return info;
        }
        List<MhPostCostVO> list = baseMapper.queryPostCost(postName, pageStart, pageSize);
        info.setTotal(count);
        info.setRows(list);
        info.setCode(HttpStatus.SUCCESS);
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setCost(List<MhPostCostDTO> postCosts) {
        MhPostCost cost;
        Date date = new Date();
        Long userId = SecurityUtils.getUserId();
        for (MhPostCostDTO postCost : postCosts) {
            cost = baseMapper.selectById(postCost.getPostId());
            if (!Objects.isNull(cost)) {
                cost.setCost(postCost.getCost())
                  .setUpdateUser(userId)
                  .setUpdateTime(date);
                baseMapper.updateById(cost);
                continue;
            }
            cost = new MhPostCost();
            cost.setPostId(postCost.getPostId())
                    .setCost(postCost.getCost())
                    .setCreateUser(userId)
                    .setCreateTime(date);
            baseMapper.insert(cost);
        }
        return true;
    }
}
