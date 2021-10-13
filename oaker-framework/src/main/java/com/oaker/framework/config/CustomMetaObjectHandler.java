package com.oaker.framework.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class CustomMetaObjectHandler extends MetaObjectHandler
{

    public static final String COMMON_FIELD_CREATEDATE = "createDate";

    public static final String COMMON_FIELD_UPDATEDATE = "updateDate";

    @Override
    public void insertFill(MetaObject metaObject)
    {
        Object fieldValue = getFieldValByName(COMMON_FIELD_CREATEDATE, metaObject);
        if (fieldValue == null)
        {
            setFieldValByName(COMMON_FIELD_CREATEDATE, new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject)
    {
        Object testType = getFieldValByName(COMMON_FIELD_UPDATEDATE, metaObject);
        if (testType == null)
        {
            setFieldValByName(COMMON_FIELD_UPDATEDATE, new Date(), metaObject);
        }
    }
}
