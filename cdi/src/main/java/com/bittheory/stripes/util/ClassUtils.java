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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Qualifier;
import net.sourceforge.stripes.action.ActionBean;

/**
 * Helps find any Qualifier annotations on a given class.
 * @author Nick Stuart
 */
public class ClassUtils {

    public static Annotation[] getQualifiers(Class clazz) {
        List<Annotation> qualifierAnnnotations = new ArrayList<Annotation>();
        Annotation[] clazzAnnotations = clazz.getAnnotations();
        for (Annotation annotation : clazzAnnotations) {
            if (annotation.annotationType().getAnnotation(Qualifier.class) != null) {
                qualifierAnnnotations.add(annotation);
            }
        }
        return qualifierAnnnotations.toArray(new Annotation[]{});
    }
    
    /**
     * Currently a naive approach to getting the real class as it relies on the
     * WeldClientProx to be a part of the classname. Obviously not portable or
     * robust.
     *
     * @param bean
     * @return
     */
    public static Class<? extends ActionBean> getRealBeanClass(Class<? extends ActionBean> bean) {
        Class<? extends ActionBean> beanClass;
        if (bean.getName().contains("$_WeldClientProx")) {
            beanClass = (Class<? extends ActionBean>) bean.getSuperclass();
        } else {
            beanClass = bean;
        }
        return beanClass;
    }
}
