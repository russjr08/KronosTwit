
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetHelper;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.GenericClickListener;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowTweetDetails;
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
                timelineView.quoteRTMenuItem.setEnabled(false);
            }else{
                timelineView.retweetMenuItem.setEnabled(true);
                timelineView.quoteRTMenuItem.setEnabled(true);
            }
            
            if(status.isRetweetedByMe()){
                timelineView.retweetMenuItem.setText("Undo RT");
            }else{
                timelineView.retweetMenuItem.setText("RT");
            }
            
            if(user.isProtected() && !status.isRetweet()){
                timelineView.retweetMenuItem.setText("RT - Private Account");
                timelineView.quoteRTMenuItem.setText("Quote RT - Private Account");
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
         
            ArrayList<String> links = TweetHelper.getLinksFromTweet(TweetHelper.unshortenTweet(status.getText()));
            
            
            for(String link : links){
                boolean alreadyContains = false;
                String linkText = "Navigate To: " + link;
                for(int i = 0; i < timelineView.popUp.getComponents().length; i++){
                    if(timelineView.popUp.getComponent(i) instanceof JMenuItem){
                        JMenuItem component = (JMenuItem) timelineView.popUp.getComponent(i);
                        if(component.getText().equalsIgnoreCase(linkText)){
                            alreadyContains = true;
                        }
                        if(component.getText().contains("Navigate")){
                            String[] parts = component.getText().split(" ");
                            if(!links.contains(parts[1])){
                                timelineView.popUp.remove(i);
                            }
                        }
                    }
                }
                if(!alreadyContains){
                    JMenuItem linkItem = new JMenuItem(linkText);
                    timelineView.popUp.add(linkItem);    
                }
                
            }
            
            ArrayList<String> hashtags = TweetHelper.getHashtagsFromTweet(status.getText());
            
            for(String hashtag : hashtags){
                boolean alreadyContains = false;
                String hashLink = "Search: " + hashtag;
                
                for(int i = 0; i < timelineView.popUp.getComponents().length; i++){
                    if(timelineView.popUp.getComponent(i) instanceof JMenuItem){
                        JMenuItem component = (JMenuItem) timelineView.popUp.getComponent(i);
                        if(component.getText().equalsIgnoreCase(hashLink)){
                            alreadyContains = true;
                        }
                        if(component.getText().contains("Search")){
                            String[] parts = component.getText().split(" ");
                            if(!hashtags.contains(parts[1])){
                                timelineView.popUp.remove(i);
                            }
                        }
                    }
                }
                if(!alreadyContains){
                    JMenuItem hashItem = new JMenuItem(hashLink);
                    timelineView.popUp.add(hashItem);
                }
               
                
            }
            
            for(int i = 0; i < timelineView.popUp.getComponents().length; i++){
                Component popUpItem = timelineView.popUp.getComponent(i);
                if(popUpItem instanceof JMenuItem){
                    JMenuItem item = (JMenuItem) popUpItem;
                    
                    for(String link : links){
                        if(item.getText().startsWith("Navigate")){
                            System.out.println("Adding Listener");
                            popUpItem.addMouseListener(new GenericClickListener());
                            if((!links.contains(link)) || links.isEmpty()){
                                timelineView.popUp.remove(timelineView.popUp.getComponent(i));
                            }
                        }
                    }
                    for(String hashTag : hashtags){
                        if(item.getText().startsWith("Search")){
                            System.out.println("Adding Listener for Search");
                            popUpItem.addMouseListener(new GenericClickListener());
                            if((!hashtags.contains(hashTag)) || (hashtags.isEmpty())){
                                timelineView.popUp.remove(timelineView.popUp.getComponent(i));
                            }
                        }
                    }
                    if(links.isEmpty()){
                        if(item.getText().startsWith("Navigate")){
                            timelineView.popUp.remove(i);
                        }
                    }
                    if(hashtags.isEmpty()){
                        if(item.getText().startsWith("Search")){
                            timelineView.popUp.remove(i);
                        }
                    }
                }
            }
            
            
            
            
            
            
            
            

            
        }
        
        if(SwingUtilities.isLeftMouseButton(event)){
            if(event.getClickCount() == 2){
                System.out.println("Double Click!");
                try {
                    new WindowTweetDetails(ConsoleMain.twitter.showStatus(timelineView.getStatuses().get(timelineView.getSelectedStatus()).getId()));
                } catch (TwitterException ex) {
                    Logger.getLogger(ViewTimelineListAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }
    
    
}
