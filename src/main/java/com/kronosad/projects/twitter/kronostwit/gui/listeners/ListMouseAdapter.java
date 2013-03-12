package com.kronosad.projects.twitter.kronostwit.gui.listeners;


import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListMouseAdapter extends MouseAdapter{




    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(SwingUtilities.isRightMouseButton(mouseEvent)){
//            System.out.println("Mouse clicked!");
            MainGUI.dataList.setSelectedIndex(MainGUI.dataList.locationToIndex(mouseEvent.getPoint()));
            MainGUI.popUp.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());

        }


    }


}
