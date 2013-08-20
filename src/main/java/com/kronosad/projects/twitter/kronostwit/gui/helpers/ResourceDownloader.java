
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import static com.kronosad.projects.twitter.kronostwit.console.ConsoleMain.loading;
import com.kronosad.projects.twitter.kronostwit.data.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    public static ArrayList<Resource> resources = new ArrayList<Resource>();
    
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
    
    public static boolean downloadResources(){
        System.out.println("Attempting Downloading of Resources...");

        ConsoleMain.loading.loadingResources();

        System.out.println("Downloading Resources...");
        for(Resource resource : resources){
            File file = new File(resource.getResourceName());
            String localMd5 = null;

            if(file.exists()){
                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ResourceDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }
                InputStream is = null;
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ResourceDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }

                byte[] buffer = new byte[8192];
                int read = 0;
                try{
                    while((read = is.read(buffer)) > 0){
                        digest.update(buffer, 0, read);
                    }

                    byte[] md5sum = digest.digest();
                    BigInteger bigInt = new BigInteger(1, md5sum);
                    localMd5 = bigInt.toString(16);
                }catch(IOException e){
                    Logger.getLogger(ResourceDownloader.class.getName()).log(Level.SEVERE, null, e);

                }
            }
            
            if((!file.exists()) || (!resource.getMd5().equals(localMd5) && !resource.isIgnoreUpdate())){
                System.out.println("Updating Resource: " + resource.getResourceName());
                try {
                    FileUtils.copyURLToFile(resource.getUrl(), file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "There was an error download resource: " + resource.getResourceName(), "Error Downloading Resource", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            
            
        }
        loading.initialCode();

        return true;
    }
    
    
}
