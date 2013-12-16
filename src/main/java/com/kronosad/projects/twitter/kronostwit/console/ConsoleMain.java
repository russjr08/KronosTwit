package com.kronosad.projects.twitter.kronostwit.console;

import com.kronosad.api.internet.ReadURL;
import com.kronosad.projects.twitter.kronostwit.checkers.CheckerBetaUser;
import com.kronosad.projects.twitter.kronostwit.checkers.CheckerUpdate;
import com.kronosad.projects.twitter.kronostwit.commands.CommandRegistry;
import com.kronosad.projects.twitter.kronostwit.commands.CommandUnshortenURL;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.Updater;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowLoadingScreen;
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
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleMain {

    @Deprecated
    public static final String[] BETA_USERS = {"russjr08", "trisam889"};
    public static Twitter twitter;
    public static RequestToken requestToken;
    public static AccessToken accessToken;
    public static Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    public static Scanner scanner;
    public static String pin;
    public static Properties prop;
    public static String consumerKey, consumerSecret;
    public static WindowLoadingScreen loading = new WindowLoadingScreen("Loading Application", 50, 50);
    public static ArrayList<String> arguments = new ArrayList<String>();

    public static void load(String[] args/*, boolean problems*/) {
//        if(problems){
//            JOptionPane.showMessageDialog(null, "Error Grabbing Application Data, terminating!", "Error Downloading Data",
//                    JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        }

        File jar = new File(ConsoleMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("This jar's file name: " + jar.getName());


        for (String argument : args) {
            arguments.add(argument);
        }
        while (loading.isUpdating) {

        }

        if (!arguments.contains("-development")) {
            if (!jar.getName().equals("KronosTwit.jar")) {
                JOptionPane.showMessageDialog(null, "Hey! My jar file name is not KronosTwit.jar! Please rename "
                        + "me next time I am closed! My update mechanism expects my file name to be KronosTwit.jar!",
                        "Unsafe file name detected", JOptionPane.WARNING_MESSAGE);
            }

        }

        if (arguments.contains("-updated")) {
            JOptionPane.showMessageDialog(null, String.format("Congrats! KronosTwit has been automatically updated to %s!", ConsoleLoader.updater.version), "Update complete!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (arguments.contains("-forceupdate")) {
            Updater.update(ConsoleLoader.updater, false);
        }

        while (loading.isDownloadingData) {

        }


        scanner = new Scanner(System.in);
        prop = new Properties();


        try {
            System.out.println("Connecting to server for secret consumer data...");
            loading.grabbingData();
            initSecrets();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: Could not get consumer API data from server!\n Without the consumer" +
                    " data, we cannot continue!\n Please verify your internet connectivity and restart this application. \n" +
                    "The KronosAD server may also be down...", "Could not connect to KronosAD"
                    , JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception hidden.");

            System.exit(1);
        } finally {
            System.out.println("Connection successful! We are now ready to talk to Twitter's servers!");
        }

        loading.checkUpdate();

        ConsoleLoader.updater = new CheckerUpdate();
        try {
            ConsoleLoader.updater.check();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to check for updates!", "Update Check Failed", JOptionPane.WARNING_MESSAGE);
        } finally {
            System.out.println("Final Versions: ");
            System.out.println("Client: " + ConsoleLoader.updater.version);
            System.out.println("Server: " + ConsoleLoader.updater.serverVersion);
            if (ConsoleLoader.updater.serverVersion > ConsoleLoader.updater.version) {

                System.out.println("WARNING: Starting update procedures!");
                Updater.update(ConsoleLoader.updater, false);

                System.exit(1);
            } else if (ConsoleLoader.updater.serverVersion < ConsoleLoader.updater.version) {
                JOptionPane.showMessageDialog(null, "Your Version of KronosTwit has a higher version than the one detected on the server, \n"
                        + "this probably means you are running a BETA copy. Please proceed with caution!", "Version Mismatch Detected!", JOptionPane.WARNING_MESSAGE);

            }
        }


        twitter = TwitterFactory.getSingleton();
        File twitter4JFile = new File("twitter4j.properties");


        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        if (!twitter4JFile.exists()) {
            System.out.println("No previous token found! Executing new user setup!");
            getNewUserTokenGUI();
            System.out.println("All other authentication data for this app has been invalidated, in case you're running this " +
                    "app from another folder, you will need to delete the twitter4j.properties file in those other" +
                    "folders.");
        } else {
            System.out.println("Authorization Details located! Using those!");
        }

        System.out.println("Checking rate limit status...");

        try {
            Map<String, RateLimitStatus> stats = twitter.getRateLimitStatus();
            RateLimitStatus status = stats.get("/application/rate_limit_status");
            System.out.println(status.toString());
            if (status.getRemaining() <= 5) {
                JOptionPane.showMessageDialog(null, String.format("Sorry! You need to wait %s seconds before you can open this app again!", status.getSecondsUntilReset()));
                System.exit(0);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }


        //String betaKey = CheckerBetaUser.getBetaKeyProperties();
        loading.checkUser();

//            if(betaKey == null){
//                betaKey = JOptionPane.showInputDialog("A previous Beta Key was not found. Please enter your Beta Key.");
//
//            }else{
//                System.out.println("Previous Beta Key found! Using those!");
//                
//            }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
//                if(CheckerBetaUser.isBetaUser(betaKey, twitter.getScreenName())){
//                    //mainMenu(); Use this if you want to use the console based version of the app.
//                    //MainGUI gui = new MainGUI();
//                    CheckerBetaUser.setBetaKeyProperties(betaKey);
//                    
//                }

            CheckerBetaUser.isBetaUser(twitter.getScreenName());
        } catch (TwitterException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {

            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (!arguments.contains("-console")) {
                        registerCommands();
                        new WindowViewTimeline("View Timeline", 500, 600);
                        loading.loadingTweets();
                    } else {
                        System.out.println("All GUI methods halted! You are now running in Console Only mode!");
                        mainMenu();
                    }
                }

            });
//                loading.done();


        }


    }

    public static void registerCommands(){
        CommandRegistry.registerCommand(new CommandUnshortenURL());
    }

    public static void getNewUserToken() {
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

    public static void getNewUserTokenGUI() {
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

    public static void saveAccessToken(AccessToken token) {
        prop.setProperty("oauth.accessToken", token.getToken());
        prop.setProperty("oauth.accessTokenSecret", token.getTokenSecret());
        prop.setProperty("includeEntities", "true");
        //prop.setProperty("debug", "true"); Do you want debug data?
        try {
            prop.store(new FileOutputStream("twitter4j.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void printHomeTimeline() {
        try {
            for (Status status : twitter.getHomeTimeline(new Paging(1, 100))) {
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public static void printMentions() {
        try {
            for (Status status : twitter.getMentionsTimeline()) {
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public static void printDirectMessages() {
        try {
            for (DirectMessage message : twitter.getDirectMessages()) {
                System.out.println(String.format("[%s]%s: %s", message.getCreatedAt(), message.getSenderScreenName(), message.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public static void tweet() {
        String newTweet = null;
        System.out.print("What would you like to tweet: ");
        while (newTweet == null) {
            newTweet = scanner.nextLine();
            if (newTweet.length() > 150) {
                System.out.println("Too many characters!");
                newTweet = null;
                tweet();
            }
        }
        //TODO: Cleanup this unneeded code.
        if (newTweet != null) {
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

    public static void initSecrets() throws Exception {
        // This was added for obscurity. Obviously we cannot completely hide this without any kind of obfuscation
        // and encryption, but this works fine :)
        // If someone has our Consumer Key / Secret, they could pretend to be us...

        String baseURL = "http://api.kronosad.com/KronosTwit/twitData/secrets/";
        ReadURL conKey = new ReadURL(baseURL + "consumerKey.txt");
        consumerKey = conKey.read();
        ReadURL conSecret = new ReadURL(baseURL + "consumerSecret.txt");
        consumerSecret = conSecret.read();

    }

    public static void mainMenu() {
        System.out.println("---------- KronosTwit Main Menu ---------");
        System.out.println("What would you like to do?");
        System.out.println("1: Tweet");
        System.out.println("2: Print Timeline");
        System.out.println("3: Print Mentions");
        System.out.println("4: Print Direct Messages");
        System.out.println("5: Exit!");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
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
