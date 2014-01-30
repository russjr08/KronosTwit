package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;
import twitter4j.Status;

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

    }
}
