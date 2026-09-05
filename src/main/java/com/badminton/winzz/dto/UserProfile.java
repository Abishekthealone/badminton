package com.badminton.winzz.dto;

import com.badminton.winzz.models.Users;

/**
 * What /login/me returns.
 *
 * WHY a DTO instead of returning the Users entity directly:
 *
 *  1. Users has getPassword() - returning the entity would ship the BCrypt hash
 *     to the browser in plain JSON. That alone is reason enough.
 *  2. Users implements UserDetails, so Jackson would also serialise
 *     authorities, accountNonExpired, credentialsNonExpired, enabled...
 *     none of which the UI wants.
 *
 * Rule of thumb: never return a JPA entity that implements UserDetails.
 */
public class UserProfile {

    private Long id;
    private String username;
    private String lastName;
    private String mail;
    private String level;
    private String role;
    private String hand;
    private Long phoneNumber;

    public UserProfile() {
    }

    public static UserProfile from(Users user) {
        UserProfile profile = new UserProfile();
        profile.id = user.getId();
        profile.username = user.getUsername();
        profile.lastName = user.getLastName();
        profile.mail = user.getMail();
        profile.level = user.getLevel();
        profile.role = user.getRole();
        profile.hand = user.getHand() == '\0' ? null : String.valueOf(user.getHand());
        profile.phoneNumber = user.getPhoneNumber();
        return profile;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getLastName() { return lastName; }
    public String getMail() { return mail; }
    public String getLevel() { return level; }
    public String getRole() { return role; }
    public String getHand() { return hand; }
    public Long getPhoneNumber() { return phoneNumber; }
}
