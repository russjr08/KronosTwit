/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar;

import com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar.DeleteUserDataListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar.ShowProfileListener;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import javax.swing.*;

/**
 *
 * @author russjr08
 */
public class MenuBarHelper {
    
    private static JMenu twitterMenu = new JMenu("Twitter");
    private static JMenu feedbackMenu = new JMenu("Feedback");
    
    private static JMenuItem deleteData = new JMenuItem("Delete User Data");
    private static JMenuItem showProfile = new JMenuItem("Show Profile");
    
    private static JMenuItem submitIssue = new JMenuItem("Submit Issue");
    

    
    public static void initMenu(){
        
        showProfile.addActionListener(new ShowProfileListener());
        deleteData.addActionListener(new DeleteUserDataListener());
        
        submitIssue.addActionListener(new SubmitIssueListener());
        
        twitterMenu.add(deleteData);
        twitterMenu.add(showProfile);
        
        feedbackMenu.add(submitIssue);
        
        
        

        
        WindowViewTimeline.menuBar.add(twitterMenu);
        WindowViewTimeline.menuBar.add(feedbackMenu);
        
        
        
        
        
        
        
    }
    
    
    
    
}
