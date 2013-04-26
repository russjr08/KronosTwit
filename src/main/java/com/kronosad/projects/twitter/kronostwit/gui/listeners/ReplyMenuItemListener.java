package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
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
        String reply, replyMsg, failedMsg;

        StatusUpdate update;

        failedMsg = null;

        User user = statuses.getStatuses().get(statuses.getSelectedStatus()).getUser();
        boolean isValid = false;


        replyMsg = "Reply to " + "@" + user.getScreenName() + " : " + statuses.getStatuses().get(statuses.getSelectedStatus()).getText();




        while(!isValid){

            if(failedMsg == null){

                reply = JOptionPane.showInputDialog(null, replyMsg, "@" + user.getScreenName() + " ");

            }else{
                reply = JOptionPane.showInputDialog(null, replyMsg, failedMsg);

            }




            if(reply == null){
                System.out.println("No Context.");
                break;
            }

            if(reply != null && reply.length() > 140){
                failedMsg = reply;
                isValid = false;

                JOptionPane.showMessageDialog(null, "Your tweet is over 140 characters! Try again!", "Can't Post Tweet!", JOptionPane.ERROR_MESSAGE);

            }else{
                isValid = true;

                try{
                    update = new StatusUpdate(reply);

                    update.setInReplyToStatusId(statuses.getStatuses().get(statuses.getSelectedStatus()).getId());

                    ConsoleMain.twitter.updateStatus(update);
                    
                    refreshTL.refreshTimeline();
                }catch (TwitterException e){
                    JOptionPane.showMessageDialog(null, "There was an error replying to status, check console!", "Error!", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }

            }





        }


    }


}
