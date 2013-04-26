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


public class FavoriteMenuItemListner extends MouseAdapter {
    
    private IStatus statuses;
    private HelperRefreshTimeline refreshTL;
    
    public FavoriteMenuItemListner(IStatus stauses){
        this.statuses = statuses;
        refreshTL = new HelperRefreshTimeline(this.statuses);
    }
    
    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        Status status = statuses.getStatuses().get(statuses.getSelectedStatus());
        System.out.println(status.getText());
        System.out.println("Already Favorited: " + status.isFavorited() );
        if(!status.isFavorited()){


            try {
                ConsoleMain.twitter.createFavorite(status.getId());
                refreshTL.refreshTimeline();
            } catch (TwitterException e) {
                JOptionPane.showMessageDialog(null, "Could not create Favorite!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }

        }else{
            try{
                ConsoleMain.twitter.destroyFavorite(status.getId());
                refreshTL.refreshTimeline();
            }catch(TwitterException e){
                JOptionPane.showMessageDialog(null, "Could not unfavorite!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
