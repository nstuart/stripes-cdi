/*
 * Copyright 2012 Nick Stuart
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bittheory.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nick Stuart
 */
@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = User.QRY_LOGIN_INFO, query = "SELECT u.hashedPassword, u.salt "
    + "FROM User u "
    + "WHERE u.userName = :userName"),
    @NamedQuery(name = User.QRY_BY_USER_NAME, query = "SELECT u "
    + "FROM User u "
    + "WHERE u.userName = :userName")
})
public class User extends Base implements Serializable {

    public static final String QRY_LOGIN_INFO = "User.loginInfo";
    public static final String QRY_BY_USER_NAME = "User.byUserName";
    private String firstName;
    private String lastName;
    @NotNull
    @Size(max = 50, min = 4)
    @Column(length = 50, unique=true)
    private String userName;
    @NotNull
    @Column(length = 255, name = "password")
    private String hashedPassword;
    @NotNull
    private String salt;
    @Transient
    private String password;
    @Transient
    private String passwordConfirmation;
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
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
