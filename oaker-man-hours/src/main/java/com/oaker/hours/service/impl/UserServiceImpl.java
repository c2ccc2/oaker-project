package com.oaker.hours.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oaker.hours.doman.entity.SysUserEntity;
import com.oaker.hours.mapper.UserMapper;
import com.oaker.hours.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/14 16:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUserEntity>implements UserService {
}
