/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.beans;

import com.bittheory.business.UserService;
import com.bittheory.business.qualifiers.DomainObject;
import com.bittheory.business.qualifiers.Action;
import com.bittheory.domain.User;
import com.bittheory.stripes.util.TransactionRequired;
import javax.inject.Inject;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;

/**
 *
 * @author nick
 */
@Action
@UrlBinding("/admin/users/{$event}/{id}")
public class UserManagement extends StripesActionBean {

    @Inject
    private UserService userService;
    @Inject
    @DomainObject()
    private User user;

    @DefaultHandler
    public ForwardResolution index() {

        return new ForwardResolution("");
    }

    @HandlesEvent("new")
    public ForwardResolution newUser() {
        return new ForwardResolution("/create_user.jsp");
    }

    @HandlesEvent("edit")
    public ForwardResolution edit() {
        return new ForwardResolution("/edit_user.jsp");
    }

    @TransactionRequired
    @HandlesEvent("update")
    public RedirectResolution update() {
        setPassword();
        userService.update();
        return new RedirectResolution(this.getClass(), "edit").addParameter("id", user.getId());
    }

    @HandlesEvent("create")
    public RedirectResolution create() {
        setPassword();
        userService.createUser(user);

        RedirectResolution rd = new RedirectResolution(this.getClass(), "edit");
        rd.addParameter("id", user.getId());
        return rd;
    }

    public void setPassword() {
        if (user.getPassword() != null) {
            user.setHashedPassword(user.getPassword());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
