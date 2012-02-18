/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext.cdi;

import com.bittheory.stripes.util.ClassUtils;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.controller.BeforeAfterMethodInterceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

/**
 * Wraps the base BeforeAfterMethodInterceptor to be able to check if we are 
 * dealing with a proxied class. Proxy classes cause issues when trying to 
 * determine before/after methods because we loose our annotation information
 * on the proxy.
 * 
 * @author Nick Stuart
 */
@Intercepts({LifecycleStage.RequestInit,
             LifecycleStage.ActionBeanResolution,
             LifecycleStage.HandlerResolution,
             LifecycleStage.BindingAndValidation,
             LifecycleStage.CustomValidation,
             LifecycleStage.EventHandling,
             LifecycleStage.ResolutionExecution,
             LifecycleStage.RequestComplete})
public class CdiBeforeAfterMethodInterceptor extends BeforeAfterMethodInterceptor{

    @Override
    protected FilterMethods getFilterMethods(Class<? extends ActionBean> beanClass) {
        return super.getFilterMethods(ClassUtils.getRealBeanClass(beanClass));
    }
    
}
