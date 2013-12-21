package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowNewTweet;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReplyMenuItemListener extends MouseAdapter {
    private IStatus statuses;
    private HelperRefreshTimeline refreshTL;
    
    public ReplyMenuItemListener(IStatus status){
        statuses = status;
        refreshTL = new HelperRefreshTimeline(statuses);
        
    }
    
    @Override
    public void mousePressed(MouseEvent event){
        final User selectedUser = statuses.getStatuses().get(statuses.getSelectedStatus()).getUser();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WindowNewTweet newTweet = new WindowNewTweet("Replying To @" + selectedUser.getScreenName(), 500, 500, statuses, statuses.getStatuses().get(statuses.getSelectedStatus()).getId()
                        , "@" + selectedUser.getScreenName() + " ");
            }
        });



        refreshTL.refreshTimeline();


    }


}
