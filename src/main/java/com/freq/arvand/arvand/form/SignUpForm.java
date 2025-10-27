package com.freq.arvand.arvand.form;

public class SignUpForm {
//    @NotBlank(message = "fill userName")
//    @Size(min = 8,max = 10, message = "max is 10 and min is8")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Just no and char")
    private String userName;

//    @NotBlank(message = "fill password")
//    @Size(min = 8,max = 10, message = "max is 10 and min is8")
//    @Pattern(regexp = "^[a-zA-Z0-9@$!%*?&]+$",message = "Just no and char and symb")
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
