package com.kronosad.projects.twitter.kronostwit.gui.stream;

import com.kronosad.projects.twitter.kronostwit.gui.TwitterContainer;
import com.kronosad.projects.twitter.kronostwit.gui.WindowTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.notification.NotificationHelper;
import javafx.application.Platform;
import twitter4j.*;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 11:02 PM
 */
public class TwitterStreamListener implements UserStreamListener {
    public TwitterStreamListener(){
        System.out.println("Stream init'd");
    }
    @Override
    public void onDeletionNotice(long directMessageId, long userId) {}

    @Override
    public void onFriendList(long[] friendIds) {}

    @Override
    public void onFavorite(User source, User target, Status favoritedStatus) {

    }

    @Override
    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {}

    @Override
    public void onFollow(User source, User followedUser) {

    }

    @Override
    public void onDirectMessage(DirectMessage directMessage) {}

    @Override
    public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {}

    @Override
    public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {}

    @Override
    public void onUserListSubscription(User subscriber, User listOwner, UserList list) {}

    @Override
    public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {}

    @Override
    public void onUserListCreation(User listOwner, UserList list) {}

    @Override
    public void onUserListUpdate(User listOwner, UserList list) {}

    @Override
    public void onUserListDeletion(User listOwner, UserList list) {}

    @Override
    public void onUserProfileUpdate(User updatedUser) {}

    @Override
    public void onBlock(User source, User blockedUser) {

    }

    @Override
    public void onUnblock(User source, User unblockedUser) {

    }

    @Override
    public void onStatus(final Status status) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(NotificationHelper.tryMention(status)){
                    WindowTimeline.instance.addMention(status, false);
                }
                WindowTimeline.instance.addTweet(status, false);
            }
        });



    }

    @Override
    public void onDeletionNotice(final StatusDeletionNotice sdn) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < WindowTimeline.instance.homeTweets.size(); i++){
                    if(sdn.getStatusId() == WindowTimeline.instance.homeTweets.get(i).getId()){
                        NotificationHelper.notifyDelete(WindowTimeline.instance.homeTweets.get(i));
                        WindowTimeline.instance.homeTweets.remove(i);
                        TwitterContainer.homeTweetList.remove(i);
                    }
                }
            }
        });

    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {}

    @Override
    public void onStallWarning(StallWarning warning) {}

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}
