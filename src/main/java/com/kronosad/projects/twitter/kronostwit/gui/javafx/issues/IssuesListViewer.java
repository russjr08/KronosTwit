package com.kronosad.projects.twitter.kronostwit.gui.javafx.issues;

import com.kronosad.projects.libraries.GitlabAPI;
import com.kronosad.projects.libraries.json.Issue;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.AppStarter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Author russjr08
 * Created at 3/29/14
 */
public class IssuesListViewer implements Initializable {

    @FXML TableView<Issue> issuesTable;
    @FXML MenuBar menu;
    @FXML MenuItem createIssue;

    @FXML TableColumn<Issue, Integer> idCol;
    @FXML TableColumn<Issue, String> titleCol;
    @FXML TableColumn<Issue, String> timeCol;

    private List<Issue> issues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu.setUseSystemMenuBar(true);
        try {
            this.issues = GitlabAPI.getProjectIssues(2).getIssues();
            ArrayList<Issue> toRemove = new ArrayList<>();

            for(Issue issue : this.issues){
                if(issue.getState().equalsIgnoreCase("closed")){
                    toRemove.add(issue);
                }
            }

            issues.removeAll(toRemove);
            this.issuesTable.setItems(FXCollections.observableList(this.issues));

        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.create().title("Error...").masthead("API Error...").showException(e);
        }


        idCol.setCellValueFactory(new PropertyValueFactory<>("iid"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("updated_at"));

        createIssue.setOnAction((e) -> {
            AppStarter.getInstance().openWindow("issues/IssueCreator.fxml", 600, 238, "Create a New Issue", false);
        });



    }
}
