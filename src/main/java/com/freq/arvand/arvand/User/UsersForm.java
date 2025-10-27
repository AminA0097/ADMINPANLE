package com.freq.arvand.arvand.User;

import com.freq.arvand.arvand.Base.BaseForm;
import com.freq.arvand.arvand.CoreSelect.CoreSelectEntity;
import com.freq.arvand.arvand.annotation.RelatedFiled;
import com.freq.arvand.arvand.annotation.WhatFiled;

public class UsersForm extends BaseForm {
    private Long userId;
    @WhatFiled(type = WhatFiled.whatTypes.Long)
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String userName;
    @WhatFiled
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    private String password;
    @WhatFiled
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    private boolean isSysAdmin;
    @WhatFiled(type = WhatFiled.whatTypes.Boolean)
    public boolean isSysAdmin() {return isSysAdmin;}
    public void setSysAdmin(boolean isSysAdmin) {this.isSysAdmin = isSysAdmin;}

    private String userAccess;
    @WhatFiled
    public String getUserAccess() {return userAccess;}
    public void setUserAccess(String userAccess) {this.userAccess = userAccess;}

    private Long status;
    @WhatFiled(type = WhatFiled.whatTypes.ManyToOne)
    @RelatedFiled(EntityName = CoreSelectEntity.class,FieldName = "coreId")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public Long getId() {
        return this.userId;
    }
}
