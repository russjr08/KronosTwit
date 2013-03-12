package com.kronosad.projects.twitter.kronostwit.gui.listeners;


import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RefreshMenuItemListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        HelperRefreshTimeline.refresh();
    }
}
