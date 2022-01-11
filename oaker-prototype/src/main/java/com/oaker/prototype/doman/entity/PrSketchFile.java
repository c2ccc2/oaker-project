package com.oaker.prototype.doman.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description : 效果图文件
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/12/17 15:37
 */
@Data
@Accessors(chain = true)
@TableName("pr_sketch_file")
public class PrSketchFile {

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 效果图记录id */
    @TableField(value = "sketch_id")
    private Long sketchId;

    /** 文档名称 */
    @TableField(value = "file_name")
    private String fileName;

    /** 原型记录id */
    @TableField(value = "prototype_id")
    private Long prototypeId;

    /** 文件地址 */
    @TableField(value = "file_url")
    private String fileUrl;

    /** 文件存放路径 */
    @TableField(value = "path")
    private String path;

    /** 创建人 */
    @TableField(value = "create_user")
    private Long createUser;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;


}
