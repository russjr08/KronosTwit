package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author russjr08
 * Created at 3/22/14
 */
public class WindowNewTweet implements Initializable {

    @FXML Button btnSubmit;
    @FXML TextArea tweetArea;
    @FXML Label lblCharsLeft;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tweetArea.setOnKeyTyped((event) -> {
            lblCharsLeft.setText(String.valueOf(140 - tweetArea.getText().length()));
            if(tweetArea.getText().length() > 140){
                btnSubmit.setDisable(true);
            }else{
                btnSubmit.setDisable(false);
            }
        });
        btnSubmit.setDefaultButton(true);
        Platform.runLater(btnSubmit::requestFocus);

        btnSubmit.setOnMouseClicked((e) -> submitTweet());
    }

    public void setReply(Status reply){
        // Todo: Implement replies.
    }

    public void submitTweet(){
        boolean success = true;
        StatusUpdate update = new StatusUpdate(tweetArea.getText());

        try {
            TwitterContainer.twitter.updateStatus(update);
        } catch (TwitterException e) {
            success = false;
            Dialogs.create().masthead("Twitter Error...").message("There was an error submitting your tweet!");
            e.printStackTrace();
        }

        if(success){
            ((Stage)btnSubmit.getScene().getWindow()).close();
        }
    }
}
