/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.*;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.Preferences;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.filter.Filter;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author russjr08
 */
public class StreamStatusListener implements UserStreamListener{
    private IStatus statuses;
    private WindowViewTimeline timelineView;
    private User authedUser = null;

    
    public StreamStatusListener(IStatus status){
        statuses = status;
        if(status instanceof WindowViewTimeline){
            timelineView = (WindowViewTimeline)status;
        }
        try {
            authedUser = ConsoleMain.twitter.showUser(ConsoleMain.twitter.getId());
        } catch (TwitterException ex) {
            Logger.getLogger(HelperRefreshTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void onStatus(Status status) {
        ArrayList<Filter> filters = Preferences.getFilters();
        
        boolean filtered = false;
        for(Filter filter : filters){
            if(filter.isUsername()){
                if(filter.getFilter().equalsIgnoreCase(status.getUser().getScreenName())){
                    filtered = true;
                    break;
                }
            }else{
                if(filter.getFilter().toLowerCase().contains(status.getText())){
                    filtered = true;
                    break;
                }
            }
        }
        if(!filtered){
            timelineView.statuses.add(0, status);
            timelineView.tweetsList.add(0, TweetFormat.formatTweet(status));
        
            if(status.getText().contains(authedUser.getScreenName())){
                timelineView.mentions.add(0, status);
                timelineView.mentionsList.add(0, TweetFormat.formatTweet(status));
            }
        }
        
        try {
            NotificationHelper.notifyMention(status, timelineView);
        } catch (TwitterException ex) {
            Logger.getLogger(StreamStatusListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public void onDeletionNotice(StatusDeletionNotice sdn) {

        for(int i = 0; i < statuses.getStatuses().size(); i++){
            if(statuses.getStatuses().get(i).getId() == sdn.getStatusId()){
                statuses.getStatuses().remove(i);
                statuses.getTweetList().removeElementAt(i);
                //statuses.getTweetList().remove(i);

            }
        }
     
        
        //statuses.getTweetList().clear();
        /*for(Status status : statuses.getStatuses()){
            statuses.getTweetList().addElement(String.format("[%s]%s:\n %s", status.getCreatedAt(), status.getUser().getName(), status.getText()));
        }*/
        
    }

    public void onTrackLimitationNotice(int i) {

    }

    public void onScrubGeo(long l, long l1) {

    }

    public void onStallWarning(StallWarning sw) {

    }

    public void onException(Exception excptn) {
        excptn.printStackTrace();
    }

    public void onDeletionNotice(long l, long l1) {

    }

    public void onFriendList(long[] longs) {

    }

    public void onFavorite(User favoriter, User favorited, Status status) {
        try {
            if(!favoriter.getScreenName().equalsIgnoreCase(ConsoleMain.twitter.getScreenName())){
                
                NotificationHelper.notifyFavorite(status, timelineView, favoriter, favorited);
                
            }
        } catch (TwitterException ex) {
            Logger.getLogger(StreamStatusListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(StreamStatusListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onUnfavorite(User user, User user1, Status status) {

    }

    public void onFollow(User user, User user1) {

    }

    public void onDirectMessage(DirectMessage dm) {

    }

    public void onUserListMemberAddition(User user, User user1, UserList ul) {

    }

    public void onUserListMemberDeletion(User user, User user1, UserList ul) {

    }

    public void onUserListSubscription(User user, User user1, UserList ul) {

    }

    public void onUserListUnsubscription(User user, User user1, UserList ul) {

    }

    public void onUserListCreation(User user, UserList ul) {

    }

    public void onUserListUpdate(User user, UserList ul) {

    }

    public void onUserListDeletion(User user, UserList ul) {

    }

    public void onUserProfileUpdate(User user) {

    }

    public void onBlock(User user, User user1) {

    }

    public void onUnblock(User user, User user1) {

    }
    
    
    
}
