package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FavoriteMenuItemListner extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        Status status = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex());

        if(!status.isFavorited()){


            try {
                ConsoleMain.twitter.createFavorite(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getId());
            } catch (TwitterException e) {
                JOptionPane.showMessageDialog(null, "Could not create Favorite!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }

        }else{
            try{
                ConsoleMain.twitter.destroyFavorite(status.getId());
            }catch(TwitterException e){
                JOptionPane.showMessageDialog(null, "Could not unfavorite!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
