/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.theme;

import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.Preferences;
import com.kronosad.projects.twitter.kronostwit.interfaces.ITheme;

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
        for(int i = 0; i < themes.size(); i++){
            ITheme theme = themes.get(i);
            if(theme.getName().equalsIgnoreCase(themeToAdd.getName())){
                System.out.println(String.format("Theme with the name %s "
                        + "is already present. Removing and re-adding!", themeToAdd.getName()));
                themes.remove(i);
            }
            
        }
        
        System.out.println("Adding Theme: " + themeToAdd.getName());
        themes.add(themeToAdd);
        themes.add(ThemeUtils.fabricate(String.format("[Day] %s", themeToAdd.getName()), themeToAdd.getDayColor(), themeToAdd.getDayFontColor(), themeToAdd.getDayColor(), themeToAdd.getDayFontColor()));
        themes.add(ThemeUtils.fabricate(String.format("[Night] %s", themeToAdd.getName()), themeToAdd.getNightColor(), themeToAdd.getNightFontColor(), themeToAdd.getNightColor(), themeToAdd.getNightFontColor()));

    }

    @Deprecated
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
    
    public void clearThemes(){
        for(int i = 0; i < themes.size(); i++){
            
            ITheme theme = themes.get(i);
            
            if(!theme.getName().equalsIgnoreCase("Default")){
                themes.remove(i);
            }
        }
    }
    
    
    
}
