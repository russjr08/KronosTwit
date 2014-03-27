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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import twitter4j.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ListView<Status> searchView;

    @FXML
    public TabPane tabPane;

    @FXML
    public MenuBar menuBar;

    @FXML
    public MenuItem btnLookupProfile;

    @FXML public MenuItem btnNewTweet;

    public ArrayList<Status> homeTweets = new ArrayList<Status>(), mentionsTweets = new ArrayList<Status>();

    public static HashMap<String, Image> menuImgCache = new HashMap<>();

    public ContextMenu cm;
    public MenuItem favorite, reply, rt, cancel, viewProfile, delete;


    public WindowTimeline(){
        if(instance == null){
            instance = this;
        }

    }


    public void initContextMenu(){
        cm = new ContextMenu();

        cm.setAutoHide(true);
        cm.setAutoFix(true);

        viewProfile = new MenuItem("View %s's Profile");

        favorite = new MenuItem("Favorite Tweet");
        reply = new MenuItem("Reply to %s");
        reply.setGraphic(new ImageView(new Image("https://si0.twimg.com/images/dev/cms/intents/icons/reply.png")));
        rt = new MenuItem("RT Tweet");
        cancel = new MenuItem("Cancel");

        delete = new MenuItem("Delete Tweet");

        // Populate ContextMenu Caches
        menuImgCache.put("favorite_off", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/favorite.png"));
        menuImgCache.put("favorite_on", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/favorite_on.png"));
        menuImgCache.put("rt_off", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/retweet.png"));
        menuImgCache.put("rt_on", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/retweet_on.png"));

        viewProfile.setOnAction((event) -> {
            Status status = null;
            try {
                status = getSelectedStatus();
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
            stage.setTitle(String.format("@ %s 's Profile", status.getUser().getScreenName()));
            stage.setScene(new Scene(page, 409, 622));
            ((ProfileViewer)loader.getController()).start(status.getUser().getScreenName());
            stage.show();


        });

        favorite.setOnAction((event) -> {
            Action response = Dialogs.create().masthead("Twitter Action...").message("Are you sure you want to favorite this tweet?").showConfirm();
            if(response != Dialog.Actions.YES) return;
            Status status = null;
            try {
                status = getSelectedStatus();
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            if(!status.isFavorited()) {
                try {
                    TwitterContainer.twitter.createFavorite(status.getId());
                } catch (TwitterException e) {
                    Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not create favorite!").showException(e);
                    e.printStackTrace();
                }
            }else{
                try {
                    TwitterContainer.twitter.destroyFavorite(status.getId());
                } catch (TwitterException e) {
                    Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not destroy favorite!").showException(e);

                    e.printStackTrace();
                }
            }
        });

        rt.setOnAction((actionEvent) -> {
            Action response = Dialogs.create().masthead("Twitter Action...").message("Are you sure you want to Retweet this tweet?").showConfirm();
            if(response != Dialog.Actions.YES) return;
            Status status = null;
            try {
                status = getSelectedStatus();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            if(!status.getUser().isProtected()) {
                rt.setDisable(false);
                if (!status.isRetweetedByMe()) {
                    try {
                        TwitterContainer.twitter.retweetStatus(status.getId());
                    } catch (TwitterException e) {
                        Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not create favorite!").showException(e);
                        e.printStackTrace();
                    }
                } else {
                    try {
                        TwitterContainer.twitter.destroyStatus(status.getCurrentUserRetweetId());
                    } catch (TwitterException e) {
                        Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not destroy favorite!").showException(e);

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
                status = getSelectedStatus();
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            reply.setText("Reply to " + status.getUser().getScreenName());
            viewProfile.setText(String.format("View %s's profile", status.getUser().getScreenName()));

            try {
                if(status.getUser().getId() == TwitterContainer.twitter.getId()){
                    delete.setDisable(false);
                }else{
                    delete.setDisable(true);
                }

            } catch (TwitterException e) {
                e.printStackTrace();
            }

            if (!status.isFavorited()) {
                favorite.setGraphic(new ImageView(menuImgCache.get("favorite_off")));
                favorite.setText("Favorite Tweet");
            } else {
                favorite.setGraphic(new ImageView(menuImgCache.get("favorite_on")));

                favorite.setText("Unfavorite Tweet");
            }

            if(!status.isRetweetedByMe()){
                rt.setGraphic(new ImageView(menuImgCache.get("rt_off")));
                rt.setText("RT Tweet");
            }else {
                rt.setGraphic(new ImageView(menuImgCache.get("rt_on")));
                rt.setText("Undo RT");
            }
        });

        reply.setOnAction((event) -> {

            Status status = null;
            try {
                status = getSelectedStatus();
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            cm.hide();
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
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();




        });

        delete.setOnAction((event) -> {
            Status status = null;
            try {
                status = getSelectedStatus();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            try {
                TwitterContainer.twitter.destroyStatus(status.getId());
            } catch (TwitterException e) {
                Dialogs.create().title("Error!").masthead("Twitter Error...").showException(e);
                e.printStackTrace();
            }
        });


        cm.getItems().addAll(viewProfile, new SeparatorMenuItem(), favorite, reply, rt, new SeparatorMenuItem(), cancel, new SeparatorMenuItem(), delete);
        tweetsView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(tweetsView, e.getScreenX(), e.getScreenY());
                }
            }
        });
        mentionsView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(tweetsView, e.getScreenX(), e.getScreenY());
                }
            }
        });
        searchView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(TwitterContainer.searchTweetList.size() > 0){
                    if (e.getButton() == MouseButton.SECONDARY) {
                        cm.show(tweetsView, e.getScreenX(), e.getScreenY());
                    }
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

        searchView.setCellFactory(stringListView -> new TweetListCellRender());


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
            System.out.println(tabPane.getSelectionModel().getSelectedIndex());

        });

        tabPane.setOnMouseClicked((event) -> search());


        initContextMenu();

    }

    public void search(){
        if(tabPane.getSelectionModel().getSelectedIndex() == 2){
            TwitterContainer.searchTweetList.clear();
            String query = Dialogs.create().title("Search").masthead("Perform a Search...").message("Enter a search query!").showTextInput();
            try {
                QueryResult res = TwitterContainer.twitter.search(new Query(query));
                res.getTweets().forEach((tweet) -> TwitterContainer.searchTweetList.add(tweet));
                searchView.setItems(TwitterContainer.searchTweetList);
            } catch (TwitterException e) {
                e.printStackTrace();
                Dialogs.create().title("Error!").masthead("There was an error with your search...").showException(e);
            }

        }

    }
    
    public Status getSelectedStatus() throws TwitterException {

        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case (0): // "Home" tab
                return TwitterContainer.twitter.showStatus(TwitterContainer.homeTweetList.get(tweetsView.getSelectionModel().getSelectedIndex()).getId());
            case (1): // "Mentions" tab
                return TwitterContainer.twitter.showStatus(TwitterContainer.mentionTweetList.get(mentionsView.getSelectionModel().getSelectedIndex()).getId());
            case (2): // "Search" tab
                return TwitterContainer.twitter.showStatus(TwitterContainer.searchTweetList.get(searchView.getSelectionModel().getSelectedIndex()).getId());
        }

        throw new IllegalAccessError("Cannot get Status, invalid Tab selected!");
    }

}
