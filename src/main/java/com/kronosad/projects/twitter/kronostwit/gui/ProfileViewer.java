package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.render.TweetListCellRender;
import com.kronosad.projects.twitter.kronostwit.user.UserRegistry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import twitter4j.Relationship;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileViewer implements Initializable {

    @FXML public ImageView profilePicture;
    @FXML public Label lblUsername, lblWebsite, lblLocation, lblBio, lblFollowers, lblFollows, lblConceiveDate;
    @FXML public CheckBox chckFollow, chckFollowsYou, chckBlocked;
    @FXML public ListView<Status> tweetsList;
    @FXML public ImageView lblStar;

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

    // Blank method, shouldn't really be called by itself.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
