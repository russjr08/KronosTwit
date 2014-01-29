package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.api.internet.ReadURL;
import com.kronosad.projects.twitter.kronostwit.checkers.UpdateInformation;
import com.kronosad.projects.twitter.kronostwit.data.DataDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.Updater;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.helpers.Initializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 7:43 PM
 */
public class WindowLoading extends Application {
    public static Twitter twitter;
    private Stage primaryStage;
    private String consumerKey, consumerSecret;

    private Label status;

    private UpdateInformation updater = new UpdateInformation();

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("WindowLoading.fxml"));
        primaryStage.setTitle("KronosTwit");
        primaryStage.setScene(new Scene(root, 419, 156));
        primaryStage.setResizable(false);
        primaryStage.show();

        status = (Label) root.lookup("#txtStatus");

        new Thread(){
            @Override
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        specialInit();

                    }
                });
            }
        }.start();
    }

    public void specialInit(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new Thread(){
                    @Override
                    public void run() {
                        prepWork();
                    }
                }.start();

            }
        });

    }

    public static void main(String... args){
        launch(args);
    }

    public void setStatus(final String set){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                status.setText(set);

            }
        });
    }

    public void prepWork(){

        try {
            initSecrets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkUpdates();
        setStatus("Downloading Data...");
        DataDownloader.downloadData();

        setStatus("Downloading Resource Data...");

        ResourceDownloader.downloadResources();

        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setJSONStoreEnabled(true);
        cb.setIncludeEntitiesEnabled(true);

        twitter = new TwitterFactory(cb.build()).getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        final WindowTimeline timeline = new WindowTimeline();

        setStatus("Downloading Tweets...");
        new Thread(new Initializer(consumerKey, consumerSecret, this)).run();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    timeline.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void checkUpdates(){
        try {
            setStatus("Checking for Updates...");
            updater.check();
            if(updater.versionNumber < updater.serverVersion){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setStatus("Downloading Updated App...");
                        Updater.update(updater.getInstance(), false, primaryStage);
                        System.exit(1);
                    }
                });
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void initSecrets() throws Exception {
        // This was added for obscurity. Obviously we cannot completely hide this without any kind of obfuscation
        // and encryption, but this works fine :)
        // If someone has our Consumer Key / Secret, they could pretend to be us...

        String baseURL = "http://api.kronosad.com/KronosTwit/twitData/secrets/";
        ReadURL conKey = new ReadURL(baseURL + "consumerKey.txt");
        consumerKey = conKey.read();
        ReadURL conSecret = new ReadURL(baseURL + "consumerSecret.txt");
        consumerSecret = conSecret.read();

    }


}
