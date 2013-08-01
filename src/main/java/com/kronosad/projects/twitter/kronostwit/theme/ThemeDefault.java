
package com.kronosad.projects.twitter.kronostwit.theme;

import java.awt.Color;
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
        if(calendar.getTime().getHours() >= 20){
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
