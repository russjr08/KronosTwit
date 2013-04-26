package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import javax.swing.*;
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
        String tweet;
        String wrongTweet = null;
        int limit = 140;
        boolean valid = false;
        while(!valid){

            tweet = JOptionPane.showInputDialog("Enter Your New Tweet Below (Limit of 140)", wrongTweet);

            if(tweet == null){
                System.out.println("No context.");
                break;
            }

            if(tweet != null && tweet.length() > 140){
                valid = false;
                JOptionPane.showMessageDialog(null, "Your tweet is over 140 characters! Try again!", "Can't Post Tweet!", JOptionPane.ERROR_MESSAGE);
                wrongTweet = tweet;
            }else{
                valid = true;
                try {
                    if(tweet != null){
                        ConsoleMain.twitter.updateStatus(new StatusUpdate(tweet));
                    }
                } catch (TwitterException e) {
                    JOptionPane.showMessageDialog(null, "There was an error posting your tweet!", "Can't Post Tweet!", JOptionPane.ERROR_MESSAGE);

                } finally {
                    refreshTL.refreshTimeline();
                }
            }

        }

    }

}
