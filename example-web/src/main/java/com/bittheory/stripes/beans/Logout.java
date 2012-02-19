/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.beans;

import com.bittheory.business.CurrentSessionUser;
import com.bittheory.business.qualifiers.Action;
import javax.inject.Inject;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author nick
 */
@UrlBinding("/logout")
@Action
public class Logout extends StripesActionBean {
    
    @Inject
    private CurrentSessionUser currentUser;
    
    @DefaultHandler
    @HandlesEvent("logout")
    public Resolution logout(){
        if(currentUser.getUser() != null){
            currentUser.setUser(null);
            context.reset();
        }
        return new RedirectResolution(Home.class);
    }
    
}
