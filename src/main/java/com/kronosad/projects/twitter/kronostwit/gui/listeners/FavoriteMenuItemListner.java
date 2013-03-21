package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import twitter4j.TwitterException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FavoriteMenuItemListner extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        System.out.println(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getText());
        try {
            ConsoleMain.twitter.createFavorite(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getId());
        } catch (TwitterException e) {
            System.out.println("Error creating favorite! ");
            e.printStackTrace();
        }
    }

}
