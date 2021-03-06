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

import com.bittheory.business.UserService;
import com.bittheory.business.qualifiers.Action;
import com.bittheory.domain.User;
import com.bittheory.stripes.util.PagePath;
import com.bittheory.stripes.util.TransactionRequired;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.FlashScope;
import net.sourceforge.stripes.validation.*;

/**
 *
 * @author nick
 */
@Action
@UrlBinding("/admin/users/{$event}/{user}")
public class UserManagement extends StripesActionBean {

    @Inject
    private UserService userService;
    @PersistenceContext
    private EntityManager em;
    @Inject
    @PagePath("users/view.jsp")
    private String view;
    @Inject
    @PagePath("users/edit.jsp")
    private String edit;
    @Inject
    @PagePath("users/create.jsp")
    private String create;
    @Inject
    @PagePath("users/index.jsp")
    private String index;
    
    @ValidateNestedProperties({
        @Validate(field="id", ignore=true),
        @Validate(field="userName", required=true, minlength=4, maxlength=50, on={"update", "create"}),
        @Validate(field="firstName", required=true, on={"update", "create"}),
        @Validate(field="lastName", required=true, on={"update", "create"}),
        @Validate(field="email", required=true, on={"update", "create"}),
        @Validate(field="password", required=true, on={"create"}),
        @Validate(field="passwordConfirmation", required=true, on={"create"})
    })
    private User user;
    
    private List<User> users;

    @DefaultHandler
    public ForwardResolution index() {
        users = em.
                createQuery("SELECT u FROM User u ORDER BY u.userName", User.class).
                getResultList();
        return new ForwardResolution(index);
    }

    @HandlesEvent("view")
    public ForwardResolution view() {
        return new ForwardResolution(view);
    }

    @HandlesEvent("new")
    public ForwardResolution newUser() {
        return new ForwardResolution(create);
    }

    @HandlesEvent("edit")
    public ForwardResolution edit() {
        return new ForwardResolution(edit);
    }
    
    @HandlesEvent("cancel")
    public RedirectResolution cancel(){
        return new RedirectResolution(this.getClass(), "index");
    }

    @TransactionRequired
    @HandlesEvent("update")
    public RedirectResolution update() {
        userService.update(user);
        FlashScope.getCurrent(context.getRequest(), true).put("message", "User information has been updated!");
        return new RedirectResolution(this.getClass());
    }

    @HandlesEvent("create")
    public RedirectResolution create() {
        userService.createUser(user);
        RedirectResolution rd = new RedirectResolution(this.getClass());
        FlashScope.getCurrent(context.getRequest(), true).put("message", "User created successfully!");
        return rd;
    }
    
    @ValidationMethod(on={"create", "update"})
    public void checkPassword(ValidationErrors errors){
        if(user.getPassword() != null){
            if(!user.getPassword().equals(user.getPasswordConfirmation())){
                errors.add("user.password", new LocalizableError("validation.password.mismatch"));
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }
}
