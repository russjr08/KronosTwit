package com.kronosad.projects.twitter.kronostwit.gui.listeners;


import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RefreshMenuItemListener extends MouseAdapter {
    private IStatus statuses;
    private HelperRefreshTimeline refreshTL;
    
    public RefreshMenuItemListener(IStatus status){
        statuses = status;
        
        refreshTL = new HelperRefreshTimeline(statuses);
    }
    
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        
        refreshTL.refreshTimeline();
    }
}
