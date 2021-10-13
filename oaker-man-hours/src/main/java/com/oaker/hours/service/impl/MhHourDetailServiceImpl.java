package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.hours.doman.columns.Columns;
import com.oaker.hours.doman.entity.MhHourDetail;
import com.oaker.hours.mapper.MhHourDetailMapper;
import com.oaker.hours.service.MhHourDetailService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/13 15:14
 */
@Service
public class MhHourDetailServiceImpl extends ServiceImpl<MhHourDetailMapper, MhHourDetail> implements MhHourDetailService {

    @Override
    public MhHourDetail queryProjectUserHour(Long projectId, Long userId, LocalDate localDate) {
        EntityWrapper<MhHourDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(Columns.MhHourDetail.projectId, projectId)
                .and()
                .eq(Columns.MhHourDetail.userId, userId);
        if (!Objects.isNull(localDate)) {
            wrapper.eq(Columns.MhHourDetail.fillDate, localDate);
        }
        MhHourDetail mhHourDetail = new MhHourDetail();
        mhHourDetail.setProjectId(projectId)
                .setUserId(userId);
        if (!Objects.isNull(localDate)) {
            mhHourDetail.setFillDate(localDate);
        }
        return baseMapper.selectOne(mhHourDetail);
    }

}
