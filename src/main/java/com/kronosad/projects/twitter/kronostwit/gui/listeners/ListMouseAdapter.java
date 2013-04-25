package com.kronosad.projects.twitter.kronostwit.gui.listeners;


import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import twitter4j.Status;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListMouseAdapter extends MouseAdapter{




    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(SwingUtilities.isRightMouseButton(mouseEvent)){
//            System.out.println("Mouse clicked!");
            Status status = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex());

            MainGUI.dataList.setSelectedIndex(MainGUI.dataList.locationToIndex(mouseEvent.getPoint()));




            MainGUI.popUp.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());

            MainGUI.popUp.setToolTipText(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getText());

            if(status.isFavorited()){
                MainGUI.favoriteMenuItem.setText("Unfavorite");
            }

            if(status.isRetweetedByMe()){
                MainGUI.retweetMenuItem.setText("Undo RT");
            }

            MainGUI.replyMenuItem.setText("Reply to %u".replaceAll("%u", MainGUI.statuses.get
                    (MainGUI.dataList.getSelectedIndex()).getUser().getScreenName()));


        }


    }


}
