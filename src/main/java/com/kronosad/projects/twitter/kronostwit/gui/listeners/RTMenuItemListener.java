package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RTMenuItemListener extends MouseAdapter {



    @Override
    public void mousePressed(MouseEvent mouseEvent){
//        System.out.println(MainGUI.dataList.getSelectedIndex());
        System.out.println(MainGUI.statuses.get(MainGUI.dataList.getSelectedIndex()).getText());
    }
}
