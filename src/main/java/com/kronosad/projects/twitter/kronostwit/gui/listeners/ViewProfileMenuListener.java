
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewProfile;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author russjr08
 */
public class ViewProfileMenuListener extends MouseAdapter {
    
    
    @Override
    public void mousePressed(MouseEvent event){
        Status status = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex());
        User user = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getUser();
        
        WindowViewProfile profile = new WindowViewProfile(user.getScreenName(), 500, 600, user, ConsoleMain.twitter);

    }
    
}
