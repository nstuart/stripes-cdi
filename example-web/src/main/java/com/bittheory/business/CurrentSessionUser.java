/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.business;

import com.bittheory.business.qualifiers.CurrentUser;
import com.bittheory.domain.User;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 *
 * @author nick
 */
@SessionScoped
public class CurrentSessionUser implements Serializable{
    
    private User user;

    @Produces
    @Named("currentUser")
    @CurrentUser
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
