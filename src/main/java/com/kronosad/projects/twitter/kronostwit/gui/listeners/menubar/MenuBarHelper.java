/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar;

import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.WindowThemePreference;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author russjr08
 */
public class MenuBarHelper {
    
    private static JMenu twitterMenu = new JMenu("Twitter");
    private static JMenu feedbackMenu = new JMenu("Feedback");
    private static JMenu preferencesMenu = new JMenu("Preferences");
    
    private static JMenuItem deleteData = new JMenuItem("Delete User Data");
    private static JMenuItem showProfile = new JMenuItem("Show Profile");
    
    private static JMenuItem submitIssue = new JMenuItem("Submit Issue");
    
    private static JMenuItem themePreference = new JMenuItem("Themes");
    

    
    public static void initMenu(){
        
        showProfile.addActionListener(new ShowProfileListener());
        deleteData.addActionListener(new DeleteUserDataListener());
        
        submitIssue.addActionListener(new SubmitIssueListener());
        
        themePreference.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent ae) {
                new WindowThemePreference();
            }
            
        });
        
        twitterMenu.add(deleteData);
        twitterMenu.add(showProfile);
        
        feedbackMenu.add(submitIssue);
        
        preferencesMenu.add(themePreference);
        
        
        

        
        WindowViewTimeline.menuBar.add(twitterMenu);
        WindowViewTimeline.menuBar.add(feedbackMenu);
        WindowViewTimeline.menuBar.add(preferencesMenu);
        
        
        
        
        
        
        
    }
    
    
    
    
}
