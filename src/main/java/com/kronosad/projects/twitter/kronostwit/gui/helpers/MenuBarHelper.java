/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

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
    
    private static JMenuItem deleteData = new JMenuItem("Delete User Data");
    private static JMenuItem showProfile = new JMenuItem("Show Profile");
    
    public static void initMenu(){
        
        showProfile.addActionListener(new ShowProfileListener());
        deleteData.addActionListener(new DeleteUserDataListener());
        
        twitterMenu.add(deleteData);
        twitterMenu.add(showProfile);
        
        
        

        
        WindowViewTimeline.menuBar.add(twitterMenu);
        
        
        
        
        
        
    }
    
    
    
    
}
