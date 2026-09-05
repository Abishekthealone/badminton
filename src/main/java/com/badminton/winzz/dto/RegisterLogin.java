package com.badminton.winzz.dto;

/**
 * Request body for /login/register and /auth/token.
 *
 * IMPORTANT: this class needs setters (or a matching constructor). Jackson binds
 * an incoming JSON body by calling them - with getters alone the binding is
 * fragile and fields can silently arrive as null. Adding the setters makes
 * deserialization explicit and predictable.
 */
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPpassword() {
        return confirmPassword;
    }

    public void setConfirmPpassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public char getHand() {
        return hand;
    }

    public void setHand(char hand) {
        this.hand = hand;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
