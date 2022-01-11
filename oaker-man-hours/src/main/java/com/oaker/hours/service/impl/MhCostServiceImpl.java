package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.common.config.AppConfig;
import com.oaker.common.constant.HttpStatus;
import com.oaker.common.core.domain.IdName;
import com.oaker.common.core.page.TableDataInfo;
import com.oaker.common.core.redis.RedisCache;
import com.oaker.common.utils.SecurityUtils;
import com.oaker.hours.config.CostConfig;
import com.oaker.hours.doman.dto.MhCostListDTO;
import com.oaker.hours.doman.dto.MhPostCostDTO;
import com.oaker.hours.doman.dto.MhUserCostDTO;
import com.oaker.hours.doman.dto.UserHourSaveDTO;
import com.oaker.hours.doman.dto.UserHourUpdateDTO;
import com.oaker.hours.doman.entity.MhCost;
import com.oaker.hours.doman.entity.MhPostCost;
import com.oaker.hours.doman.entity.MhProject;
import com.oaker.hours.doman.entity.MhUserCost;
import com.oaker.hours.doman.entity.MhUserHour;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.doman.vo.CostConfigVO;
import com.oaker.hours.doman.vo.MhCostListVO;
import com.oaker.hours.mapper.MhCostMapper;
import com.oaker.hours.service.MhCostService;
import com.oaker.system.domain.SysPost;
import com.oaker.system.service.impl.SysPostServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/11/4 16:10
 */
@Service
public class MhCostServiceImpl extends ServiceImpl<MhCostMapper, MhCost> implements MhCostService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private SysPostServiceImpl sysPostService;

    @Resource
    private MhUserHourServiceImpl userHourService;

    @Resource
    private MhPostCostServiceImpl postCostService;

    @Resource
    private MhUserCostServiceImpl userCostService;

    @Resource
    private ProjectUserServiceImpl projectUserService;


    @Override
    public CostConfigVO get() {
        CostConfigVO vo = new CostConfigVO();
        vo.setPostCost(CostConfig.POST_COST);
        vo.setUserCost(CostConfig.USER_COST);
        return vo;
    }

    @Override
    public boolean update(Boolean postCost, Boolean userCost) {
        if (!Objects.isNull(postCost)) {
            redisCache.setCacheMapValue(CostConfig.REDIS_KEY, CostConfig.REDIS_KEY_POST_COST, postCost);
            CostConfig.POST_COST = postCost;
        }
        if (!Objects.isNull(userCost)) {
            redisCache.setCacheMapValue(CostConfig.REDIS_KEY, CostConfig.REDIS_KEY_USER_COST, userCost);
            CostConfig.USER_COST = userCost;
        }
        return true;
    }

    @Override
    public TableDataInfo queryPost(String postName, int pageStart, int pageSize) {
        return postCostService.query(postName, pageStart, pageSize);
    }

    @Override
    public boolean setPostCost(List<MhPostCostDTO> postCosts) {
        return postCostService.setCost(postCosts);
    }

    @Override
    public TableDataInfo queryUser(String nickName, int pageStart, int pageSize) {
        return userCostService.query(nickName, pageStart, pageSize);
    }

    @Override
    public boolean setUserCost(List<MhUserCostDTO> userCosts) {
        return userCostService.setCost(userCosts);
    }

    @Override
    public boolean create(UserHourSaveDTO userHourSaveDTO) {
        List<UserHourSaveDTO.ProjectHour> projectHours = userHourSaveDTO.getProjectHours();
        List<MhCost> mhCostList = new ArrayList<>(projectHours.size());
        MhCost mhCost;
        Date date = new Date();
        Long userId = SecurityUtils.getUserId();
        LocalDate costDate = userHourSaveDTO.getDate();
        BigDecimal postCost = BigDecimal.ZERO;
        BigDecimal userCost = BigDecimal.ZERO;
        for (UserHourSaveDTO.ProjectHour projectHour : projectHours) {
            BigDecimal hour = projectHour.getHour();
            if (hour.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            mhCost = new MhCost();
            // 如果开启岗位成本统计
            if (CostConfig.POST_COST) {
                SysPost sysPost = sysPostService.getByUserId(userId);
                MhPostCost cost = postCostService.selectById(sysPost.getPostId());
                if (!Objects.isNull(cost)) {
                    postCost = this.computeCost(cost.getCost(), hour);
                }
            }
            // 如果开启人员成本统计
            if (CostConfig.USER_COST) {
                MhUserCost mhUserCost = userCostService.selectById(userId);
                if (!Objects.isNull(mhUserCost)) {
                    userCost = this.computeCost(mhUserCost.getCost(), hour);
                }
            }
            MhProject mhProject = projectService.selectById(projectHour.getProjectId());
            mhCost.setProjectId(mhProject.getProjectId())
                    .setProjectStatus(mhProject.getProjectStatus())
                    .setCreateTime(date)
                    .setCostDate(costDate)
                    .setUserId(userId)
                    .setUseHour(hour)
                    .setPostCost(postCost)
                    .setUserCost(userCost);
            mhCostList.add(mhCost);
        }
        return this.insertBatch(mhCostList);
    }

    /**
     *   岗位成本 = 岗位成本总计 / 每月工作天数 / 每日工作时数 * 上报工时
     *  人员成本 = 人员成本总计 / 每月工作天数 / 每日工作时数 * 上报工时
     * @param appCost
     * @param hour
     * @return
     */
    private BigDecimal computeCost(BigDecimal appCost, BigDecimal hour) {
        return appCost.divide(new BigDecimal(AppConfig.WORK_DAY), 2, RoundingMode.HALF_UP)
                .divide(AppConfig.WORK_TIME, 2, RoundingMode.HALF_UP)
                .multiply(hour);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCost(UserHourUpdateDTO userHourUpdateDTO) {
        MhUserHour userHour = userHourService.selectById(userHourUpdateDTO.getHourId());
        Long userId = SecurityUtils.getUserId();
        LocalDate costDate = userHour.getFillDate();
        BigDecimal postCost = BigDecimal.ZERO;
        BigDecimal userCost = BigDecimal.ZERO;
        List<UserHourUpdateDTO.ProjectHourDetail> projectHours = userHourUpdateDTO.getProjectHours();
        for (UserHourUpdateDTO.ProjectHourDetail projectHour : projectHours) {
            MhCost mhCost = new MhCost();
            mhCost.setUserId(userId)
                    .setProjectId(projectHour.getProjectId())
                    .setCostDate(costDate);
            mhCost = baseMapper.selectOne(mhCost);
            BigDecimal useHour = projectHour.getHour();
            if (!Objects.isNull(mhCost)) {
                SysPost sysPost = sysPostService.getByUserId(userId);
                MhPostCost cost = postCostService.selectById(sysPost.getPostId());
                if (!Objects.isNull(cost)) {
                    postCost = this.computeCost(cost.getCost(), useHour);
                }
                // 人员成本统计
                MhUserCost mhUserCost = userCostService.selectById(userId);
                if (!Objects.isNull(mhUserCost)) {
                    userCost = this.computeCost(mhUserCost.getCost(), useHour);
                }
                mhCost.setUseHour(useHour)
                        .setUserCost(userCost)
                        .setPostCost(postCost);
                baseMapper.updateById(mhCost);
                continue;
            }
            if (useHour.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            // 如果开启岗位成本统计
            if (CostConfig.POST_COST) {
                SysPost sysPost = sysPostService.getByUserId(userId);
                MhPostCost cost = postCostService.selectById(sysPost.getPostId());
                if (!Objects.isNull(cost)) {
                    postCost = this.computeCost(cost.getCost(), useHour);
                }
            }
            // 如果开启人员成本统计
            if (CostConfig.USER_COST) {
                MhUserCost mhUserCost = userCostService.selectById(userId);
                if (!Objects.isNull(mhUserCost)) {
                    userCost = this.computeCost(mhUserCost.getCost(), useHour);
                }
            }
            MhProject mhProject = projectService.selectById(projectHour.getProjectId());
            mhCost = new MhCost();
            mhCost.setProjectId(mhProject.getProjectId())
                    .setProjectStatus(mhProject.getProjectStatus())
                    .setCreateTime(new Date())
                    .setCostDate(costDate)
                    .setUserId(userId)
                    .setUseHour(useHour)
                    .setPostCost(postCost)
                    .setUserCost(userCost);
            baseMapper.insert(mhCost);
        }
        return true;
    }

    @Override
    public TableDataInfo query(MhCostListDTO mhCostDTO, int pageStart, int pageSize) {
        TableDataInfo dataInfo = new TableDataInfo();
        int count = baseMapper.count(mhCostDTO);
        if (count <= 0) {
            return dataInfo;
        }
        List<MhCostListVO> query = baseMapper.query(mhCostDTO, pageStart, pageSize);
        query.forEach(vo -> {
            if (!Objects.isNull(vo.getPostId())) {
                SysPost sysPost = sysPostService.selectPostById(vo.getPostId());
                if (!Objects.isNull(sysPost)) {
                    vo.setPostName(sysPost.getPostName());
                }
                SysUserEntity userEntity = userService.selectById(vo.getUserId());
                vo.setNickName(userEntity.getNickName());
            }
        });
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setTotal(count);
        dataInfo.setRows(query);
        return dataInfo;
    }

    @Override
    public List<IdName> queryUserAll(Long projectId) {
        return projectUserService.queryUserAll(projectId);
    }

    @Override
    public List<IdName> queryPostAll(Long projectId) {
        return projectUserService.queryPostAll(projectId);
    }
}
