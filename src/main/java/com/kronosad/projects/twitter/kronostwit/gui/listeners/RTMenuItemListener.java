package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.ViewTimelineListAdapter;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.User;


public class RTMenuItemListener extends MouseAdapter {

    private IStatus statuses;
    private HelperRefreshTimeline refreshTL = new HelperRefreshTimeline(statuses);
    
    public RTMenuItemListener(IStatus status){
        statuses = status;
    }
    

    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        Status status = null;
            try {
                status = ConsoleMain.twitter.showStatus(statuses.getStatuses().get(statuses.getSelectedStatus()).getId());
            } catch (TwitterException ex) {
                Logger.getLogger(ViewTimelineListAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
        User user = status.getUser();
        if(!user.isProtected()){
            if(!status.isRetweetedByMe()){
                try {
                    ConsoleMain.twitter.retweetStatus(status.getId());
                    refreshTL.refreshTimeline();
                } catch (TwitterException e) {
                    JOptionPane.showMessageDialog(null, "Could not RT!", "Error", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }else{

                try{
                    ConsoleMain.twitter.destroyStatus(status.getCurrentUserRetweetId());
                    
                    refreshTL.refreshTimeline();
                }catch(TwitterException e){
                    JOptionPane.showMessageDialog(null, "Could not Undo RT!", "Error", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }

            }
        }
    }
}
