package com.kronosad.projects.twitter.kronostwit.gui.components;

import com.kronosad.projects.twitter.kronostwit.gui.AppStarter;
import com.kronosad.projects.twitter.kronostwit.gui.ProfileViewer;
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
public class ProfileMenuItem extends CustomMenuItem {

    public ProfileMenuItem(ITweetReceptor tweetReceptor) {
        super(tweetReceptor);
        this.setOnAction((event) -> {
            new Thread(() -> {
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
                        loader.setLocation(AppStarter.class.getResource("ProfileViewer.fxml"));
                        page = (Parent) loader.load(AppStarter.class.getResource("ProfileViewer.fxml").openStream());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setTitle(String.format("@%s 's Profile", status.getUser().getScreenName()));
                    stage.setScene(new Scene(page, 409, 622));
                    ((ProfileViewer) loader.getController()).start(status.getUser().getScreenName());
                    stage.show();
                });
            }){}.start();
        });
    }
}
