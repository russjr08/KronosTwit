/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.console;

import com.kronosad.projects.twitter.kronostwit.checkers.CheckerUpdate;
import com.kronosad.projects.twitter.kronostwit.data.DataDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowConsole;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Does all tasks that need to be performed before any heavy code / javax / swing / awt code is triggered.
 * @author russjr08
 */
public class ConsoleLoader {
    public static CheckerUpdate updater;
    public static WindowConsole console;

    @Deprecated
    private static String[] resourceList = {"greetings.txt", "beta_user.png", "verified_account.png"};
    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException{
        updater = new CheckerUpdate();
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KronosTwit - Alpha");
        console = new WindowConsole();
        updater.check();
        new Thread(){ // Need to download data here?
            @Override
            public void run(){
                boolean downloadData = DataDownloader.downloadData(); 
    
            }
        }.start();
        
        
        ConsoleMain.load(args);

            
        ResourceDownloader.downloadResources();

        
    }
    
    
    
}
