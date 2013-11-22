/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author russjr08
 */
public class Preferences {
    

    public static String getActiveTheme(){
        String themeName;
        
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("KronosTwit.preferences"));
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            return "Default";
        }
        
        themeName = prop.getProperty("activeTheme");
        
        
        return themeName;
    }
    
    public static void setActiveTheme(String themeName){
        System.out.println("Setting Active Theme to: " + themeName);
        Properties prop = new Properties();
        
        if(new File("KronosTwit.preferences").exists()){
            try {
                prop.load(new FileInputStream("KronosTwit.preferences"));
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        prop.setProperty("activeTheme", themeName);
        
        try {
            prop.store(new FileOutputStream("KronosTwit.preferences"), null);
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
}
