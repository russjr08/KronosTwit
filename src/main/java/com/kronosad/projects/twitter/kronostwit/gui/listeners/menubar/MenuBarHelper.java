/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.WindowThemePreference;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.filter.WindowPreferenceFilters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author russjr08
 */
public class MenuBarHelper {
    
    private static JMenu twitterMenu = new JMenu("Twitter");
    private static JMenu feedbackMenu = new JMenu("Feedback");
    private static JMenu preferencesMenu = new JMenu("Preferences");
    private static JMenu helpMenu = new JMenu("Help");
    
    private static JMenuItem deleteData = new JMenuItem("Delete User Data");
    private static JMenuItem showProfile = new JMenuItem("Show Profile");
    
    private static JMenuItem submitIssue = new JMenuItem("Submit Issue");
        
    private static JMenuItem filterPreference = new JMenuItem("Filters");
    private static JMenuItem themePreference = new JMenuItem("Themes");

    private static JMenuItem changelog = new JMenuItem("Changelog (Version " + ConsoleLoader.updater.getVersion().getVersionNumber() + ")");
    

    
    public static void initMenu(){
        
        showProfile.addActionListener(new ShowProfileListener());
        deleteData.addActionListener(new DeleteUserDataListener());
        
        submitIssue.addActionListener(new SubmitIssueListener());
        
        themePreference.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent ae) {
                new WindowThemePreference();
            }
            
        });
        
        filterPreference.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent ae){
                new WindowPreferenceFilters();
            }
            
            
        });

        changelog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ConsoleMain.desktop.browse(ConsoleLoader.updater.getVersion().getChangeLog().toURI());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        twitterMenu.add(deleteData);
        twitterMenu.add(showProfile);
        
        feedbackMenu.add(submitIssue);
        
        preferencesMenu.add(filterPreference);
        preferencesMenu.add(themePreference);

        helpMenu.add(changelog);
        
        

        
        WindowViewTimeline.menuBar.add(twitterMenu);
        WindowViewTimeline.menuBar.add(feedbackMenu);
        WindowViewTimeline.menuBar.add(preferencesMenu);
        WindowViewTimeline.menuBar.add(helpMenu);
        
        
        
        
        
        
        
    }
    
    
    
    
}
