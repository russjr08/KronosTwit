
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.data.Resource;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.kronosad.projects.twitter.kronostwit.console.ConsoleMain.loading;



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
            File file = getResource(fileName);

            if(file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            FileUtils.copyURLToFile(url, file);
            
            
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error downloading resource: " + fileName, "Error Downloading Resources", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return false;
        }
        
    }
    
    public static boolean downloadResources(){
        System.out.println("Attempting Downloading of Resources...");

        ConsoleMain.loading.loadingResources();

        System.out.println("Downloading Resources...");
        
        System.out.println("--- Local Resources... ---");
        for(Resource resource : resources){
            System.out.println("---");
            File file = getResource(resource.getResourceName());
            if(file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
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
            
            System.out.println("Resource Name: " + resource.getResourceName());
            System.out.println("Resource (Local) MD5: " + localMd5);
            System.out.println("---");
            
            
            if((!file.exists()) || (!resource.getMd5().equals(localMd5) && !resource.isIgnoreUpdate())){
                System.out.println("Updating Resource: " + resource.getResourceName());
                try {
                    FileUtils.copyURLToFile(resource.getUrl(), file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "There was an error downloading resource: " + resource.getResourceName(), "Error Downloading Resource", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return false;
                }
            }
            
            
        }
        System.out.println("--- End Local Resources ---");
        loading.initialCode();

        return true;
    }

    /**
     * Gets a resource.
     * @param fileName Filename of resource
     * @return A {@link java.io.File} object of the resource.
     */
    public static File getResource(String fileName){
        return new File("resources" + File.separator + fileName);
    }
    
    
}
