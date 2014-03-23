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
import twitter4j.TwitterException;

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
    public MenuItem favorite, reply, rt, cancel;


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
        cancel = new MenuItem("Cancel");

        favorite.setOnAction((event) -> {
            Status status = null;
            try {
                status = TwitterContainer.twitter.showStatus(TwitterContainer.homeTweetList.get(tweetsView.getSelectionModel().getSelectedIndex()).getId());
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            System.out.println(status);
            if(!status.isFavorited()) {
                try {
                    TwitterContainer.twitter.createFavorite(status.getId());
                } catch (TwitterException e) {
                    Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not create favorite!").showWarning();
                    e.printStackTrace();
                }
            }else{
                try {
                    TwitterContainer.twitter.destroyFavorite(status.getId());
                } catch (TwitterException e) {
                    Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not destroy favorite!").showWarning();

                    e.printStackTrace();
                }
            }
        });

        rt.setOnAction((actionEvent) -> {
            Status status = null;
            try {
                status = TwitterContainer.twitter.showStatus(TwitterContainer.homeTweetList.get(tweetsView.getSelectionModel().getSelectedIndex()).getId());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            if(!status.getUser().isProtected()) {
                rt.setDisable(false);
                if (!status.isRetweetedByMe()) {
                    try {
                        TwitterContainer.twitter.retweetStatus(status.getId());
                    } catch (TwitterException e) {
                        Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not create favorite!").showWarning();
                        e.printStackTrace();
                    }
                } else {
                    try {
                        TwitterContainer.twitter.destroyStatus(status.getCurrentUserRetweetId());
                    } catch (TwitterException e) {
                        Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not destroy favorite!").showWarning();

                        e.printStackTrace();
                    }
                }
            }else{
                rt.setDisable(true);
            }
        });

        cm.setOnShowing((event) -> {
            Status status = null;
            try {
                status = TwitterContainer.twitter.showStatus(TwitterContainer.homeTweetList.get(tweetsView.getSelectionModel().getSelectedIndex()).getId());
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            reply.setText("Reply to " + status.getUser().getScreenName());

            if (!status.isFavorited()) {
                favorite.setText("Favorite Tweet");
            } else {
                favorite.setText("Unfavorite Tweet");
            }
        });

        reply.setOnAction((event) -> {

            Status status = null;
            try {
                status = TwitterContainer.twitter.showStatus(TwitterContainer.homeTweetList.get(tweetsView.getSelectionModel().getSelectedIndex()).getId());
            } catch (TwitterException e) {
                e.printStackTrace();
            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppStarter.class.getResource("WindowNewTweet.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Replying to: " + status.getUser().getScreenName());
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.setScene(new Scene(root, 396, 174));
            stage.show();

            ((WindowNewTweet)loader.getController()).setReply(status);



        });


        cm.getItems().addAll(favorite, reply, rt, new SeparatorMenuItem(), cancel);

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
