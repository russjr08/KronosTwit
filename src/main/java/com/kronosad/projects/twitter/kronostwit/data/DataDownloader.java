
package com.kronosad.projects.twitter.kronostwit.data;

import com.kronosad.projects.twitter.kronostwit.interfaces.IData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Russell
 */
public class DataDownloader {
    
    private static ArrayList<IData> downloaders = new ArrayList<IData>();
    
    public static boolean downloadData(){
        System.out.println("Preparing to download data...");
        initDownloaders();
        for(IData downloader : downloaders){
            try {
                downloader.download();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(DataDownloader.class.getName()).log(Level.SEVERE, null, ex);
                return true;
            } catch (IOException ex) {
                Logger.getLogger(DataDownloader.class.getName()).log(Level.SEVERE, null, ex);
                return true;
            } catch (SAXException ex) {
                Logger.getLogger(DataDownloader.class.getName()).log(Level.SEVERE, null, ex);
                return true;
            }
        }
        return false;
    }
    
    private static void initDownloaders(){
        
        downloaders.add(new DataUsers());
        downloaders.add(new DataResources());
        
    }
    
    
    
}
