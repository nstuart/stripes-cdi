/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext;

import com.bittheory.business.CurrentSessionUser;
import com.bittheory.stripes.beans.Login;
import javax.inject.Inject;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@Intercepts({
    LifecycleStage.HandlerResolution,
    LifecycleStage.BindingAndValidation
})
public class SecurityInterceptor implements Interceptor {

    @Inject
    private Logger log;
    @Inject
    private CurrentSessionUser user;
    @Inject
    private CdiActionBeanContext beanContext;;

    @Override
    public Resolution intercept(ExecutionContext context) throws Exception {
        if (context.getLifecycleStage() == LifecycleStage.HandlerResolution) {
            log.debug("Checking actionBean security on bean {} for user {}.", context.getActionBean().getClass(), user.getUserName());
            //TODO: Check for annotations on actionbean class.
            if (!(context.getActionBean() instanceof Login) && user.getUser() == null) {
                beanContext.setRedirectedFrom();
                return new RedirectResolution(Login.class);
            }
        } else {
            if (context.getHandler() != null) {
                //TODO: Check for annotations on event handler method
                log.debug("Checking handler security on bean {}.{} for user {}.",
                        new Object[]{
                            context.getActionBean().getClass().getCanonicalName(),
                            context.getHandler().getName(),
                            user.getUserName()});
            }

        }
        return context.proceed();
    }
}
