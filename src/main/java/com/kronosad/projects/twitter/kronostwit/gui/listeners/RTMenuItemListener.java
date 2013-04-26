package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RTMenuItemListener extends MouseAdapter {

    private IStatus statuses;
    private HelperRefreshTimeline refreshTL = new HelperRefreshTimeline(statuses);
    
    public RTMenuItemListener(IStatus status){
        statuses = status;
    }
    

    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        Status status = statuses.getStatuses().get(statuses.getSelectedStatus());
        

        if(!status.isRetweetedByMe()){
            try {
                ConsoleMain.twitter.retweetStatus(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getId());
                refreshTL.refreshTimeline();
            } catch (TwitterException e) {
                JOptionPane.showMessageDialog(null, "Could not RT!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }else{

            try{
                ConsoleMain.twitter.destroyStatus(status.getId());
                refreshTL.refreshTimeline();
            }catch(TwitterException e){
                JOptionPane.showMessageDialog(null, "Could not Undo RT!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }

        }
    }
}
