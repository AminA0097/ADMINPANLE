package com.freq.arvand.arvand.Admin;

import com.freq.arvand.arvand.Auth.AuthInterface;
import com.freq.arvand.arvand.form.AdminForm;
import com.freq.arvand.arvand.form.LoginForm;
import com.freq.arvand.arvand.userSession.UserSession;
import com.freq.arvand.arvand.userSession.UserSessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/freq/admin/")
public class AdminPanel {
    private AdminInterface adminInterface;
    private AuthInterface authInterface;
    @Autowired
    private UserSessionImpl userSession;


    public AdminPanel(AdminInterface adminInterface, AuthInterface authInterface) {
        this.adminInterface = adminInterface;
        this.authInterface = authInterface;
    }

    @GetMapping("testservice")
    public String testservice() {
        return "Service Is Up!";
    }
    @GetMapping("getdashboard")
    public List<Map<String, String>> getdashboard() throws Exception {
        return adminInterface.fillDashboard(userSession.getSession());
//        return adminInterface.fillDashboard();
    }
    @PostMapping("login")
    public String login(@RequestBody AdminForm loginForm) throws Exception {
        return adminInterface.loginAdmin(loginForm);
    }

}
