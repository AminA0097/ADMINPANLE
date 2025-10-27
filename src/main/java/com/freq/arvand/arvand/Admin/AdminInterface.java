package com.freq.arvand.arvand.Admin;

import com.freq.arvand.arvand.Base.BaseInterface;
import com.freq.arvand.arvand.form.AdminForm;
import com.freq.arvand.arvand.form.LoginForm;
import com.freq.arvand.arvand.userSession.UserSession;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface AdminInterface extends BaseInterface {

//    public List<Map<String, String>> fillDashboard()throws Exception;
    public List<Map<String, String>> fillDashboard(UserSession sysSession)throws Exception;

    public String loginAdmin(AdminForm loginForm)throws Exception;
}
