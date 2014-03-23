package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.javafx.render.TweetListCellRender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileViewer implements Initializable {

    @FXML public ImageView profilePicture;
    @FXML public Label lblUsername, lblWebsite, lblLocation, lblBio, lblFollowers, lblFollows, lblConceiveDate;
    @FXML public CheckBox chckFollow, chckFollowsYou, chckBlocked;
    @FXML public ListView<Status> tweetsList;

    private User user;

    private Relationship relationship;

    public ProfileViewer(){}


    public void start(String userToShow){
        try {
            this.user = TwitterContainer.twitter.showUser(userToShow);
        } catch (TwitterException e) {
            e.printStackTrace();
            Dialogs.create().title("Twitter Error").masthead("User not found").message("Twitter reports the user was not found!").showError();
            ((Stage)profilePicture.getScene().getWindow()).close();
            return;
        }

        try {
            this.relationship = TwitterContainer.twitter.showFriendship(TwitterContainer.twitter.getId(), user.getId());
        } catch (TwitterException e) {
            e.printStackTrace();
            Dialogs.create().masthead("Error!").message("There was an error looking up this user!").showError();
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

        ObservableList<Status> tweets = FXCollections.observableArrayList();
        try {
            for(Status s : TwitterContainer.twitter.getUserTimeline(user.getId())){
                tweets.add(s);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        tweetsList.setItems(tweets);

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
