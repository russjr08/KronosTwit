
package com.kronosad.projects.twitter.kronostwit.theme;

import com.kronosad.projects.twitter.kronostwit.interfaces.ITheme;

import java.awt.Color;
import java.io.File;
import java.util.Calendar;

/**
 * This is an abstract class. The only methods that should be implemented by
 * this class is the methods that are used to calculate which colors to use currently.
 * @author russjr08
 */
public abstract class ExtendableTheme implements ITheme {
    
    public ExtendableTheme(){
        
        isDaytime = true;
    }
    
    public boolean isDaytime;
    
    public Color getCurrentColor() {
        
        Calendar calendar = Calendar.getInstance();
        File disableThemes = new File("DISABLE_THEMES");
        File forceNight = new File("FORCE_NIGHT_THEME");
        if(disableThemes.exists()){
            return getDayColor();
        }
        
        if(forceNight.exists()){
            return getNightColor();
        }
        
        if(calendar.getTime().getHours() >= 20 || calendar.getTime().getHours() <= 7){
            isDaytime = false;
            return getNightColor();
        }else{
            isDaytime = true;
            return getDayColor();
        }
        
    }

    public Color getCurrentFontColor() {

        Calendar calendar = Calendar.getInstance();
        File disableThemes = new File("DISABLE_THEMES");
        File forceNight = new File("FORCE_NIGHT_THEME");
        
        if(disableThemes.exists()){
            return getDayFontColor();
        }
        
        if(forceNight.exists()){
            return getNightFontColor();
        }
        
        if(calendar.getTime().getHours() >= 20 || calendar.getTime().getHours() <= 7){
            isDaytime = false;
            return getNightFontColor();
        }else{
            isDaytime = true;
            return getDayFontColor();
        }
        
    
    }
    
    // To be implemented by subclass.

    public abstract Color getDayColor();

    public abstract Color getDayFontColor();

    public abstract Color getNightColor();

    public abstract Color getNightFontColor();

    public abstract String getName();
    
}
