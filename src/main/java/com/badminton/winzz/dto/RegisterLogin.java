package com.badminton.winzz.dto;

public class RegisterLogin {

    private String username;
    private String password;
    private String confirmPassword;
    private String role;
    private String mail;
    private String lastName;

    private String level;

    private char hand;

    private Long phoneNumber;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPpassword() {
        return confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public String getMail() {
        return mail;
    }


    public String getLastName() {
        return lastName;
    }

    public String getLevel() {
        return level;
    }

    public char getHand() {
        return hand;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
