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

/**
 * Helps find any Qualifier annotations on a given class.
 * @author Nick Stuart
 */
public class QualifierLookup {

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
}
