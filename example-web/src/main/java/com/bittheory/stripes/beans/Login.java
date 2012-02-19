/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.beans;

import com.bittheory.business.CurrentSessionUser;
import com.bittheory.business.qualifiers.Action;
import com.bittheory.domain.User;
import com.bittheory.stripes.util.PagePath;
import com.bittheory.stripes.model.LoginRequest;
import com.google.common.base.Strings;
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
    @PersistenceContext
    private EntityManager em;
    @Inject
    @PagePath(value = "login/index.jsp")
    private String index;

    @DefaultHandler
    public Resolution index() {
        return new ForwardResolution(index);
    }

    @HandlesEvent("login")
    public Resolution login() {
        try {
            log.debug("Attempting to login user {}", loginRequest.getUserName());
            User user = em.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName", User.class).
                    setParameter("firstName", loginRequest.getUserName()).getSingleResult();
            currentSessionUser.setUser(user);
            String rdFrom = context.getRedirectedFrom();
            if (Strings.isNullOrEmpty(rdFrom)) {
                return new RedirectResolution(Home.class);
            } else {
                return new RedirectResolution(rdFrom, false);
            }
        } catch (NoResultException ex) {
            context.getValidationErrors().addGlobalError(new SimpleError("Invalid user information."));
            return new RedirectResolution(this.getClass());
        }
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }
}
