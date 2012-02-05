/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "user")
public class User extends Base implements Serializable {

    private String firstName;
    private String lastName;
    @NotNull
    @Size(max = 50, min = 4)
    @Column(length = 50)
    private String userName;
    @NotNull
    @Column(length = 255, name="password")
    private String hashedPassword;
    @Transient
    private String password;
    @NotNull
    @Column(length = 100)
    private String email;
    @ManyToMany()
    @JoinTable(name = "users_tracker_roles",
    joinColumns = {
        @JoinColumn(name = "user_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "tracker_role_id")})
    private Set<Role> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
