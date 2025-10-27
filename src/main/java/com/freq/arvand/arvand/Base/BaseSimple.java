package com.freq.arvand.arvand.Base;

import com.freq.arvand.arvand.annotation.SimpleQuery;

import java.util.List;

public abstract class BaseSimple {
    public String getSimpleQuery(String queryName){
        return new StringBuilder().append("select new ").append(this.getClass().getName())
                .append(this.getClass().getAnnotation(SimpleQuery.class).Query()).toString();
    }
    public abstract List<String> getFields();
}
