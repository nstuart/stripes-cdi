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

import com.bittheory.business.CurrentSessionUser;
import com.bittheory.stripes.ext.CdiActionBeanContext;
import com.bittheory.stripes.model.LoginRequest;
import javax.inject.Inject;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 *
 * @author nick
 */
public class StripesActionBean implements ActionBean{

    protected CdiActionBeanContext context;
    @Inject
    protected CurrentSessionUser currentSessionUser;

    public CurrentSessionUser getCurrentSessionUser() {
        return currentSessionUser;
    }

    public void setCurrentSessionUser(CurrentSessionUser currentSessionUser) {
        this.currentSessionUser = currentSessionUser;
    }
    
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = (CdiActionBeanContext) context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }
    
}
