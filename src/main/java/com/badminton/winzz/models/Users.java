package com.badminton.winzz.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String confirmPassword;
    private String role;

    private String mail;


    private String lastName;

    private String level;

    private char hand;

    private Long phoneNumber;




    public Users() {
    }

    public Users(Long id, String username,String mail, String password, String confirmPpassword, String role, String fullName, String lastName, String level, char hand, Long phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPpassword;
        this.role = role;
        this.lastName = lastName;
        this.level = level;
        this.hand = hand;
        this.phoneNumber = phoneNumber;
        this.mail=mail;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getConfirmPpassword() {
        return confirmPassword;
    }

    public void setConfirmPpassword(String confirmPpassword) {
        this.confirmPassword = confirmPpassword;
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

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail=mail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role)
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}