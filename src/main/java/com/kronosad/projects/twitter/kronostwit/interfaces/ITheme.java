
package com.kronosad.projects.twitter.kronostwit.interfaces;

import java.awt.Color;

/**
 *
 * @author russjr08
 */
public interface ITheme {
    
    /**
     * Gets the current background color of this theme.
     */
    public Color getCurrentColor();
    
    /**
     * Gets the current font color of this theme.
     */
    public Color getCurrentFontColor();
    
    /**
     * Gets the background day color of this theme.
     */
    public Color getDayColor();
    
    /**
     * Gets the background night color of this theme.
     */
    
    public Color getDayFontColor();
    
    /**
     * Gets the background night color of this theme.
     */
    public Color getNightColor();
    
    /**
     * Gets the background font color of this theme.
     */
    
    public Color getNightFontColor();
    
    /**
     * Gets the name of the theme. 
     */
    public String getName();
    
}
