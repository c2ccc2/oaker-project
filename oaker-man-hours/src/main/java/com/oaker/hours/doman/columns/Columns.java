package com.oaker.hours.doman.columns;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/9/7 16:40
 */
public interface Columns {

    String id = "id";
    String deleted = "deleted";
    String delFlag = "del_flag";
    String createBy = "create_by";
    String createTime = "create_time";
    String updateBy = "update_by";
    String updateTime = "update_time";

    interface Project {
        String projectId = "project_id";
        String projectName = "project_name";
        String projectCode = "project_code";
        String projectManager = "project_manager";
        String enable = "enable";
        String startDate = "start_date";
        String endDate = "end_date";
        String projectStatus = "project_status";
        String remark = "remark";
    }

    interface ProjectHour {
        String projectId = "project_id";
        String manHour = "man_hour";
        String useHour = "use_hour";
    }

    interface ProjectUser {
        String id = "id";
        String projectId = "project_id";
        String userId = "user_id";
        String status = "status";
        String removeTime = "remove_time";
        String everyday = "everyday";
        String createUser = "create_user";
        String createTime = "create_time";
        String updateUser = "update_user";
        String updateTime = "update_time";
    }

    interface MhUserHour {
        String userId = "user_id";
        String fillDate = "fill_date";
        String totalHour = "total_hour";
    }

    interface MhHourDetail {
        String hourId = "hour_id";
        String userId = "user_id";
        String projectId = "project_id";
        String useHour = "use_hour";
        String fillDate = "fill_date";
    }

    interface MhUserHourMiss {
        String userId = "user_id";
        String missDate = "miss_date";
    }

    interface MhUserHourMissDetail {
        String missId = "miss_id";
        String projectId = "project_id";
        String userId = "user_id";
        String missDate = "miss_date";
    }

    interface SysUserEntity {
        String deptId = "dept_id";
        String userName = "user_name";
        String nickName = "nick_name";
        String userType = "user_type";
        String email = "email";
        String phonenumber = "phonenumber";
        String sex = "sex";
        String avatar = "avatar";
        String password = "password";
        String status = "status";
        String delFlag = "del_flag";
        String loginIp = "login_ip";
        String loginDate = "login_date";
        String createBy = "create_by";
        String createTime = "create_time";
        String updateBy = "update_by";
        String updateTime = "update_time";
        String remark = "remark";
    }

    interface MhPostCost {
        String postId = "post_id";
        String cost = "cost";
        String createUser = "create_user";
        String updateUser = "update_user";
    }

    interface MhUserCost {
        String postId = "user_id";
        String cost = "cost";
        String createUser = "create_user";
        String updateUser = "update_user";
    }

    interface MhCost {
        String projectId = "project_id";
        String userId = "user_id";
        String postCost = "post_cost";
        String userCost = "user_cost";
        String costDate = "cost_date";
        String projectStatus = "project_status";
    }

    interface MhUserLeave {
        String userId = "user_id";
        String leaveDate = "leave_date";
        String leaveType = "leave_type";
    }

    interface MhUserLeaveDetail {
        String leaveId = "leave_id";
        String projectId = "project_id";
        String projectStatus = "project_status";
        String userId = "user_id";
        String leaveDate = "leave_date";
        String leaveType = "leave_type";
    }

    public static void main(String[] args) {
        Field[] fields = MhUserLeave.class.getDeclaredFields();
        for (Field field : fields) {
            String column = null;
            TableId tableId = field.getAnnotation(TableId.class);
            if (!Objects.isNull(tableId)) {
                column = tableId.value();
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (!Objects.isNull(tableField)) {
                column = tableField.value();
            }
            if (StringUtils.isBlank(column)) {
                continue;
            }
            String format = String.format("String %s = \"%s\";", field.getName(), column);
            System.out.println(format);
        }
    }

}
