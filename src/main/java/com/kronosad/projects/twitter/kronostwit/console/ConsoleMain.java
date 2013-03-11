package com.kronosad.projects.twitter.kronostwit.console;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleMain {

    private static Twitter twitter;
    private static RequestToken requestToken;
    private static AccessToken accessToken;
    private static Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    private static Scanner scanner;
    private static String pin;
    private static Properties prop;


    public static void main(String[] args){
        scanner = new Scanner(System.in);
        prop = new Properties();

        twitter = TwitterFactory.getSingleton();
        File twitter4JFile = new File("twitter4j.properties");
        if(!twitter4JFile.exists()){
            System.out.println("No previous token found! Executing new user setup!");
            twitter.setOAuthConsumer("6WbMFNhhP9hOBBqrcWfT8g", "hzQNEWw46AKgCypV7XkrZ2pWISrwtINh1Pv0Jc");
            getNewUserToken();
        }else{
            System.out.println("Authorization Details located! Using those!");
        }
//        try{
//            accessToken = getPreviousUserToken();
//        }catch(NullPointerException exception){
//            System.out.println("No previous token found! Executing new user setup!");
//            getNewUserToken();
//
//        }

        mainMenu();


    }

    private static void getNewUserToken(){
        try {
            requestToken = twitter.getOAuthRequestToken();
            desktop.browse(URI.create(requestToken.getAuthenticationURL()));

        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter the pin that was given to you after authentication: ");

        pin = scanner.nextLine();

        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            saveAccessToken(accessToken);

        } catch (TwitterException e) {
            e.printStackTrace();
        }


    }

//    private static AccessToken getPreviousUserToken(){
//        try {
//            prop.load(new FileInputStream("config.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new AccessToken(prop.getProperty("token"), prop.getProperty("tokenSecret"));
//
//    }

    private static void saveAccessToken(AccessToken token){
        prop.setProperty("oauth.accessToken", token.getToken());
        prop.setProperty("oauth.accessTokenSecret", token.getTokenSecret());
        //prop.setProperty("debug", "true");
        prop.setProperty("oauth.consumerKey", "6WbMFNhhP9hOBBqrcWfT8g");
        prop.setProperty("oauth.consumerSecret", "hzQNEWw46AKgCypV7XkrZ2pWISrwtINh1Pv0Jc");
        try {
            prop.store(new FileOutputStream("twitter4j.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void printHomeTimeline(){
        try {
            for(Status status : twitter.getHomeTimeline()){
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    private static void printMentions(){
        try {
            for(Status status : twitter.getMentionsTimeline()){
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    private static void printDirectMessages(){
        try {
            for(DirectMessage message : twitter.getDirectMessages()){
                System.out.println(String.format("[%s]%s: %s", message.getCreatedAt(), message.getSenderScreenName(), message.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    private static void tweet(){
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

                System.out.println("Tweeted: " + twitter.getUserTimeline().get(1).getText());
            } catch (TwitterException e) {
                System.out.println("ERROR!");
                e.printStackTrace();
            }
        }
        mainMenu();
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
