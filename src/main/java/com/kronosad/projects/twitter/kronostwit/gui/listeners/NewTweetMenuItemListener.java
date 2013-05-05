package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowNewTweet;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewTweetMenuItemListener extends MouseAdapter {
    private IStatus statuses;
    private HelperRefreshTimeline refreshTL;
    public NewTweetMenuItemListener(IStatus status){
        statuses = status;
        refreshTL = new HelperRefreshTimeline(statuses);
    }
    
    @Override
    public void mousePressed(MouseEvent event){
        
        System.out.println("Calling new Tweet window");
        WindowNewTweet newTweet = new WindowNewTweet("New Tweet", 500, 500, statuses);
        

    }

}
