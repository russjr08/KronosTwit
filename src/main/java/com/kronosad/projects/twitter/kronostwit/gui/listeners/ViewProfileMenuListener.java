
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewProfile;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.Status;
import twitter4j.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        
        final User user = status.getUser();
        new Thread(){
            @Override
            public void run(){
                new WindowViewProfile(user.getScreenName(), 500, 600, user, ConsoleMain.twitter);
            }
        }.start();

    }
    
}
