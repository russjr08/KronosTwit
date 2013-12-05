
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author russjr08
 */
public class HelperRefreshUserTimeline {
    public static boolean canRefresh = true;
    private static IStatus statuses;
    
    public HelperRefreshUserTimeline(IStatus status){
        this.statuses = status;
    }
    
    public void refreshUserTimeline(User user){
        if(canRefresh){
            try {

                System.out.println("Status update, REFRESH!");
                this.statuses.getStatuses().clear();
                this.statuses.getTweetList().clear();




                for(Status timelineStatuses : ConsoleMain.twitter.getUserTimeline(user.getScreenName())){
                    this.statuses.getStatuses().add(timelineStatuses);
                }

                for(Status status : this.statuses.getStatuses()){
                    this.statuses.getTweetList().addElement(TweetFormat.formatTweet(status));

                }
            } catch (TwitterException e) {
                e.printStackTrace();
                if(e.getStatusCode() == 429){
                    System.out.println("ERROR: Twitter limit reached. Shutting down auto update! You will need to" +
                            " restart this application to reuse refresh!");
                    canRefresh = false;
                }
            }

        }else{
            System.out.println("We can not refresh at the time! (Twitter status refresh limit reached!)");
        }
    }
}
