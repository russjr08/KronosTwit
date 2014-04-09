package com.kronosad.projects.twitter.kronostwit.gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Author russjr08
 * Created at 3/22/14
 */
public class WindowNewTweet implements Initializable {

    @FXML Button btnSubmit;
    @FXML TextArea tweetArea;
    @FXML Label lblCharsLeft;

    File attachment;
    Status reply;


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

        tweetArea.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        tweetArea.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    if(db.getFiles().size() != 1){
                        System.out.println("Invalid amount of files!");
                        Dialogs.create().title("Drag-N-Drop").masthead("Attachment Error...").message("Please only drag ONE file!").showWarning();
                        return;
                    }
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        System.out.println("Attaching: " + filePath);
                        attachment = file;
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    public void setReply(Status reply){
        System.out.println("Setting reply!");
        this.reply = reply;
        ArrayList<String> users = new ArrayList<>();
        for(String word : reply.getText().split(" ")){
            if(word.startsWith("@")){
                try {
                    if(!word.equals(TwitterContainer.twitter.getScreenName())){
                        users.add(word);
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!users.contains("@" + reply.getUser().getScreenName())){
            users.add("@" + reply.getUser().getScreenName());
        }
        System.out.println("Users found: " + users.size());
        String joined = String.join(" ", users);
        presetText(joined);
    }

    public void submitTweet(){
        boolean success = true;
        StatusUpdate update = new StatusUpdate(tweetArea.getText());

        if(attachment != null){
            update.setMedia(attachment);
        }

        if(reply != null){
            update.setInReplyToStatusId(reply.getId());
        }

        try {
            TwitterContainer.twitter.updateStatus(update);
        } catch (TwitterException e) {
            success = false;
            Dialogs.create().masthead("Twitter Error...").message("There was an error submitting your tweet!").showException(e);
            e.printStackTrace();
        }

        if(success){
            ((Stage)btnSubmit.getScene().getWindow()).close();
        }
    }

    public void presetText(String text){
        this.tweetArea.setText(text);
        this.tweetArea.requestFocus();
    }
}
