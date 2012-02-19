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
package com.bittheory.stripes.ext;

import com.google.common.base.Strings;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@RequestScoped
public class CdiActionBeanContext extends ActionBeanContext {

    private static final String SESSION_SEC_REDIRECT = "SEC_REDIRECT";
    @Inject
    private Logger log;

    public void reset() {
        getRequest().getSession().invalidate();
    }

    public String getRedirectedFrom() {
        return (String) getRequest().getSession().getAttribute(SESSION_SEC_REDIRECT);
    }

    public void setRedirectedFrom() {
        if (getRequest().getMethod().equals("GET")) {
            StringBuilder path = new StringBuilder(getRequest().getRequestURI());
            String query = getRequest().getQueryString();
            if (!Strings.isNullOrEmpty(query)) {
                path.append("?").append(query);
            }
            log.debug("Saving redirect information of [{}]", path.toString());
            getRequest().getSession().setAttribute(SESSION_SEC_REDIRECT, path.toString());
        } else {
            log.info("Request was not a GET request, can not save redirect information.");
        }
    }
}
