
package com.kronosad.projects.twitter.kronostwit.theme;

import java.awt.Color;
import java.io.File;
import java.util.Calendar;

/**
 *
 * @author russjr08
 */
public class ThemeDefault implements ITheme{
    
    public ThemeDefault(){
        
    }
    
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
            return getNightColor();
        }else{
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
    
}
