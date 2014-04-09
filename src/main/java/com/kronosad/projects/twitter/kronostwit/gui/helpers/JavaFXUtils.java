package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.gui.WindowTimeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 8:08 PM
 */
public class JavaFXUtils {

    public static void showNewWindow(String xml, Stage stage){
        try {
            Parent root = FXMLLoader.load(WindowTimeline.class.getResource(xml));
            Scene scene = new Scene(root, root.getLayoutX(), root.getLayoutY());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
