package com.luoromeo.bms.config.mybatis;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

/**
 * @description
 * @author zhanghua.luo
 * @date 2019年07月13日 02:34:45
 * @modified By
 */
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object gmtCreate = this.getFieldValByName("gmtCreate", metaObject);
        Object gmtModified = this.getFieldValByName("gmtModified。", metaObject);
        Date currTime = new Date();
        if (null == gmtCreate) {
            metaObject.setValue("gmtCreate", currTime);
        }
        if (null == gmtModified) {
            metaObject.setValue("gmtModified", currTime);
        }
        metaObject.setValue("delFlag", "0");
        metaObject.setValue("createUser", "罗长华");
        metaObject.setValue("updateUser", "罗长华");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currTime = new Date();
        setFieldValByName("gmtModified", currTime, metaObject);
        setFieldValByName("updateUser", "罗长华", metaObject);
    }
}
