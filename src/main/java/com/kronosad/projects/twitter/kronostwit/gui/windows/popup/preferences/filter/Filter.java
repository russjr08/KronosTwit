/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.filter;

import java.io.Serializable;

/**
 *
 * @author russjr08
 */
public class Filter implements Serializable{
    
    private boolean isUsername = false;
    private String filter;
    
    public Filter(String filter, boolean isUser){
        isUsername = isUser;
        this.filter = filter;
    }

    public boolean isUsername() {
        return isUsername;
    }

    public String getFilter() {
        return filter;
    }
    
    
    
}
