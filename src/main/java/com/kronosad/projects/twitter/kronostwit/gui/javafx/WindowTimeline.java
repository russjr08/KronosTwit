package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetHelper;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.render.TweetListCellRender;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import twitter4j.Status;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 6:08 PM
 */
@SuppressWarnings("all")
public class WindowTimeline implements Initializable {

    public static WindowTimeline instance;

    @FXML
    private ListView<Status> tweetsView;

    @FXML
    private ListView<Status> mentionsView;

    @FXML
    public TabPane tabPane;

    @FXML
    public MenuBar menuBar;

    @FXML
    public MenuItem btnLookupProfile;

    @FXML public MenuItem btnNewTweet;

    public ArrayList<Status> homeTweets = new ArrayList<Status>(), mentionsTweets = new ArrayList<Status>();

    public ContextMenu cm;
    public MenuItem favorite, reply, rt;


    public WindowTimeline(){
        if(instance == null){
            instance = this;
        }

    }


    public void initContextMenu(){
        cm = new ContextMenu();

        favorite = new MenuItem("Favorite Tweet");
        reply = new MenuItem("Reply to %s");
        rt = new MenuItem("RT Tweet");

        cm.getItems().addAll(favorite, reply, rt);

        tweetsView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.SECONDARY){
                    cm.show(tweetsView, e.getScreenX(), e.getScreenY());
                }
            }
        });
    }

    public void addTweet(Status status, boolean initialLoad){
        if(!initialLoad){
            TwitterContainer.homeTweetList.add(0, TweetHelper.removeTwitterLinks(status));
            this.homeTweets.add(0, status);

        }else{
            TwitterContainer.homeTweetList.add(TweetHelper.removeTwitterLinks(status));
            this.homeTweets.add(status);

        }
    }

    public void addMention(Status status, boolean initialLoad){
        if(!initialLoad){
            TwitterContainer.mentionTweetList.add(0, TweetHelper.removeTwitterLinks(status));
            mentionsTweets.add(0, status);
        }else{
            TwitterContainer.mentionTweetList.add(TweetHelper.removeTwitterLinks(status));
            mentionsTweets.add(status);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        menuBar.setUseSystemMenuBar(true);

        tweetsView.setItems(TwitterContainer.homeTweetList);

        tweetsView.setCellFactory(stringListView -> new TweetListCellRender());

        mentionsView.setItems(TwitterContainer.mentionTweetList);

        mentionsView.setCellFactory(stringListView -> new TweetListCellRender());

        Timer animTimer = new Timer();
        AppStarter.getInstance().stage.setWidth(518);
        AppStarter.getInstance().stage.setHeight(650);
        AppStarter.getInstance().stage.centerOnScreen();

        // I think we're going to get rid of the animation. It's sketchy code...
//        animTimer.scheduleAtFixedRate(new TimerTask() {
//
//            int i=0;
//
//            @Override
//            public void run() {
//                if (i<100){
//
//                    AppStarter.getInstance().stage.setWidth(AppStarter.getInstance().stage.getWidth() + 3.18);
//                    AppStarter.getInstance().stage.setHeight(AppStarter.getInstance().stage.getHeight() + 6.5);
//                    AppStarter.getInstance().stage.centerOnScreen();
//
//                }
//                else {
//                    this.cancel();
//                    AppStarter.getInstance().stage.centerOnScreen();
//
//                }
//
//                i++;
//            }
//        }, 2000, 2);


        AppStarter.getInstance().stage.setResizable(true);
        AppStarter.getInstance().stage.show();

        btnLookupProfile.setOnAction((actionEvent) -> {

                String user = Dialogs.create().masthead("Waiting for Input...").message("Which user would you like to visit?").showTextInput();

                if(user != null && !user.isEmpty()){
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

        });

        btnNewTweet.setOnAction((actionEvent) ->{
            Parent page = null;
            FXMLLoader loader = new FXMLLoader();

            try {
                loader.setLocation(AppStarter.class.getResource("WindowNewTweet.fxml"));
                page = (Parent) loader.load(AppStarter.class.getResource("WindowNewTweet.fxml").openStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Compose a new Tweet...");
            stage.setScene(new Scene(page, 396, 174));
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();

        });




        initContextMenu();

    }

}
