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

import com.bittheory.business.qualifiers.Action;
import com.bittheory.stripes.util.PagePath;
import javax.inject.Inject;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author nick
 */
@Action
@UrlBinding("/home/{$event}")
public class Home extends StripesActionBean {

    @Inject
    @PagePath(value="home/index.jsp")
    private String index;
    
    @DefaultHandler
    @HandlesEvent("index")
    public ForwardResolution index() {
        return new ForwardResolution(index);
    }
}
