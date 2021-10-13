package com.oaker.system.domain.vo;

import lombok.Data;

/**
 * @Description : 用户职位信息
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/10 16:40
 */
@Data
public class SysPostUserVO {

    /** 职位id */
    private Long postId;

    /** 职位名称 */
    private String postName;

    /** 用户id */
    private Long userId;

}
