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
package com.bittheory.stripes.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

/**
 *
 * @author nick
 */
public class PageNameGenerator {

    private static String PAGE_LOCATION = "/WEB-INF/pages/";

    @Produces
    @Named("defaultLayout")
    public String defaultLayout() {
        return PAGE_LOCATION + "layout/default.jsp";
    }

    @Produces
    @PagePath
    private String pageName(InjectionPoint injectionPoint) {
        PagePath p = injectionPoint.getAnnotated().getAnnotation(PagePath.class);
        
        if (p.value() == null || p.value().equals("")) {
            throw new IllegalArgumentException("@Page must have a value set!");
        } else {
            return PAGE_LOCATION + p.value();
        }
    }
}
