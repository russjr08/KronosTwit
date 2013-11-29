/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences;

import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.filter.Filter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
    
    public static ArrayList<Filter> getFilters(){
        
        File file = new File("filtered.serialized");
        
        if(file.exists()){
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream("filtered.serialized");
                ObjectInputStream is = new ObjectInputStream(fileIn);
                ArrayList<Filter> filters = (ArrayList<Filter>) is.readObject();
                is.close();
                fileIn.close();
                return filters;
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileIn.close();
                } catch (IOException ex) {
                    Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }else{
            return new ArrayList<Filter>();
        }
        
        return new ArrayList<Filter>();
        
    }
    
    public static void serializeFilters(ArrayList<Filter> filters){
        
        try{
            FileOutputStream os = new FileOutputStream("filtered.serialized");
            ObjectOutputStream objectOS = new ObjectOutputStream(os);
            
            objectOS.writeObject(filters);
            
            objectOS.close();
            os.close();
            
                    
        }catch(IOException e){
            
        }
        
        
    }
    
    
    
}
