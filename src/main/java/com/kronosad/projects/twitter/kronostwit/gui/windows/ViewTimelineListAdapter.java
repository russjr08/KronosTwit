
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import twitter4j.Status;
import twitter4j.TwitterException;
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
            JPopupMenu.setDefaultLightWeightPopupEnabled(true);
            timelineView.tweetsView.setSelectedIndex(timelineView.tweetsView.locationToIndex(event.getPoint()));
            
            Status status = null;
            try {
                status = ConsoleMain.twitter.showStatus(timelineView.getStatuses().get(timelineView.getSelectedStatus()).getId());
            } catch (TwitterException ex) {
                Logger.getLogger(ViewTimelineListAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(timelineView.getSelectedStatus());
            System.out.println(timelineView.tweetsView.getMaxSelectionIndex());
            System.out.println(timelineView.getStatuses().size());
            User user = status.getUser();
            System.out.println(status.getText());
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
            
            try{
                if(user.getId() == ConsoleMain.twitter.getId()){
                    timelineView.deleteMenuItem.setVisible(true);
                }else{
                    timelineView.deleteMenuItem.setVisible(false);
                }
            }catch(TwitterException e){
                System.out.println("Error determining if we can delete this Tweet!");
                e.printStackTrace();
            }
            
        }
    }
    
    
}
