package com.oaker.web.controller.tool.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/8 10:23
 */
@Api(tags = "用户信息管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/doc/system/user")
public class UserApi {

    @GetMapping("/box")
    @ApiOperation("获取用户下拉框")
    public List<IdName> queryBox() {
        IdName idName = new IdName();
        idName.setId(1L);
        idName.setName("admin");
        ArrayList<IdName> idNames = new ArrayList<>();
        idNames.add(idName);
        return idNames;
    }


    @ApiModel(value = "IdName", description = "用户实体")
    class IdName {
        @ApiModelProperty("用户ID")
        private Long id;

        @ApiModelProperty("用户名称")
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
