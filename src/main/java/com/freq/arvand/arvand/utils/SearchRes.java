package com.freq.arvand.arvand.utils;

import com.freq.arvand.arvand.Base.BaseSimple;

import java.util.Collection;

public class SearchRes {
    public SearchRes(){}
    Collection<BaseSimple> res;
    int count = 0;
    public SearchRes(Collection<BaseSimple> res, int count) {
        setRes(res);
        setCount(count);
    }

    public Collection<BaseSimple> getRes() {
        return res;
    }

    public void setRes(Collection<BaseSimple> res) {
        this.res = res;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
