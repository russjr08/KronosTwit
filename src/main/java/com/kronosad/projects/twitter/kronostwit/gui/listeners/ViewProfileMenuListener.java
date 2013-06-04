
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewProfile;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author russjr08
 */
public class ViewProfileMenuListener extends MouseAdapter {
    private IStatus statuses;
    public ViewProfileMenuListener(IStatus status){
        statuses = status;
    }
    
    @Override
    public void mousePressed(MouseEvent event){
        Status status;
        if(statuses.getStatuses().get(statuses.getSelectedStatus()).isRetweet()){
            status = statuses.getStatuses().get(statuses.getSelectedStatus()).getRetweetedStatus();

        }else{
            status = statuses.getStatuses().get(statuses.getSelectedStatus());

        }
        
        User user = status.getUser();
        
        WindowViewProfile profile = new WindowViewProfile(user.getScreenName(), 500, 600, user, ConsoleMain.twitter);

    }
    
}
