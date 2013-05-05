package com.kronosad.projects.twitter.kronostwit.gui.listeners;


import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewProfile;
import twitter4j.Status;
import twitter4j.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListMouseAdapter extends MouseAdapter{




    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(SwingUtilities.isRightMouseButton(mouseEvent)){
//            System.out.println("Mouse clicked!");
            MainGUI.dataList.setSelectedIndex(MainGUI.dataList.locationToIndex(mouseEvent.getPoint()));

            Status status = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex());
            User user = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getUser();




            MainGUI.popUp.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());

            MainGUI.popUp.setToolTipText(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getText());
                
            MainGUI.viewProfileMenuItem.setText("View %u's Profile".replaceAll("%u", user.getScreenName()));
            
            if(status.isFavorited()){
                MainGUI.favoriteMenuItem.setText("Unfavorite");
            }else{
                MainGUI.favoriteMenuItem.setText("Favorite");
            }
            
            if(user.isProtected()){
                MainGUI.retweetMenuItem.setEnabled(false);
            }else{
                MainGUI.retweetMenuItem.setEnabled(true);
            }
            
            if(status.isRetweetedByMe()){
                MainGUI.retweetMenuItem.setText("Undo RT");
            }else{
                MainGUI.retweetMenuItem.setText("RT");
            }
            
            if(user.isProtected()){
                MainGUI.retweetMenuItem.setText("RT - Private Account");
            }
            
            

            MainGUI.replyMenuItem.setText("Reply to %u".replaceAll("%u", MainGUI.statuses.get
                    (MainGUI.dataList.getSelectedIndex()).getUser().getScreenName()));

        }


    }


}
