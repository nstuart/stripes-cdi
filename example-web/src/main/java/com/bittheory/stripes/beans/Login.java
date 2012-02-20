/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.beans;

import com.bittheory.stripes.util.LoginSuccess;
import com.bittheory.business.CurrentSessionUser;
import com.bittheory.business.UserService;
import com.bittheory.business.qualifiers.Action;
import com.bittheory.domain.User;
import com.bittheory.stripes.util.PagePath;
import com.bittheory.stripes.model.LoginRequest;
import com.bittheory.stripes.util.LoginFailure;
import com.google.common.base.Strings;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@Action
@UrlBinding("/login")
public class Login extends StripesActionBean {

    @ValidateNestedProperties({
        @Validate(field = "userName", required = true, on = "login"),
        @Validate(field = "password", required = true, on = "login")
    })
    @Inject
    private LoginRequest loginRequest;
    @Inject
    private Logger log;
    @Inject
    private UserService userService;
    @Inject
    @PagePath(value = "login/index.jsp")
    private String index;
    
    @Inject
    @LoginSuccess
    private Event<User> loginSuccess;
    @Inject
    @LoginFailure
    private Event<LoginRequest> loginFailure;

    @DefaultHandler
    public Resolution index() {
        return new ForwardResolution(index);
    }

    @HandlesEvent("login")
    public Resolution login() {
        log.debug("Attempting to login user {}", loginRequest.getUserName());
        Resolution res;
        if (userService.validPassword(loginRequest.getUserName(), loginRequest.getPassword())) {
            currentSessionUser.setUser(
                    userService.loadByUserName(loginRequest.getUserName()));
            String rdFrom = context.getRedirectedFrom();
            if (Strings.isNullOrEmpty(rdFrom)) {
                res = new RedirectResolution(Home.class);
            } else {
                res = new RedirectResolution(rdFrom, false);
            }
            loginSuccess.fire(currentSessionUser.getUser());
        } else {
            loginFailure.fire(loginRequest);
            context.getValidationErrors().addGlobalError(new SimpleError("Invalid user information."));
            loginRequest.clear();
            res = new ForwardResolution(this.getClass());
        }
        return res;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }
}
