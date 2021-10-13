package com.oaker.system.domain.vo;

import com.oaker.common.core.domain.entity.SysUser;
import lombok.Data;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/10 14:50
 */
@Data
public class SysUserVO extends SysUser {


    /** 岗位名称 */
    private String postName;

    /** 角色名称 */
    private String roleName;

}
