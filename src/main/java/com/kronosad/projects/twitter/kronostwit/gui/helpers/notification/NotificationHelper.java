package com.kronosad.projects.twitter.kronostwit.gui.helpers.notification;

import com.kronosad.projects.twitter.kronostwit.gui.WindowLoading;
import javafx.application.Platform;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 11:12 PM
 */
public class NotificationHelper {

    public static boolean tryMention(final Status status){
        final AtomicBoolean mention = new AtomicBoolean();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if(status.getText().contains(WindowLoading.twitter.getScreenName())){
                        Notification notification = new Notification("KronosTwit Mention", "So and so mentioned you!", Notification.INFO_ICON);
                        Notification.Notifier.INSTANCE.notify(notification);
                        mention.set(true);
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

            }
        });

        return mention.get();
    }

    public static void notifyDelete(final Status status){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notification.Notifier.INSTANCE.notifyError("Tweet Deleted!", "@" + status.getUser().getScreenName() + ": " + status.getText());
            }
        });
    }

}
