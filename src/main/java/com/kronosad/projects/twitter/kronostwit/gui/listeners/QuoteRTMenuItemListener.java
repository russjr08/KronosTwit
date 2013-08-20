/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.ViewTimelineListAdapter;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowNewTweet;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author Russell
 */
public class QuoteRTMenuItemListener extends MouseAdapter{
    
    
    private IStatus statuses;
    
    public QuoteRTMenuItemListener(IStatus status){
        statuses = status;
    }
    
    
    @Override
    public void mousePressed(MouseEvent event){
        Status status = null;
        try{
            status = ConsoleMain.twitter.showStatus(statuses.getStatuses().get(statuses.getSelectedStatus()).getId());
            
        }catch(TwitterException ex){
            Logger.getLogger(ViewTimelineListAdapter.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        User user = status.getUser();
        if(!user.isProtected()){
            WindowNewTweet newTweet = new WindowNewTweet("Quote Retweet", 500, 500, statuses, "RT @" + user.getScreenName() + ": " + status.getText());

        }
    }
    
}
