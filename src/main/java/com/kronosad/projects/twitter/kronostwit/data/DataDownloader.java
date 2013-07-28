
package com.kronosad.projects.twitter.kronostwit.data;

import com.kronosad.projects.twitter.kronostwit.interfaces.IData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Russell
 */
public class DataDownloader {
    
    private static ArrayList<IData> downloaders = new ArrayList<IData>();
    
    public static boolean downloadData(){
        initDownloaders();
        for(IData downloader : downloaders){
            try {
                downloader.download();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(DataDownloader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DataDownloader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(DataDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                return false;
            }
        }
        return true;
    }
    
    private static void initDownloaders(){
        
        downloaders.add(new DataUsers());
        
    }
    
    
    
}
