package com.kronosad.projects.twitter.kronostwit.console;

import com.kronosad.api.internet.ReadURL;
import com.kronosad.projects.twitter.kronostwit.checkers.CheckerBetaUser;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleMain {

    public static Twitter twitter;
    public static RequestToken requestToken;
    public static AccessToken accessToken;
    public static Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    public static Scanner scanner;
    public static String pin;
    public static Properties prop;
    public static String consumerKey, consumerSecret;


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        scanner = new Scanner(System.in);
        prop = new Properties();

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KronosTwit - Beta");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


        try {
            System.out.println("Connecting to server for secret consumer data...");
            initSecrets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: Could not get consumer API data from server!\n Without the consumer" +
                    " data, we cannot continue!\n Please verify your internet connectivity and restart this application. \n" +
                    "The KronosAD server may also be down...", "Could not connect to KronosAD"
                    , JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception hidden.");

            System.exit(1);
        }finally {
            System.out.println("Connection successful! We are now ready to talk to Twitter's servers!");
        }
        twitter = TwitterFactory.getSingleton();
        File twitter4JFile = new File("twitter4j.properties");
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        if(!twitter4JFile.exists()){
            System.out.println("No previous token found! Executing new user setup!");
            getNewUserTokenGUI();
            System.out.println("All other authentication data for this app has been invalidated, in case you're running this " +
                    "app from another folder, you will need to delete the twitter4j.properties file in those other" +
                    "folders.");
        }else{
            System.out.println("Authorization Details located! Using those!");
        }

        String betaKey = CheckerBetaUser.getBetaKeyProperties();

        if(betaKey == null){
            betaKey = JOptionPane.showInputDialog("A previous Beta Key was not found. Please enter your Beta Key.");

        }else{
            System.out.println("Previous Beta Key found! Using those!");
        }


        try {
            if(CheckerBetaUser.isBetaUser(betaKey, twitter.getScreenName())){
                //mainMenu(); Use this if you want to use the console based version of the app.
                MainGUI gui = new MainGUI();
                CheckerBetaUser.setBetaKeyProperties(betaKey);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            System.exit(1);
        }




    }

    public static void getNewUserToken(){
        try {
            requestToken = twitter.getOAuthRequestToken();
            desktop.browse(URI.create(requestToken.getAuthenticationURL()));


        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter the pin that was given to you after authentication: ");

        pin = JOptionPane.showInputDialog("Please Input the PIN after you have authenticated with Twitter.");



        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            saveAccessToken(accessToken);

        } catch (TwitterException e) {
            e.printStackTrace();
        }


    }

    public static void getNewUserTokenGUI(){
        try {
            requestToken = twitter.getOAuthRequestToken();
            desktop.browse(URI.create(requestToken.getAuthenticationURL()));


        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter the pin that was given to you after authentication: ");

        pin = JOptionPane.showInputDialog("Please Input the PIN after you have authenticated with Twitter.");



        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            saveAccessToken(accessToken);

        } catch (TwitterException e) {
            e.printStackTrace();
        }


    }


    public static void saveAccessToken(AccessToken token){
        prop.setProperty("oauth.accessToken", token.getToken());
        prop.setProperty("oauth.accessTokenSecret", token.getTokenSecret());
        //prop.setProperty("debug", "true"); Do you want debug data?
        try {
            prop.store(new FileOutputStream("twitter4j.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void printHomeTimeline(){
        try {
            for(Status status : twitter.getHomeTimeline()){
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public static void printMentions(){
        try {
            for(Status status : twitter.getMentionsTimeline()){
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public static void printDirectMessages(){
        try {
            for(DirectMessage message : twitter.getDirectMessages()){
                System.out.println(String.format("[%s]%s: %s", message.getCreatedAt(), message.getSenderScreenName(), message.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public static void tweet(){
        String newTweet = null;
        System.out.print("What would you like to tweet: ");
        while(newTweet == null){
            newTweet = scanner.nextLine();
            if(newTweet.length() > 150){
                System.out.println("Too many characters!");
                newTweet = null;
                tweet();
            }
        }
        //TODO: Cleanup this unneeded code.
        if(newTweet != null){
            try {
                twitter.updateStatus(newTweet);
                //TODO: Fix this..
                System.out.println("Tweeted: " + twitter.getUserTimeline().get(1).getText());
            } catch (TwitterException e) {
                System.out.println("ERROR!");
                e.printStackTrace();
            }
        }
        mainMenu();
    }

    public static void initSecrets() throws Exception{
        // This was added for obscurity. Obviously we cannot completely hide this without any kind of obfuscation
        // and encryption, but this works fine :)
        // If someone has our Consumer Key / Secret, they could pretend to be us...

        String baseURL = "http://api.kronosad.com/KronosTwit/twitData/secrets/";
        ReadURL conKey = new ReadURL(baseURL + "consumerKey.txt");
        consumerKey = conKey.read();
        ReadURL conSecret = new ReadURL(baseURL + "consumerSecret.txt");
        consumerSecret = conSecret.read();

    }

    public static void mainMenu(){
        System.out.println("---------- KronosTwit Main Menu ---------");
        System.out.println("What would you like to do?");
        System.out.println("1: Tweet");
        System.out.println("2: Print Timeline");
        System.out.println("3: Print Mentions");
        System.out.println("4: Print Direct Messages");
        System.out.println("5: Exit!");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice){
            case 1:
                tweet();
            case 2:
                printHomeTimeline();
            case 3:
                printMentions();
            case 4:
                printDirectMessages();
            case 5:
                System.exit(0);
            default:
                mainMenu();
        }
    }

}
