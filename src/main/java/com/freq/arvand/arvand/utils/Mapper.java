package com.freq.arvand.arvand.utils;



import com.freq.arvand.arvand.Base.BaseEntity;
import com.freq.arvand.arvand.Base.BaseForm;
import com.freq.arvand.arvand.Base.BaseInterface;
import com.freq.arvand.arvand.annotation.RelatedFiled;
import com.freq.arvand.arvand.annotation.WhatFiled;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Mapper {
    public static boolean copyFormToEntity(BaseForm form, BaseEntity BaseEntity) throws Exception {
        Method[] methods = form.getClass().getMethods();
        Set methodSet = new HashSet();
        for (Method method : methods) {
            if (!method.getName().startsWith("get") && !method.getName().startsWith("is")) {
                continue;
            }
            if (!method.isAnnotationPresent(WhatFiled.class)) {
                continue;
            }
            methodSet.add(method);
        }

        for (Iterator iterator = methodSet.iterator(); iterator.hasNext(); ) {
            Method setMethod;
            Method method = (Method) iterator.next();
            WhatFiled whatField = method.getAnnotation(WhatFiled.class);
            if (whatField.type().isManyToOne()) {
                Long id = (Long) method.invoke(form);
                RelatedFiled relatedFiled = method.getAnnotation(RelatedFiled.class);
                setMethod = BaseEntity.getClass().getMethod(
                        getSetterMethod(method.getName()), relatedFiled.EntityName());
                Class<?> entityClass = ClsFounder.getClass(relatedFiled.EntityName(),"Interface");
                BaseInterface baseInterface = (BaseInterface) SpringContextHelper.getContext().getBean(entityClass);
                BaseEntity entity;
                entity = baseInterface.findById(id);
                setMethod.invoke(BaseEntity, entity);
            } else if (whatField.type().isManyToMany()) {
                Set<Long> ids = (Set<Long>) method.invoke(form);
                RelatedFiled relatedFiled = method.getAnnotation(RelatedFiled.class);
                setMethod = BaseEntity.getClass().getMethod(
                        getSetterMethod(method.getName()), Set.class
                );
                Class<?> entityClass = ClsFounder.getClass(relatedFiled.EntityName(),"Interface");
                BaseInterface baseInterface = (BaseInterface) SpringContextHelper.getContext().getBean(entityClass);

                Set<BaseEntity> relatedEntities = new HashSet<>();
                for (Long id : ids) {

                    BaseEntity entity = baseInterface.findById(id);
                    if (entity != null) {
                        relatedEntities.add(entity);
                    }
                }

                setMethod.invoke(BaseEntity, relatedEntities);
            } else {
                WhatFiled.whatTypes types = whatField.type();
                Class<?> _class = whatClass(types.name());
                setMethod = BaseEntity.getClass().getMethod(
                        getSetterMethod(method.getName()), _class);
                Object value = method.invoke(form);
                setMethod.invoke(BaseEntity, value);
            }

        }
        return true;
    }
    public static Class<?> whatClass(String what) {
        switch (what) {
            case "String":
                return String.class;
            case "Long":
                return Long.class;
            case "Boolean":
                return boolean.class;
            default:
                return null;
        }
    }
    public static String getSetterMethod(String methodName) {
        if (methodName.startsWith("get")) {
            return methodName.replaceFirst("[g]", "s");
        }
        if (methodName.startsWith("is")) {
            return methodName.replace("is", "set");
        }
        return null;
    }
}
