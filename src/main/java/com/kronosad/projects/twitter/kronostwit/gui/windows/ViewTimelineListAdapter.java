
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author Russell
 */
public class ViewTimelineListAdapter extends MouseAdapter {
    private WindowViewTimeline timelineView;
    public ViewTimelineListAdapter(WindowViewTimeline timeline){
        timelineView = timeline;
        
    }
    
    @Override
    public void mouseClicked(MouseEvent event){
        if(SwingUtilities.isRightMouseButton(event)){
            timelineView.tweetsView.setSelectedIndex(timelineView.tweetsView.locationToIndex(event.getPoint()));
            
            Status status = timelineView.getStatuses().get(timelineView.tweetsView.getSelectedIndex());
            User user = status.getUser();
            
            timelineView.popUp.show(event.getComponent(), event.getX(), event.getY());
            
            timelineView.viewProfileMenuItem.setText("View %u's Profile".replaceAll("%u", user.getScreenName()));
            
            if(status.isFavorited()){
                timelineView.favoriteMenuItem.setText("Unfavorite");
            }else{
                timelineView.favoriteMenuItem.setText("Favorite");
            }
            
            if(user.isProtected()){
                timelineView.retweetMenuItem.setEnabled(false);
            }else{
                timelineView.retweetMenuItem.setEnabled(true);
            }
            
            if(status.isRetweetedByMe()){
                timelineView.retweetMenuItem.setText("Undo RT");
            }else{
                timelineView.retweetMenuItem.setText("RT");
            }
            
            if(user.isProtected()){
                timelineView.retweetMenuItem.setText("RT - Private Account");
            }
            
            timelineView.replyMenuItem.setText("Reply to %u".replaceAll("%u", user.getScreenName()));
            
            
        }
    }
    
    
}