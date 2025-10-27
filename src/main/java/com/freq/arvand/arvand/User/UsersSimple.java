package com.freq.arvand.arvand.User;

import com.freq.arvand.arvand.Base.BaseSimple;
import com.freq.arvand.arvand.annotation.SimpleQuery;
import org.springframework.stereotype.Component;


import java.util.List;

@SimpleQuery(Query = " (e) from UsersEntity e where 1 = 1 ")
@Component
public class UsersSimple extends BaseSimple {
    public UsersSimple(){}
    private Long userId;
    private String userName;
    private String passWord;
    private boolean isSysAdmin;
    private String userAccess;
    private Long status;

    public UsersSimple(UsersEntity users) {
        this.userId = users.getUserId() == null ? null : users.getUserId();
        this.userName = users.getUserName() == null ? null : users.getUserName();
        this.passWord = users.getPassword() == null ? null : users.getPassword();
        this.isSysAdmin = users.isSysAdmin();
        this.userAccess = users.getUserAccess() == null ? null : users.getUserAccess();
        this.status = users.getStatus().getId() == null ? null : users.getStatus().getId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isSysAdmin() {
        return isSysAdmin;
    }

    public void setSysAdmin(boolean sysAdmin) {
        isSysAdmin = sysAdmin;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public List<String> getFields() {
        return List.of("e.userName");
    }
}
