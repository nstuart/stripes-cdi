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
    
    public String getUserName(){
        if(user != null){
            return user.getUserName();
        }else{
            return null;
        }
    }
    
}
