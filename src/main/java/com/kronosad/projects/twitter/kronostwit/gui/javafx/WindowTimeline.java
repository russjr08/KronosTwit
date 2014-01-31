package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx_external.scene.control.Dialogs;
import twitter4j.Status;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 6:08 PM
 */
@SuppressWarnings("all")
public class WindowTimeline implements Initializable {

    public static WindowTimeline instance;

    @FXML
    private ListView<String> tweetsView;

    @FXML
    private ListView<String> mentionsView;

    @FXML
    public TabPane tabPane;

    @FXML
    public MenuBar menuBar;

    @FXML
    public MenuItem btnLookupProfile;

    public ArrayList<Status> homeTweets = new ArrayList<Status>(), mentionsTweets = new ArrayList<Status>();

    public WindowTimeline(){
        if(instance == null){
            instance = this;
        }

    }



    public void addTweet(Status status, boolean initialLoad){
        System.out.println(status);
        if(!initialLoad){
            TwitterContainer.homeTweetList.add(0, TweetFormat.formatTweet(status));
            this.homeTweets.add(0, status);

        }else{
            TwitterContainer.homeTweetList.add(TweetFormat.formatTweet(status));
            this.homeTweets.add(status);

        }
    }

    public void addMention(Status status, boolean initialLoad){
        if(!initialLoad){
            TwitterContainer.mentionTweetList.add(0, TweetFormat.formatTweet(status));
            mentionsTweets.add(0, status);
        }else{
            TwitterContainer.mentionTweetList.add(TweetFormat.formatTweet(status));
            mentionsTweets.add(status);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        menuBar.setUseSystemMenuBar(true);

        tweetsView.setItems(TwitterContainer.homeTweetList);
        mentionsView.setItems(TwitterContainer.mentionTweetList);

        Timer animTimer = new Timer();
        AppStarter.getInstance().stage.setWidth(0);
        AppStarter.getInstance().stage.setHeight(0);
        animTimer.scheduleAtFixedRate(new TimerTask() {

            int i=0;

            @Override
            public void run() {
                if (i<100){

                    AppStarter.getInstance().stage.setWidth(AppStarter.getInstance().stage.getWidth() + 3.18);
                    AppStarter.getInstance().stage.setHeight(AppStarter.getInstance().stage.getHeight() + 6.5);
                    AppStarter.getInstance().stage.centerOnScreen();

                }
                else {
                    this.cancel();
                    AppStarter.getInstance().stage.centerOnScreen();

                }

                i++;
            }
        }, 2000, 2);


        AppStarter.getInstance().stage.setResizable(true);
        AppStarter.getInstance().stage.show();

        btnLookupProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = Dialogs.showInputDialog(AppStarter.getInstance().stage, "Whose profile would you like to view?");
                if(user != null || !user.isEmpty()){
                    Parent page = null;
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(AppStarter.class.getResource("ProfileViewer.fxml"));
                        page = (Parent) loader.load(AppStarter.class.getResource("ProfileViewer.fxml").openStream());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setTitle(String.format("@ %s 's Profile", user));
                    stage.setScene(new Scene(page, 409, 622));
                    ((ProfileViewer)loader.getController()).start(user);
                    stage.show();

                }
            }
        });

    }

}
