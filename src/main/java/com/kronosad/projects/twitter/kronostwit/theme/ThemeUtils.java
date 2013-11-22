package com.kronosad.projects.twitter.kronostwit.theme;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author russjr08
 */
public class ThemeUtils {
    
    /**
     * Returns an {@link ITheme} from the given parameters.
     * @param name Name of the theme
     * @param dayColor Day color of theme
     * @param dayFontColor Day font color of theme
     * @param nightColor Night color of theme
     * @param nightFontColor Night font color of theme
     * @return An instance of {@link ITheme}
     */
    public static ITheme fabricate(final String name, final Color dayColor, final Color dayFontColor, final Color nightColor, final Color nightFontColor){
        
        ITheme theme = new ExtendableTheme(){

            @Override
            public Color getDayColor() {
                return dayColor;
            }

            @Override
            public Color getDayFontColor() {
                return dayFontColor;
            }

            @Override
            public Color getNightColor() {
                return nightColor;
            }

            @Override
            public Color getNightFontColor() {
                return nightFontColor;
            }

            @Override
            public String getName() {
                return name;
            }
            
            
        };
        
        return theme;
    }
    
    
    /**
     * Serializes an instance of {@link ExtendableTheme} under themes\themeName.theme
     * @param theme The theme to save
     * @throws IOException In case there was a problem saving the theme 
     */
    public static void saveThemeToDisk(ExtendableTheme theme) throws IOException{
        File folder = new File("themes");
        
        if(!folder.exists()){
            folder.mkdirs();
        }
        
        
        Properties prop = new Properties();
        
        prop.setProperty("dayColor", String.valueOf(theme.getDayColor().getRGB()));
        prop.setProperty("dayFontColor", String.valueOf(theme.getDayFontColor().getRGB()));
        prop.setProperty("nightColor", String.valueOf(theme.getNightColor().getRGB()));
        prop.setProperty("nightFontColor", String.valueOf(theme.getNightFontColor().getRGB()));
        prop.setProperty("name", theme.getName());
        
        prop.store(new FileOutputStream("themes" + File.separator + theme.getName() + ".theme"), null);
        

        
        
    }
    
    /**
     * Re-serializes an {@link ExtendableTheme} from the disk
     * @param themeName Name of the theme.
     * @return An instance of {@link ExtendableTheme}
     * @throws IOException Thrown if theme is not found on the disk
     */
    public static ExtendableTheme fabricateThemeFromFile(String themeName) throws IOException{
        
        final Properties prop = new Properties();
        
        if(!themeName.endsWith(".theme")){
            prop.load(new FileInputStream("themes" + File.separator + themeName + ".theme"));

        }else{
            prop.load(new FileInputStream("themes" + File.separator + themeName));
    
        }
        
        
        
        ExtendableTheme theme = new ExtendableTheme() {

            @Override
            public Color getDayColor() {
                String[] colors = prop.getProperty("dayColor").split(",");
                
                return new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
            }

            @Override
            public Color getDayFontColor() {
                String[] colors = prop.getProperty("dayFontColor").split(",");
                
                return new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
            }

            @Override
            public Color getNightColor() {
                String[] colors = prop.getProperty("nightColor").split(",");
                
                return new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
            }

            @Override
            public Color getNightFontColor() {
                String[] colors = prop.getProperty("nightFontColor").split(",");
                
                return new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
            }

            @Override
            public String getName() {
                return prop.getProperty("name");
            }
        };
        
        
        
        return theme;       
 
    }
    
    /**
     * Crawls the themes folder and adds all found themes to the {@link ThemeRegistry}
     * @throws IOException Thrown if an error was found reading the theme.
     */
    public static void initThemes() throws IOException{
        
        File folder = new File("themes");
        
        if(!folder.exists()){
            folder.mkdirs();
        }
        
        File themeFolder = new File("themes");
        File[] listOfFiles = themeFolder.listFiles();
        
        if(listOfFiles != null && listOfFiles.length != 0){
            for(File file : listOfFiles){
                if(file.isFile()){
                    if(file.getName().endsWith(".theme")){
                        ConsoleLoader.themeReg.add(fabricateThemeFromFile(file.getName()));
                    }
                }
            }
        }else{
            System.out.println("No external Themes detected.");
        }
    }
    
    
    
    
    
    
    
    
    
}
