/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.console;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import java.io.File;

/**
 * Does all tasks that need to be performed before any heavy code / javax / swing / awt code is triggered.
 * @author russjr08
 */
public class ConsoleLoader {
    
    private static String[] resourceList = {"greetings.txt", "beta_user.png"};
    
    public static void main(String[] args){
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KronosTwit - Beta");
        loadResources();

        ConsoleMain.load();
    }
    
    public static void loadResources(){
        ConsoleMain.loading.loadingResources();
        for(String resource : resourceList){
            File resourceFile = new File(resource);
            if(!resourceFile.exists()){
                ResourceDownloader.downloadResource(resource);
                
            }
            
        }
        
        
    }
    
    
    
    
}
