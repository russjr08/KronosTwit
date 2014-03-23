/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.checkers.UpdateInformation;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author russjr08
 */
public class Updater {
    public static void update(UpdateInformation checker, boolean attemptTwo, Stage stage){
        try {
            System.out.println("Downloading Updated Jar file: " + checker.getVersion().getDownloadURL());
            FileUtils.copyURLToFile(checker.getVersion().getDownloadURL(), new File("KronosTwit-Updated.jar"));
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }

        String os = System.getProperty("os.name");
        System.out.println("Detected Operating System: " + os);

        try{
            if(os.toLowerCase().contains("linux") || os.toLowerCase().contains("os x")){
                File updateScript = ResourceDownloader.getResource("update.sh");
                if(updateScript.exists()){
                    System.out.println("Running Linux/OS X update script!");

                    updateScript.setExecutable(true);
                    ProcessBuilder updater = new ProcessBuilder("./resources/update.sh");

                    updater.start();
                }else{
                    System.out.println("Update script not found... downloading!");
                    ResourceDownloader.downloadResource("update.sh");
                    Updater.update(checker, true);
                }


            }else if(os.toLowerCase().contains("windows")){
                System.out.println("Running Windows update script!");
                File updateScript = ResourceDownloader.getResource("update.bat");
                if(updateScript.exists()){
                    ProcessBuilder updater = new ProcessBuilder("resources/update.bat");

                    updater.start();
                }else{
                    System.out.println("Update script not found.. downloading now!");
                    ResourceDownloader.downloadResource("update.bat");
                    Updater.update(checker, true);
                }


            }else{
                Dialogs.create().masthead("Update Error").message("Your OS is not supported for auto updates. \"\n" +
                        "                        + \"Please move KronosTwit-Updated.jar manually!\"").showError();
            }
        }catch(IOException ex){

            System.out.println("Failed to run update mechanism!");
            ex.printStackTrace();
            if(!attemptTwo){
                Dialogs.create().masthead("Update Error").message("Failed to run updater! Now closing...").showError();
            }else{
                Dialogs.create().masthead("Update Error").message("Failed to run updater! Downloading Resources and trying again!").showError();

            }
            if(!attemptTwo){
                ResourceDownloader.downloadResources();
                Updater.update(checker, true);
            }
        }
    }

    public static void update(UpdateInformation checker, boolean attemptTwo){
        update(checker, attemptTwo, null);
    }
    
}
