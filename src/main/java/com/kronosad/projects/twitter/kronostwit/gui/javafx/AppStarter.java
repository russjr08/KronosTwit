package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class AppStarter extends Application {
    public Stage stage;
    public Parent currentParent;
    private static AppStarter instance;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WindowLoading.fxml"));
        Parent root = (Parent)loader.load();
        ((WindowLoading)loader.getController()).start(stage);
        stage.setTitle("KronosTwit");
        stage.setScene(new Scene(root, 419, 156));
        stage.setResizable(true);
        stage.show();

        this.instance = this;
    }

    public static void main(String... args){
        launch(args);
    }

    public static AppStarter getInstance(){
        return instance;
    }

    public Parent switchWindow(String fxml, double width, double height){

        Parent page = null;
        try {
            page = FXMLLoader.load(AppStarter.class.getResource(fxml), null, new JavaFXBuilderFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, width, height);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent windowEvent) {
//
//            }
//        }); POST JAVA 8 SOLUTION

        stage.setOnCloseRequest((windowEvent) -> {
            Platform.exit();
            if(TwitterContainer.stream != null){
                stage.close();
                System.exit(0);
            }
        });
//        stage.sizeToScene();
        this.currentParent = page;
        return page;

    }

    public Parent openWindow(String fxml, double width, double height, String title){
        Parent page = null;
        try {
            page = (Parent) FXMLLoader.load(AppStarter.class.getResource(fxml), null, new JavaFXBuilderFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(page, width, height));
        return page;
    }

}
