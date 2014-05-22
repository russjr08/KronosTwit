package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.gui.components.*;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.render.TweetListCellRender;
import com.kronosad.projects.twitter.kronostwit.interfaces.ITweetReceptor;
import com.kronosad.projects.twitter.kronostwit.user.UserRegistry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import twitter4j.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileViewer implements Initializable, ITweetReceptor {

    @FXML public ImageView profilePicture;
    @FXML public Label lblUsername, lblWebsite, lblLocation, lblBio, lblFollowers, lblFollows, lblConceiveDate;
    @FXML public CheckBox chckFollow, chckFollowsYou, chckBlocked;
    @FXML public ListView<Status> tweetsList;
    @FXML public ImageView lblStar;

    public ContextMenu cm;
    public com.kronosad.projects.twitter.kronostwit.gui.components.CustomMenuItem favorite, reply, rt, viewProfile;
    public MenuItem cancel, delete;

    private User user;

    private Relationship relationship;

    public ProfileViewer(){}


    /**
     * Call this method to set the profile that this Viewer will view.
     * @param userToShow The user that you want the Viewer to view.
     */
    public void start(String userToShow){

        Platform.runLater(() -> {
            try {
                this.user = TwitterContainer.twitter.showUser(userToShow);
            } catch (TwitterException e) {
                e.printStackTrace();
                Dialogs.create().title("Twitter Error...").masthead("User not found!").showException(e);
                ((Stage)profilePicture.getScene().getWindow()).close();
                return;
            }

            try {
                this.relationship = TwitterContainer.twitter.showFriendship(TwitterContainer.twitter.getId(), user.getId());
            } catch (TwitterException e) {
                e.printStackTrace();
                Dialogs.create().title("Twitter Error...").masthead("Error!").showException(e);
                ((Stage)profilePicture.getScene().getWindow()).close();
                return;
            }

            profilePicture.setImage(new Image(user.getProfileImageURL()));
            lblUsername.setText(user.getScreenName());
            lblWebsite.setText(user.getURL());
            lblLocation.setText(user.getLocation());
            lblBio.setText(user.getDescription());
            lblFollowers.setText(String.valueOf(user.getFollowersCount()));
            lblFollows.setText(String.valueOf(user.getFriendsCount()));
            lblConceiveDate.setText(user.getCreatedAt().toString());

            if(relationship.isSourceFollowingTarget()){
                chckFollow.setSelected(true);
            }
            if(relationship.isSourceFollowedByTarget()){
                chckFollowsYou.setSelected(true);
            }
            if(relationship.isSourceBlockingTarget()){
                chckBlocked.setSelected(true);
            }

            try {
                if(user.getId() == TwitterContainer.twitter.getId()){
                    chckFollow.setDisable(true);
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            try {
                tweetsList.setItems(FXCollections.observableList(TwitterContainer.twitter.getUserTimeline(user.getId())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            tweetsList.setCellFactory(stringListView -> new TweetListCellRender());

            chckFollow.setOnAction((actionEvent) -> {
                if(chckFollow.isSelected()){
                    try {
                        TwitterContainer.twitter.createFriendship(user.getId());
                    } catch (TwitterException e) {
                        e.printStackTrace();
                        Dialogs.create().masthead("Twitter Error").message("Error following user!").showWarning();
                    }
                }else{
                    try {
                        TwitterContainer.twitter.destroyFriendship(user.getId());
                    } catch (TwitterException e) {
                        e.printStackTrace();
                        Dialogs.create().masthead("Twitter Error").message("Error unfollowing user!").showWarning();


                    }
                }
            });

            applyStar();
            initContextMenu();
        });


    }

    public void applyStar(){
        if(UserRegistry.getKronosUser(user.getScreenName()).isBetaUser()){
            try {
                lblStar.setImage(new Image(new FileInputStream(ResourceDownloader.getResource("beta_user.png"))));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // This shouldn't occur, but we'll print the error anyways. The user doesn't need to know about it though.
            }
        }
        if(user.isVerified()){
            try {
                lblStar.setImage(new Image(new FileInputStream(ResourceDownloader.getResource("verified_account.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // This shouldn't occur, but we'll print the error anyways. The user doesn't need to know about it though.
            }
        }
    }

    public void initContextMenu(){
        cm = new ContextMenu();

        cm.setAutoHide(true);
        cm.setAutoFix(true);

        viewProfile = new ProfileMenuItem(this);

        favorite = new FavoriteMenuItem(this);
        reply = new ReplyMenuItem(this);
        reply.setGraphic(new ImageView(new Image("https://si0.twimg.com/images/dev/cms/intents/icons/reply.png")));
        rt = new com.kronosad.projects.twitter.kronostwit.gui.components.RTMenuItem(this);
        cancel = new MenuItem("Cancel");

        delete = new MenuItem("Delete Tweet");

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
                favorite.setGraphic(new ImageView(WindowTimeline.menuImgCache.get("favorite_off")));
                favorite.setText("Favorite Tweet");
            } else {
                favorite.setGraphic(new ImageView(WindowTimeline.menuImgCache.get("favorite_on")));

                favorite.setText("Unfavorite Tweet");
            }

            if(!status.isRetweetedByMe()){
                rt.setGraphic(new ImageView(WindowTimeline.menuImgCache.get("rt_off")));
                rt.setText("RT Tweet");
            }else {
                rt.setGraphic(new ImageView(WindowTimeline.menuImgCache.get("rt_on")));
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

        tweetsList.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY){
                    cm.show(tweetsList, event.getScreenX(), event.getScreenY());
                }
            }
        });
    }
    
    // Blank method, shouldn't really be called by itself.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public Status getSelectedStatus() throws TwitterException {
        Status status = TwitterContainer.twitter.showStatus(tweetsList.getItems().get(tweetsList.getSelectionModel().getSelectedIndex()).getId());
        return status;
    }
}
