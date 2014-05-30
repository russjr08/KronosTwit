package com.kronosad.projects.twitter.kronostwit.gui.components;

import com.kronosad.projects.twitter.kronostwit.gui.AppStarter;
import com.kronosad.projects.twitter.kronostwit.gui.WindowNewTweet;
import com.kronosad.projects.twitter.kronostwit.interfaces.ITweetReceptor;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.IOException;

/**
 * Created by Russell on 5/22/2014.
 */
public class ReplyMenuItem extends CustomMenuItem {

    public ReplyMenuItem(ITweetReceptor tweetReceptor) {
        super(tweetReceptor);
        this.setOnAction((event) -> {
            Platform.runLater(() -> {
                Status status = null;
                try {
                    status = tweetReceptor.getSelectedStatus();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

                Parent page = null;
                FXMLLoader loader = new FXMLLoader();

                try {
                    loader.setLocation(AppStarter.class.getResource("WindowNewTweet.fxml"));
                    page = (Parent) loader.load(AppStarter.class.getResource("WindowNewTweet.fxml").openStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((WindowNewTweet)loader.getController()).setReply(status);
                Stage stage = new Stage();
                stage.setTitle("Replying to: " + status.getUser().getScreenName());
                stage.setScene(new Scene(page, 396, 174));

                stage.show();
            });

        });
    }

}
