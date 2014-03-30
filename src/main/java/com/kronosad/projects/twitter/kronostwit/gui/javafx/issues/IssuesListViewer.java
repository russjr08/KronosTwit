package com.kronosad.projects.twitter.kronostwit.gui.javafx.issues;

import com.kronosad.projects.libraries.GitlabAPI;
import com.kronosad.projects.libraries.json.Issue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Author russjr08
 * Created at 3/29/14
 */
public class IssuesListViewer implements Initializable {

    @FXML TableView issuesTable;
    @FXML MenuBar menu;

    @FXML TableColumn<Issue, Integer> idCol;
    @FXML TableColumn<Issue, String> titleCol;
    @FXML TableColumn<Issue, String> timeCol;

    private List<Issue> issues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {
                this.issues = GitlabAPI.getProjectIssues(2).getIssues();
                for(int i = 0; i < this.issues.size(); i++){
                    Issue issue = this.issues.get(i);
                    System.out.println(issue.getState());
                    if(issue.getState().equalsIgnoreCase("closed")){
                        this.issues.remove(i);
                    }
                }
                this.issuesTable.setItems(FXCollections.observableList(this.issues));

            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.create().title("Error...").masthead("API Error...").showException(e);
            }
        }).start();

        idCol.setCellValueFactory(new PropertyValueFactory<>("iid"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("updated_at"));


    }
}
