package com.freq.arvand.arvand.CoreSelect;

import com.freq.arvand.arvand.Base.BaseForm;
import com.freq.arvand.arvand.annotation.WhatFiled;

public class CoreSelectForm extends BaseForm {

    private Long coreId;
    @WhatFiled
    public Long getCoreId() {
        return coreId;
    }
    public void setCoreId(Long coreId) {
        this.coreId = coreId;
    }


    private String coreName;
    @WhatFiled
    public String getCoreName() {
        return coreName;
    }
    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    private String coreGroup;
    @WhatFiled
    public String getCoreGroup() {
        return coreGroup;
    }
    public void setCoreGroup(String coreGroup) {
        this.coreGroup = coreGroup;
    }

    @Override
    public Long getId() {
        return this.coreId;
    }
}
