
package com.kronosad.projects.twitter.kronostwit.theme;

import com.kronosad.projects.twitter.kronostwit.interfaces.ITheme;

import java.awt.Color;
import java.io.File;
import java.util.Calendar;

/**
 *
 * @author russjr08
 */
public class ThemeDefault implements ITheme {
    
    public ThemeDefault(){
        Calendar calendar = Calendar.getInstance();
       if(calendar.getTime().getHours() >= 20 || calendar.getTime().getHours() <= 7){
            isDaytime = false;
        }else{
            isDaytime = true;
        } 
    }
    
    public boolean isDaytime = true;
    
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

    public Color getDayColor() {
        Color dayColor = new Color(238, 238, 238);
        
        return dayColor;
    }

    public Color getNightColor() {
        return new Color(77,77,77);
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

    public Color getDayFontColor() {
        
        return Color.BLACK;
    
    }

    public Color getNightFontColor() {
        return Color.WHITE;
    }

    public String getName() {
        return "Default";
    }
    
}
