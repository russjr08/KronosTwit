package com.kronosad.projects.twitter.kronostwit.gui.helpers.notification;

import com.kronosad.projects.twitter.kronostwit.gui.WindowLoading;
import javafx.application.Platform;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 11:12 PM
 */
public class NotificationHelper {

    public static boolean tryMention(final Status status){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if(status.getText().contains(WindowLoading.twitter.getScreenName())){
                        Notification notification = new Notification("KronosTwit Mention", String.format("@%s mentioned you!", status.getUser().getScreenName()), Notification.INFO_ICON);
                        Notification.Notifier.INSTANCE.notify(notification);
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            return status.getText().contains(WindowLoading.twitter.getScreenName());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return false;
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
