package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;


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

    public Parent switchWindow(String fxml, double width, double height, String title){

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
        stage.setTitle(title);

        stage.setOnCloseRequest((windowEvent) -> {
            System.out.println("Someone's shutting me down! :(");
            Platform.exit();
            if(TwitterContainer.stream != null){
                stage.close();
                TwitterContainer.stream.shutdown();
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

    public String getTitleOfTheLaunch(){
        User user = null;
        try {
            user = TwitterContainer.twitter.showUser(TwitterContainer.twitter.getId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        File file = ResourceDownloader.getResource("greetings.txt");

        List<String> lines = null;
        try {
            lines = FileUtils.readLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Random random = new Random();

        int randomGreeting = random.nextInt(lines.size());

        return (lines.get(randomGreeting).replaceAll("%u", user.getName()));
    }

}
