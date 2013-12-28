package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.event.NotificationEvent;
import ch.swingfx.twinkle.event.NotificationEventAdapter;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import ch.swingfx.twinkle.window.Positions;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author russjr08
 */
public class NotificationHelper {
    
    public static void notifyMention(Status status, final WindowViewTimeline viewTimeline) throws TwitterException{
        INotificationStyle style = new DarkDefaultNotification().withAlpha(0.9f).withWidth(400);      
        
        if(status.getText().contains("@" + ConsoleMain.twitter.getScreenName())){
            // Anti-aliasing
            System.setProperty("swing.aatext", "true");
            
            if(!status.getText().startsWith("RT")){
                
            
                new NotificationBuilder().withStyle(style).withTitle("KronosTwit - New Mention!")
                        .withMessage("@" + status.getUser().getScreenName() + ": " + status.getText())
                        .withListener(new NotificationEventAdapter() {
                            public void clicked(NotificationEvent event){
                                 if(viewTimeline != null){
                                     viewTimeline.toFront();
                                 }
                            }

                        })
                        .withPosition(Positions.NORTH_WEST)
                        .withDisplayTime(15000)
                        .showNotification();
                }else{
                    new NotificationBuilder().withStyle(style).withTitle("KronosTwit - Retweet!")
                        .withMessage("@" + status.getUser().getScreenName() + ": " + status.getText())
                        .withListener(new NotificationEventAdapter() {
                            public void clicked(NotificationEvent event){
                                 if(viewTimeline != null){
                                     viewTimeline.toFront();
                                 }
                            }

                        })
                        .withPosition(Positions.NORTH_WEST)
                        .withDisplayTime(15000)
                        .showNotification();
                }
            
        }
        
    }
    
    public static void notifyDeletion(Status status, final WindowViewTimeline viewTimeline) throws TwitterException{
        
            System.setProperty("swing.aatext", "true");
            INotificationStyle style = new DarkDefaultNotification().withAlpha(0.9f).withWidth(400);
            
            new NotificationBuilder().withStyle(style).withTitle("KronosTwit - Status Deletion!")
                    .withMessage("@" + status.getUser().getScreenName() + ": " + status.getText())
                    .withListener(new NotificationEventAdapter() {
                        public void clicked(NotificationEvent event){
                             if(viewTimeline != null){
                                 viewTimeline.toFront();
                             }
                        } 
                    
                    })
                    .withPosition(Positions.NORTH_WEST)
                    .withDisplayTime(15000)
                    .showNotification();
            
            
        
        
    }
    
    public static void notifyFavorite(Status status, final WindowViewTimeline viewTimeline, User favoriter) throws TwitterException{
        System.setProperty("swing.aatext", "true");
            INotificationStyle style = new DarkDefaultNotification().withAlpha(0.9f).withWidth(400);
            
            new NotificationBuilder().withStyle(style).withTitle("KronosTwit - Your Tweet was Favorited! (By @" + favoriter.getScreenName() + ")")
                    .withMessage("@" + status.getUser().getScreenName() + ": " + status.getText())
                    .withListener(new NotificationEventAdapter() {
                        public void clicked(NotificationEvent event){
                             if(viewTimeline != null){
                                 viewTimeline.toFront();
                             }
                        } 
                    
                    })
                    .withPosition(Positions.NORTH_WEST)
                    .withDisplayTime(15000)
                    .showNotification();
            
        
    }

    public static void notifyFollower(final WindowViewTimeline viewTimeline, User follower){
        System.setProperty("swing.aatext", "true");
        INotificationStyle style = new DarkDefaultNotification().withAlpha(0.9f).withWidth(400);

        new NotificationBuilder().withStyle(style).withTitle("KronosTwit - You have a new follower!")
                .withMessage("@" + follower.getScreenName() + " Now follows you!")
                .withListener(new NotificationEventAdapter() {
                    public void clicked(NotificationEvent event) {
                        if (viewTimeline != null) {
                            viewTimeline.toFront();
                        }
                    }

                })
                .withPosition(Positions.NORTH_WEST)
                .withDisplayTime(15000)
                .showNotification();

    }
    
}
