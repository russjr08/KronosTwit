/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.checkers.CheckerUpdate;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author russjr08
 */
public class Updater {
    
    public static void update(CheckerUpdate checker, boolean attemptTwo){
        try {
            System.out.println("Downloading Updated Jar file: " + checker.buildURL);
            FileUtils.copyURLToFile(checker.buildURL, new File("KronosTwit-Updated.jar"));
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String os = System.getProperty("os.name");
        System.out.println("Detected Operating System: " + os);
        
        while(ConsoleMain.loading.isDownloadingData || ConsoleMain.loading.isUpdating){
            
        }
        try{
            if(os.toLowerCase().contains("linux") || os.toLowerCase().contains("os x")){
                    File updateScript = new File("update.sh");
                    if(updateScript.exists()){
                        System.out.println("Running Linux/OS X update script!");

                        ProcessBuilder exeflag = new ProcessBuilder("chmod", "+x", "update.sh");
                        ProcessBuilder updater = new ProcessBuilder("./update.sh");
                        exeflag.start();
                        updater.start();
                    }else{
                        System.out.println("Update script not found... downloading!");
                        ResourceDownloader.downloadResource("update.sh");
                        Updater.update(checker, true);
                    }
                   

            }else if(os.toLowerCase().contains("windows")){
                System.out.println("Running Windows update script!");
                File updateScript = new File("update.bat");
                if(updateScript.exists()){
                    ProcessBuilder updater = new ProcessBuilder("update.bat");
                    
                    updater.start();
                }else{
                    System.out.println("Update script not found.. downloading now!");
                    ResourceDownloader.downloadResource("update.bat");
                    Updater.update(checker, true);
                }
                
                
            }else{
                JOptionPane.showMessageDialog(null, "Your OS is not supported for auto updates. "
                        + "Please move KronosTwit-Updated.jar manually!", "OS Not Supported", JOptionPane.ERROR_MESSAGE);
            }
        }catch(IOException ex){
            
            System.out.println("Failed to run update mechanism!");
            ex.printStackTrace();
            if(!attemptTwo){
            JOptionPane.showMessageDialog(null, "Failed to run updater! Now closing...", "Update Failed!", JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Failed to run updater! Downloading Resources and trying again!", "Update Failed!", JOptionPane.WARNING_MESSAGE);
            }
            if(!attemptTwo){
                ConsoleMain.loading.checkUpdate();
                ResourceDownloader.downloadResources();
                Updater.update(checker, true);
            }
        }
        
    }
    
}