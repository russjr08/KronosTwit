/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.NotificationHelper;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

/**
 *
 * @author russjr08
 */
public class StreamStatusListener implements UserStreamListener{
    private IStatus statuses;
    private WindowViewTimeline timelineView;
    
    public StreamStatusListener(IStatus status){
        statuses = status;
        if(status instanceof WindowViewTimeline){
            timelineView = (WindowViewTimeline)status;
        }
        
    }
    
    public void onStatus(Status status) {
        statuses.getStatuses().add(0, status);
        statuses.getTweetList().add(0, String.format("[%s]%s:\n %s", status.getCreatedAt(), status.getUser().getName(), status.getText()));
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
                statuses.getTweetList().remove(i);

            }
        }
        
        //statuses.getTweetList().clear();
        /*for(Status status : statuses.getStatuses()){
            statuses.getTweetList().addElement(String.format("[%s]%s:\n %s", status.getCreatedAt(), status.getUser().getName(), status.getText()));
        }*/
        System.out.println("ON DELETE CALLED!");
        
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

    public void onFavorite(User user, User user1, Status status) {

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
