package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetFormat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 6:08 PM
 */
@SuppressWarnings("all")
public class WindowTimeline extends Application {
    private Stage primaryStage;

    public static WindowTimeline instance;

    public ObservableList<String> homeTweetList = FXCollections.observableArrayList();
    public ObservableList<String> mentionTweetList = FXCollections.observableArrayList();
    private ListView<String> tweetsView, mentionsView;

    public ArrayList<Status> homeTweets = new ArrayList<Status>(), mentionsTweets = new ArrayList<Status>();

    public WindowTimeline(){
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("WindowTimeline.fxml"));
        primaryStage.setTitle("KronosTwit");
        primaryStage.setScene(new Scene(root, 518, 650));


        // Setup components
        tweetsView = (ListView)root.lookup("#tweetsList");
        mentionsView = (ListView)root.lookup("#mentionsList");

        tweetsView.setItems(homeTweetList);
        mentionsView.setItems(mentionTweetList);

        Timer animTimer = new Timer();
        primaryStage.setWidth(1);
        primaryStage.setHeight(1);
        animTimer.scheduleAtFixedRate(new TimerTask() {

            int i=0;

            @Override
            public void run() {
                if (i<100){

                    primaryStage.setWidth(primaryStage.getWidth() + 5.18);
                    primaryStage.setHeight(primaryStage.getHeight() + 6.5);
                    primaryStage.centerOnScreen();

                }
                else {
                    this.cancel();
                    primaryStage.centerOnScreen();

                }

                i++;
            }
        }, 2000, 2);

//        FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
//        ft.setFromValue(0.1);
//        ft.setToValue(1.0);
//        ft.play();

        primaryStage.setResizable(false);
        primaryStage.show();

        instance = this;



    }

    public static void main(String[] args){
        launch(args);
    }

    public void addTweet(Status status){
        System.out.println(status);
        this.homeTweetList.add(TweetFormat.formatTweet(status));
        this.homeTweets.add(status);
    }

    public void addMention(Status status){
        WindowTimeline.instance.mentionTweetList.add(TweetFormat.formatTweet(status));
        WindowTimeline.instance.mentionsTweets.add(status);
    }


}
