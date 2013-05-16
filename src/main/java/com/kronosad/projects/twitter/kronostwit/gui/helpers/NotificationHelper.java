package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.event.NotificationEvent;
import ch.swingfx.twinkle.event.NotificationEventAdapter;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 *
 * @author russjr08
 */
public class NotificationHelper {
    
    public static void notifyMention(Status status, final WindowViewTimeline viewTimeline) throws TwitterException{
        
        if(status.getText().contains("@" + ConsoleMain.twitter.getScreenName())){
            System.setProperty("swing.aatext", "true");
            INotificationStyle style = new DarkDefaultNotification().withAlpha(0.9f).withWidth(400);
            
            new NotificationBuilder().withStyle(style).withTitle("KronosTwit - New Mention!")
                    .withMessage("@" + status.getUser().getScreenName() + ": " + status.getText())
                    .withListener(new NotificationEventAdapter() {
                        public void clicked(NotificationEvent event){
                             if(viewTimeline != null){
                                 viewTimeline.toFront();
                             }
                        } 
                    
                    })
                    .showNotification();
            
            
        }
        
    }
    
}
