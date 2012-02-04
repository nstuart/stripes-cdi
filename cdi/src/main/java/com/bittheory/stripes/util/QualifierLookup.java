/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
