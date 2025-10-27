package com.freq.arvand.arvand.userSession;


import com.freq.arvand.arvand.User.UsersSimple;

public final class UserSession {

    private UsersSimple usersSimple;
    private  String sessionId;
    private  String userId;
    private  String username;
    private  String ipAddress;
    private UserActions userAction;
    private static UserSession sys = null;
    public UserSession(String sessionId, UsersSimple usersSimple) throws Exception{
        this.usersSimple = usersSimple;
        this.sessionId = sessionId;
        this.userAction = UserActions.init(usersSimple);

    }
    public static UserSession getSysSession()throws Exception{
        if(sys != null)return sys;
        UsersSimple usersSimple = new UsersSimple();
        usersSimple.setUserId(2l);
        usersSimple.setUserName("BySys");
        usersSimple.setUserAccess("Sys");
        usersSimple.setSysAdmin(false);
        usersSimple.setStatus(1l);
        sys = new UserSession("2", usersSimple);
        sys.usersSimple = usersSimple;
        return sys;
    }

    public UsersSimple getUsersSimple() {
        return usersSimple;
    }

    public void setUsersSimple(UsersSimple usersSimple) {
        this.usersSimple = usersSimple;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UserActions getUserAction() {
        return userAction;
    }

    public void setUserAction(UserActions userAction) {
        this.userAction = userAction;
    }

    public static UserSession getSys() {
        return sys;
    }

    public static void setSys(UserSession sys) {
        UserSession.sys = sys;
    }

}
