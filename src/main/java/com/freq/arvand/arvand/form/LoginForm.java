package com.freq.arvand.arvand.form;

public class LoginForm {
//    @NotBlank(message = "fill userName")
//    @Size(min = 8,max = 10, message = "max is 10 and min is8")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Just Number and Alphabet")
    private String userName;

//    @NotBlank(message = "fill password")
//    @Size(min = 8,max = 10, message = "max is 10 and min is8")
//    @Pattern(regexp = "^[a-zA-Z0-9@$!%*?&]+$",message = "Just Number and Alphabet and Symbolic")
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
