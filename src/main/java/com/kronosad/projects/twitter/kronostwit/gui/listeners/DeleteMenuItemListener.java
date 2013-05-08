package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author russjr08
 */
public class DeleteMenuItemListener extends MouseAdapter {
    
    private IStatus statuses;
    private HelperRefreshTimeline refreshTL;
    
    public DeleteMenuItemListener(IStatus status){
        statuses = status;
        
        refreshTL = new HelperRefreshTimeline(statuses);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        
        Status status = statuses.getStatuses().get(statuses.getSelectedStatus());
        
        User user = status.getUser();
        try{
            if(user.getId() == ConsoleMain.twitter.getId()){
                ConsoleMain.twitter.destroyStatus(status.getId());
                refreshTL.refreshTimeline();
            }else{
                throw new IllegalArgumentException("This is not your tweet!");
            }
            
       }catch(TwitterException e){
           System.out.println("Error!");
           
           e.printStackTrace();
       }
    
    }
    
    
    
}
