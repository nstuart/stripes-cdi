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
package com.bittheory.business.observers;

import com.bittheory.business.qualifiers.UserRegistered;
import com.bittheory.domain.User;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
public class UserRegisteredEmail {
    
    @Inject
    private Logger log;
    
    public void sendEmail(@Observes @UserRegistered User user){
        log.info("User {} successfully registered.");
        log.info("Sent email.");
    }
    
}
