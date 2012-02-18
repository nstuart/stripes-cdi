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
package com.bittheory.stripes.beans;

import com.bittheory.stripes.model.LoginRequest;
import com.bittheory.business.CurrentSessionUser;
import com.bittheory.business.qualifiers.Action;
import com.bittheory.domain.User;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@Action
@UrlBinding("/home/{$event}")
public class Home extends StripesActionBean {

    @Inject
    private CurrentSessionUser loginInfo;
    @ValidateNestedProperties({
        @Validate(field = "userName", required = true, on = "login")
    })
    @Inject
    private LoginRequest loginRequest;
    @Inject
    private Logger log;
    @PersistenceContext
    private EntityManager em;

    @DefaultHandler
    @HandlesEvent("index")
    public ForwardResolution index() {
        return new ForwardResolution("/index.jsp");
    }

    @HandlesEvent("login")
    public RedirectResolution login() {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName", User.class).
                    setParameter("firstName", loginRequest.getUserName()).getSingleResult();
            loginInfo.setUser(user);
            return new RedirectResolution(this.getClass());
        } catch (NoResultException ex) {
            return new RedirectResolution(this.getClass());
        }
    }

    @HandlesEvent("logout")
    public RedirectResolution logout() {
        User user = new User();
        loginInfo.setUser(null);
        return new RedirectResolution(this.getClass());
    }
    
    @Before(stages={LifecycleStage.BindingAndValidation})
    public void before(){
        log.info("CDI Before filter works!");
    }

    public CurrentSessionUser getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(CurrentSessionUser loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }
}
