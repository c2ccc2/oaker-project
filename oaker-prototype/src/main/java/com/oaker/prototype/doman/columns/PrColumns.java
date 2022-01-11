package com.oaker.prototype.doman.columns;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Description :
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/15 17:05
 */
public interface PrColumns {

    String id = "id";
    String deleted = "deleted";
    String createBy = "create_by";
    String createTime = "create_time";
    String updateBy = "update_by";
    String updateTime = "update_time";
    String createUser = "create_user";
    String updateUser = "update_user";

    interface PrPrototype {
        String name = "name";
        String projectId = "project_id";
        String prototypeUrl = "prototype_url";
        String path = "path";
        String pubId = "pub_id";
        String remark = "remark";
    }

    interface PrProtoRecord {
        String prototypeId = "prototype_id";
        String prototypeUrl = "prototype_url";
        String path = "path";
    }

    interface PrDoc {
        String docName = "doc_name";
        String prototypeId = "prototype_id";
        String docUrl = "doc_url";
        String path = "path";
    }

    interface PrSketch {
        String fileName = "file_name";
        String prototypeId = "prototype_id";
        String path = "path";
    }

    interface PrSketchFile {
        String sketchId = "sketch_id";
        String fileName = "file_name";
        String prototypeId = "prototype_id";
        String fileUrl = "file_url";
        String path = "path";
    }



    public static void main(String[] args) {
        Field[] fields = PrSketchFile.class.getDeclaredFields();
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
