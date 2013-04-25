package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RTMenuItemListener extends MouseAdapter {



    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        Status status = MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex());

        if(!status.isRetweetedByMe()){
            try {
                ConsoleMain.twitter.retweetStatus(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getId());
            } catch (TwitterException e) {
                JOptionPane.showMessageDialog(null, "Could not RT!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }else{

            try{
                ConsoleMain.twitter.destroyStatus(status.getId());
            }catch(TwitterException e){
                JOptionPane.showMessageDialog(null, "Could not Undo RT!", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }

        }
    }
}
