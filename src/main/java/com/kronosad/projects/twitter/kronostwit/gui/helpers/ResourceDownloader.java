
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;



/**
 *
 * @author russjr08
 */
public class ResourceDownloader {
    
    private final static String API_URL = "http://api.kronosad.com/KronosTwit/resources/";
    
    public static boolean downloadResource(String fileName){
        try {
            URL url = new URL(API_URL + fileName);
            File file = new File(fileName);
            
            FileUtils.copyURLToFile(url, file);
            
            
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error downloading resource: " + fileName, "Error Downloading Resources", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    }
    
    
}
