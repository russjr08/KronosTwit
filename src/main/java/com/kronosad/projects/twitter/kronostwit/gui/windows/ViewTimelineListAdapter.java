
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetHelper;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.GenericClickListener;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
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
                if(ConsoleMain.twitter.showStatus(timelineView.getStatuses().get(timelineView.getSelectedStatus()).getId()).isRetweet()){
                    status = ConsoleMain.twitter.showStatus(timelineView.getStatuses().get(timelineView.getSelectedStatus()).getId()).getRetweetedStatus();
                }else{
                    status = ConsoleMain.twitter.showStatus(timelineView.getStatuses().get(timelineView.getSelectedStatus()).getId());
                }
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
            
            if(user.isProtected() && !status.isRetweet()){
                timelineView.retweetMenuItem.setEnabled(false);
            }else{
                timelineView.retweetMenuItem.setEnabled(true);
            }
            
            if(status.isRetweetedByMe()){
                timelineView.retweetMenuItem.setText("Undo RT");
            }else{
                timelineView.retweetMenuItem.setText("RT");
            }
            
            if(user.isProtected() && !status.isRetweet()){
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
         
            ArrayList<String> links = TweetHelper.getLinksFromTweet(status.getText());
            
            
            for(String link : links){
                JMenuItem linkItem = new JMenuItem("Navigate To: " + link);
                timelineView.popUp.add(linkItem);
            }
            
            for(int i = 0; i < timelineView.popUp.getComponents().length; i++){
                Component popUpItem = timelineView.popUp.getComponent(i);
                if(popUpItem instanceof JMenuItem){
                    JMenuItem item = (JMenuItem) popUpItem;
                    for(String link : links){
                        if(item.getText().startsWith("Navigate")){
                            System.out.println("Adding Listener");
                            popUpItem.addMouseListener(new GenericClickListener());
                            if(!item.getText().equalsIgnoreCase("Navigate To: " + link)){
                                timelineView.popUp.remove(timelineView.popUp.getComponent(i));
                            }
                        }else{
                            System.out.println("Doesn't start with Navigate!");
                        }
                    }
                    if(links.isEmpty()){
                        if(item.getText().startsWith("Navigate")){
                            timelineView.popUp.remove(i);
                        }
                    }
                }
            }
            
            
            
            
            
            
            
            

            
        }
        
        
    }
    
    
}
