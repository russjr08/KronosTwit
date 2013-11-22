/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.theme;

import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.Preferences;
import java.util.ArrayList;

/**
 *
 * @author russjr08
 */
public class ThemeRegistry {
    
    
    private ArrayList<ITheme> themes = new ArrayList<ITheme>();
    private ITheme activeTheme;
    
    public ThemeRegistry(){
        
        activeTheme = new ThemeDefault();
        add(activeTheme);
        
    }
    
    public void add(ITheme themeToAdd){
        for(ITheme theme : themes){
            if(theme.getName().equalsIgnoreCase(themeToAdd.getName())){
                throw new IllegalArgumentException("Theme Already Exists!");
            }
            
        }
        
        System.out.println("Adding Theme: " + themeToAdd.getName());
        themes.add(themeToAdd);
        
    }
    
    public void setActive(ITheme theme){
        activeTheme = theme;
    }
    
    public void getNewActive(){
        String active = Preferences.getActiveTheme();
        
        for(ITheme theme : themes){
            if(theme.getName().equalsIgnoreCase(active)){
                activeTheme = theme;
                break;
            }
        }
    }
    
    public void remove(ITheme themeToRemove){
        for(int i = 0; i < themes.size(); i++){
            ITheme theme = themes.get(i);
            
            if(theme.getName().equalsIgnoreCase(themeToRemove.getName())){
                themes.remove(i);
                System.out.println("Removing Theme: " + themeToRemove.getName());
            }
        }
    }
    
    public ArrayList<ITheme> getThemes(){
        return themes;
    }
    
    public ITheme getActiveTheme(){
        return activeTheme;
    }
    
    
    
}
