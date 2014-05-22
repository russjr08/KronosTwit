package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.gui.components.*;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetHelper;
import com.kronosad.projects.twitter.kronostwit.gui.render.TweetListCellRender;
import com.kronosad.projects.twitter.kronostwit.interfaces.ITweetReceptor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import twitter4j.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
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
public class WindowTimeline implements Initializable, ITweetReceptor {

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

    @FXML public MenuItem btnChangelogs;

    @FXML public MenuItem btnIssues;

    @FXML public MenuItem btnInvalidateCaches;

    public ArrayList<Status> homeTweets = new ArrayList<Status>(), mentionsTweets = new ArrayList<Status>();

    public static HashMap<String, Image> menuImgCache = new HashMap<String, Image>();

    public ContextMenu cm;
    public com.kronosad.projects.twitter.kronostwit.gui.components.CustomMenuItem favorite, reply, rt, viewProfile;
    public MenuItem cancel, delete;


    public WindowTimeline(){
        if(instance == null){
            instance = this;
        }

    }


    public void initContextMenu(){
        cm = new ContextMenu();

        cm.setAutoHide(true);
//        cm.setAutoFix(true);

        viewProfile = new ProfileMenuItem(this);

        favorite = new FavoriteMenuItem(this);
        reply = new ReplyMenuItem(this);
        reply.setGraphic(new ImageView(new Image("https://si0.twimg.com/images/dev/cms/intents/icons/reply.png")));
        rt = new RTMenuItem(this);
        cancel = new MenuItem("Cancel");

        delete = new MenuItem("Delete Tweet");

        // Populate ContextMenu Caches
        menuImgCache.put("favorite_off", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/favorite.png"));
        menuImgCache.put("favorite_on", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/favorite_on.png"));
        menuImgCache.put("rt_off", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/retweet.png"));
        menuImgCache.put("rt_on", new Image("https://si0.twimg.com/images/dev/cms/intents/icons/retweet_on.png"));





        cm.setOnShowing((event) -> {
//            new Thread(() -> {}){}.start();
            Status status = null;
            try {
                status = getSelectedStatus();
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < cm.getItems().size(); i++){
                MenuItem item = cm.getItems().get(i);
                if(item instanceof LinkMenuItem){
                    LinkMenuItem li = (LinkMenuItem) item;
                    cm.getItems().removeAll(item);

                }
            }

            ArrayList<String> links = new ArrayList<String>();
            for(URLEntity entity : status.getURLEntities()){
                if(links.contains(entity.getExpandedURL())) return;
                cm.getItems().add(new LinkMenuItem(entity.getExpandedURL(), LinkMenuItem.LinkType.NORMAL));
                links.add(entity.getExpandedURL());
            }
            for(MediaEntity mediaEntity : status.getMediaEntities()){
                if(links.contains(mediaEntity.getMediaURL())) return;
                cm.getItems().add(new LinkMenuItem(mediaEntity.getMediaURL(), LinkMenuItem.LinkType.PICTURE));
                links.add(mediaEntity.getMediaURL());
            }

            for(String word : status.getText().split(" ")){
                if(word.startsWith("/r/")){
                    if(links.contains(word)) return;
                    links.add(word);
                    cm.getItems().add(new LinkMenuItem(word, LinkMenuItem.LinkType.REDDIT));
                }else if(word.startsWith("r/")){
                    word = "/" + word;
                    if(links.contains(word)) return;
                    links.add(word);
                    cm.getItems().add(new LinkMenuItem(word, LinkMenuItem.LinkType.REDDIT));
                }
            }


            for(int i = 0; i < cm.getItems().size(); i++){
                MenuItem item = cm.getItems().get(i);
                if(item instanceof LinkMenuItem){
                    LinkMenuItem li = (LinkMenuItem) item;
                    if(!links.contains(li.getLink())){
                        cm.getItems().removeAll(item);
                    }
                }
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

        });

        tabPane.setOnMouseClicked((event) -> search());

        btnChangelogs.setOnAction((e) -> {
            if(Desktop.isDesktopSupported()){
                try {
                    Desktop.getDesktop().browse(new URL("http://git.tristen.io/russjr08/kronostwit/wikis/changelogs").toURI());
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnIssues.setOnAction((e) -> AppStarter.getInstance().openWindow("issues/IssueListViewer.fxml", 600, 400, "Issues"));
        btnInvalidateCaches.setOnAction((e) -> TwitterContainer.imgCache.clear());
        initContextMenu();

    }

    public void done(WindowLoading loading){
        loading.setStatus("Precaching Images...");
        for(Status status : TwitterContainer.homeTweetList){
            if(!menuImgCache.containsKey(status.getUser().getScreenName())){
                menuImgCache.put(status.getUser().getScreenName(), new Image(status.getUser().getProfileImageURL()));
            }
        }
    }

    public void search(){
        if(tabPane.getSelectionModel().getSelectedIndex() == 2){
            TwitterContainer.searchTweetList.clear();
            String query = Dialogs.create().title("Search").masthead("Perform a Search...").message("Enter a search query!").showTextInput();
            try {
                QueryResult res = TwitterContainer.twitter.search(new Query(query));
                res.getTweets().forEach((tweet) -> TwitterContainer.searchTweetList.add(TweetHelper.removeTwitterLinks(tweet)));
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
