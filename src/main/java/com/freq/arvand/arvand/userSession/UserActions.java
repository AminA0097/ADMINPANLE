package com.freq.arvand.arvand.userSession;


import com.freq.arvand.arvand.User.UserInterface;
import com.freq.arvand.arvand.User.UsersSimple;
import com.freq.arvand.arvand.utils.SpringContextHelper;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class UserActions {
    private UserInterface userInterface;
    private SortedMap<String, String> act;
    private String ip;
    private String authorities;

    public UserActions() {}

    public Map<String, String> getActions(long id) throws Exception {
        return null;
    }

    public static UserActions init(UsersSimple usersSimple) throws Exception {
        UserActions userActions = new UserActions();
        genAct(userActions, usersSimple.getUserId());

        userActions.ip = "127.0.0.1";
        userActions.authorities = usersSimple.getUserAccess();

        return userActions;
    }

    private static void genAct(UserActions userActions, Long userId) throws Exception {
        UserInterface userInterface = SpringContextHelper.getContext().getBean(UserInterface.class);
        List acts = userInterface.loadActions(userId);
    }
    public boolean hasAction(String entity, String action) {
        if(entity.equals("ProductEntity") && action.equals("0")) {return true;}

        return false;
    }
    // --- Getters & Setters ---
    public SortedMap<String, String> getAct() {
        return act;
    }

    public void setAct(SortedMap<String, String> act) {
        this.act = act;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
