package com.freq.arvand.arvand.controller;

import com.freq.arvand.arvand.userSession.UserSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    public AdminController() {
    }

    @GetMapping("/test/")
    public UserSession test() throws Exception {
        return UserSession.getSysSession();
    }
    @GetMapping("/t")
    public String test2() throws Exception {
        return "test2";
    }
}
