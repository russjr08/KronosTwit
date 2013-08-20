/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Russell
 */
public class Resource {
    
    private String resourceName;
    private String md5;
    private URL url;
    private boolean ignoreUpdate;
    
    public Resource(String name, String md5, String url){
        this.resourceName = name;
        this.md5 = md5;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        ignoreUpdate = false;
    }
    
    public Resource(String name, String md5, String url, boolean ignoreUpdate){
        this.resourceName = name;
        this.md5 = md5;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ignoreUpdate = ignoreUpdate;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getMd5() {
        return md5;
    }

    public URL getUrl() {
        return url;
    }

    public boolean isIgnoreUpdate() {
        return ignoreUpdate;
    }
    
    
    
}
