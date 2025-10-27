package com.freq.arvand.arvand.User;

import com.freq.arvand.arvand.Base.BaseInterface;
import com.freq.arvand.arvand.userSession.UserSession;

import java.util.List;

public interface UserInterface extends BaseInterface {
    List loadActions(Long userId)throws Exception;

    public String getCount(UserSession session) throws Exception;
}
