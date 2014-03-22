package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.api.internet.ReadURL;
import com.kronosad.projects.twitter.kronostwit.checkers.UpdateInformation;
import com.kronosad.projects.twitter.kronostwit.data.DataDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.Updater;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.helpers.Initializer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 7:43 PM
 */
public class WindowLoading implements Initializable{
    public static Twitter twitter;

    private Stage stage;

    private String consumerKey, consumerSecret;

    @FXML protected AnchorPane anchorPane;
    @FXML protected WebView loginWebView;

    @FXML
    protected Label status;

    private UpdateInformation updater = new UpdateInformation();

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

        if(!new File("twitter4j.properties").exists()){
            System.out.println("No credentials found! Starting new user setup...");

            try {
                openBrowserToTwitter(twitter.getOAuthRequestToken().getAuthenticationURL());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }else{
            setStatus("Downloading Tweets...");
            new WindowTimeline();
            new Thread(new Initializer(consumerKey, consumerSecret, this)).run();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        AppStarter.getInstance().switchWindow("WindowTimeline.fxml", 518 ,650);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        TwitterContainer.twitter = twitter;



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
                        Updater.update(updater.getInstance(), false, stage);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void start(Stage stage){
        this.stage = stage;
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

    public void openBrowserToTwitter(final String url){

        Platform.runLater(() -> {
            anchorPane.setPrefSize(767, 630);
            loginWebView.setVisible(true);
            stage.setWidth(767);
            stage.setHeight(630);
            stage.centerOnScreen();
            loginWebView.getEngine().load(url);
            loginWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State state2) {
                    if(state2 == Worker.State.SUCCEEDED){
                        if(loginWebView.getEngine().getLocation().equals("https://api.twitter.com/oauth/authorize")){
                            String html = xmlToString(loginWebView.getEngine().getDocument());
                            org.jsoup.nodes.Document doc = Jsoup.parse(html);
                            Elements elements = doc.select("code");
                            for(Element element : elements){
                                System.out.println(element.toString());
                                System.out.println(element.text());
                                try {
                                    saveAccessToken(twitter.getOAuthAccessToken(element.text()));
                                    setStatus("Downloading Tweets...");
                                    new WindowTimeline();
                                    new Thread(new Initializer(WindowLoading.this.consumerKey, WindowLoading.this.consumerSecret, WindowLoading.this)).run();
                                    AppStarter.getInstance().switchWindow("WindowTimeline.fxml", 518 ,650);

                                } catch (TwitterException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }
            });
        });

    }

    private static void saveAccessToken(AccessToken token) {
        Properties prop = new Properties();
        prop.setProperty("oauth.accessToken", token.getToken());
        prop.setProperty("oauth.accessTokenSecret", token.getTokenSecret());
        prop.setProperty("includeEntities", "true");
        prop.setProperty("jsonStoreEnabled", "true");

        try {
            prop.store(new FileOutputStream("twitter4j.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String xmlToString(Document node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
