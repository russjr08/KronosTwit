/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.theme;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.gui.windows.Window;
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

    private ThemeRegistry instance;
    
    public ThemeRegistry() throws IllegalAccessException {

        if(instance != null){
            throw new IllegalAccessException("ThemeRegistry should only be initialized once!");
        }

        activeTheme = new ThemeDefault();
        add(activeTheme);
        instance = this;
        
    }

    /**
     * Gets an instance of the ThemeRegistry.
     * @return An instance of {@link com.kronosad.projects.twitter.kronostwit.theme.ThemeRegistry}
     */
    public ThemeRegistry getInstance(){
        return instance;
    }

    /**
     * Adds a theme to the Theme Database. If the Theme already exists (Theme name is already added), the existing one
     * will be removed and then replaced with the new one.
     * @param themeToAdd {@link com.kronosad.projects.twitter.kronostwit.interfaces.ITheme} that will be added.
     */
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

    /**
     * Sets the active theme.
     * WARNING: This will recolor all open {@link com.kronosad.projects.twitter.kronostwit.gui.windows.Window Windows}!
     * @param theme An {@link com.kronosad.projects.twitter.kronostwit.interfaces.ITheme} to be used.
     */
    public void setActive(ITheme theme){
        activeTheme = theme;
        for(Window window : ConsoleLoader.windows){
            window.recolor();
        }
    }

    /**
     * Gets / Sets the new active theme that is set in the KronosTwit preference file.
     */
    public void getNewActive(){
        String active = Preferences.getActiveTheme();
        
        for(ITheme theme : themes){
            if(theme.getName().equalsIgnoreCase(active)){
                activeTheme = theme;
                break;
            }
        }
    }

    /**
     * Removes a theme from the Theme Database
     * @param themeToRemove {@link com.kronosad.projects.twitter.kronostwit.interfaces.ITheme} to be removed.
     */
    public void remove(ITheme themeToRemove){
        for(int i = 0; i < themes.size(); i++){
            ITheme theme = themes.get(i);
            
            if(theme.getName().equalsIgnoreCase(themeToRemove.getName())){
                themes.remove(i);
                System.out.println("Removing Theme: " + themeToRemove.getName());
            }
        }
    }

    /**
     * Gets an {@link java.util.ArrayList} of {@link com.kronosad.projects.twitter.kronostwit.interfaces.ITheme} which
     * are currently in the database.
     * @return An ArrayList of themes in the Theme Database
     */
    public ArrayList<ITheme> getThemes(){
        return themes;
    }

    /**
     * Gets the Active Theme.
     * @return The {@link com.kronosad.projects.twitter.kronostwit.interfaces.ITheme} that is in-use.
     */
    public ITheme getActiveTheme(){
        return activeTheme;
    }

    /**
     * Clears all themes from the database. Should only be used INTERNALLY.
     */
    public void clearThemes(){
        for(int i = 0; i < themes.size(); i++){
            
            ITheme theme = themes.get(i);
            
            if(!theme.getName().equalsIgnoreCase("Default")){
                themes.remove(i);
            }
        }
    }
    
    
    
}
