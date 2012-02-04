/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.util;

import com.bittheory.stripes.util.QualifierLookup;
import java.lang.annotation.Annotation;
import javax.inject.Qualifier;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nick
 */
public class TestQualifierLookup {

    @Test
    public void testQualiferLookup() {
        Annotation[] annotations = QualifierLookup.getQualifiers(QualifiedSample.class);
        assertEquals(1, annotations.length);
        assertEquals(TestQualifier.class, annotations[0].annotationType());
    }
}
