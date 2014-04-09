package com.kronosad.projects.twitter.kronostwit.gui.issues;

import com.kronosad.projects.libraries.GitlabAPI;
import com.kronosad.projects.libraries.PastebinAPI;
import com.kronosad.projects.twitter.kronostwit.gui.TwitterContainer;
import com.kronosad.projects.twitter.kronostwit.gui.WindowConsole;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import twitter4j.TwitterException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author russjr08
 * Created at 3/30/14
 */
public class IssueCreator implements Initializable {
    @FXML
    Button btnSubmit;

    @FXML
    TextField txtSubject, txtLabels;

    @FXML
    TextArea txtDetails;

    @Override
    public void initialize(URL u, ResourceBundle resourceBundle) {
        btnSubmit.setOnAction((e) -> {
            Action response = Dialogs.create().title("Warning...").masthead("User Warning...").message("Warning, creating an issue will" +
                    " upload your console log (for this session) to Pastebin, and append your Twitter username to the list of" +
                    " tags/labels, do you want to proceed?").showConfirm();

            if(response == Dialog.Actions.YES){
                boolean success = true;
                PastebinAPI paste = new PastebinAPI("0593b337f2378b464f1d9b04541e333c", "text", WindowConsole.getInstance().getText());
                String url = "";
                try {
                    url = paste.post();
                    System.out.println("Pastebin URL: " + url);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    Dialogs.create().title("Error...").masthead("Pastebin Error...").showException(e1);

                }
                String issueText = txtDetails.getText() + "\n" + url;
                try {
                    int issueID = GitlabAPI.createIssue(2, txtSubject.getText(), issueText, txtLabels.getText() + ", " + TwitterContainer.twitter.getScreenName());
                    if(Desktop.isDesktopSupported()){
                        Desktop.getDesktop().browse(new URL(String.format("http://git.tristen.io/russjr08/kronostwit/issues/%s", issueID)).toURI());
                    }
                } catch (IOException | TwitterException | URISyntaxException e1) {
                    e1.printStackTrace();
                    Dialogs.create().title("Error...").masthead("There was a problem with your request...").showException(e1);
                    success = false;
                }finally{

                    if(success){
                        ((Stage)txtDetails.getScene().getWindow()).close();
                    }

                }
            }
        });
    }
}
