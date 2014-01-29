package com.kronosad.projects.twitter.kronostwit.console;

import com.kronosad.api.internet.ReadURL;
import com.kronosad.projects.twitter.kronostwit.checkers.CheckerBetaUser;
import com.kronosad.projects.twitter.kronostwit.checkers.UpdateInformation;
import com.kronosad.projects.twitter.kronostwit.commands.CommandRegistry;
import com.kronosad.projects.twitter.kronostwit.commands.CommandUnshortenURL;
import com.kronosad.projects.twitter.kronostwit.commands.DefaultCommands;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.Updater;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.WindowTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowLoadingScreen;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

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

/**
 * This is an internal class. DO NOT ATTEMPT TO MESS WITH IT.
 */
public class ConsoleMain {

    public static Twitter twitter;
    private static RequestToken requestToken;
    private static AccessToken accessToken;
    public static Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    private static Scanner scanner;
    private static String pin;
    private static Properties prop;
    private static String consumerKey, consumerSecret;
    public static WindowLoadingScreen loading = new WindowLoadingScreen("Loading Application", 50, 50);
    public static ArrayList<String> arguments = new ArrayList<String>();

    protected static void load(final String[] args) {
        File jar = new File(ConsoleMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("This jar's file name: " + jar.getName());


        for (String argument : args) {
            arguments.add(argument);
        }
        while (loading.isUpdating) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!arguments.contains("-development")) {
            if (!jar.getName().equals("KronosTwit.jar")) {
                JOptionPane.showMessageDialog(null, "Hey! My jar file name is not KronosTwit.jar! Please rename "
                        + "me next time I am closed! My update mechanism expects my file name to be KronosTwit.jar!",
                        "Unsafe file name detected", JOptionPane.WARNING_MESSAGE);
            }

        }

        if (arguments.contains("-updated")) {
            JOptionPane.showMessageDialog(null, String.format("Congrats! KronosTwit has been automatically updated to %s!", ConsoleLoader.updater.versionNumber), "Update complete!", JOptionPane.INFORMATION_MESSAGE);

            if(ConsoleLoader.updater.getVersion().needsResetLogin()){
                File file = new File("twitter4j.properties");
                if(file.exists()){
                    file.delete();
                }
                JOptionPane.showMessageDialog(null, "Sorry for the inconvenience, but this update requires a re-login to Twitter!",
                        "Relog required!", JOptionPane.WARNING_MESSAGE);
            }

        }

        if (arguments.contains("-forceupdate")) {
            Updater.update(ConsoleLoader.updater, false);
        }

        while (loading.isDownloadingData) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        ConsoleLoader.updater = new UpdateInformation();
        try {
            ConsoleLoader.updater.check();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to check for updates!", "Update Check Failed", JOptionPane.WARNING_MESSAGE);
        } finally {
            System.out.println("Final Versions: ");
            System.out.println("Client: " + ConsoleLoader.updater.versionNumber);
            System.out.println("Server: " + ConsoleLoader.updater.serverVersion);
            if (ConsoleLoader.updater.serverVersion > ConsoleLoader.updater.versionNumber) {

                System.out.println("WARNING: Starting update procedures!");
                JOptionPane.showMessageDialog(null, "Your versionNumber of KronosTwit is out of date. KronosTwit will now update.",
                        "Updating Initiated", JOptionPane.WARNING_MESSAGE);
                Updater.update(ConsoleLoader.updater, false);

                System.exit(1);
            } else if (ConsoleLoader.updater.serverVersion < ConsoleLoader.updater.versionNumber && !arguments.contains("-development")) {
                JOptionPane.showMessageDialog(null, "Your Version of KronosTwit has a higher versionNumber than the one detected on the server, \n"
                        + "this probably means you are running a BETA copy. Please proceed with caution!", "Version Mismatch Detected!", JOptionPane.WARNING_MESSAGE);

            }
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setJSONStoreEnabled(true);
        cb.setIncludeEntitiesEnabled(true);

        twitter = new TwitterFactory(cb.build()).getInstance();
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


        loading.checkUser();

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

            CheckerBetaUser.isBetaUser(twitter.getScreenName());
        } catch (TwitterException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if(arguments.contains("javafxEnabled")){
                new WindowTimeline();
//                return;
            }
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    if (!arguments.contains("-console")) {
                        registerCommands();
//                        try {
//                            new WindowViewTimeline("View Timeline", 500, 600);
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        loading.loadingTweets();
//                        ConsoleLoader.crawlPluginsDir();
                    } else {
                        System.out.println("All GUI methods halted! You are now running in Console Only mode!");
                        mainMenu();
                    }
                }

            });


        }


    }


    private static void registerCommands(){
        DefaultCommands.fabricateDefaultCommands();
        CommandRegistry.registerCommand(new CommandUnshortenURL());

    }

    private static void getNewUserTokenGUI() {
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
            System.out.println("An error occured. Your PIN may be invalid, re-executing user setup.");
            getNewUserTokenGUI();
            e.printStackTrace();
        }


    }

    private static void saveAccessToken(AccessToken token) {
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

    private static void printHomeTimeline() {
        try {
            for (Status status : twitter.getHomeTimeline(new Paging(1, 100))) {
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    private static void printMentions() {
        try {
            for (Status status : twitter.getMentionsTimeline()) {
                System.out.println(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    private static void printDirectMessages() {
        try {
            for (DirectMessage message : twitter.getDirectMessages()) {
                System.out.println(String.format("[%s]%s: %s", message.getCreatedAt(), message.getSenderScreenName(), message.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    private static void tweet() {
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

    private static void initSecrets() throws Exception {
        // This was added for obscurity. Obviously we cannot completely hide this without any kind of obfuscation
        // and encryption, but this works fine :)
        // If someone has our Consumer Key / Secret, they could pretend to be us...

        String baseURL = "http://api.kronosad.com/KronosTwit/twitData/secrets/";
        ReadURL conKey = new ReadURL(baseURL + "consumerKey.txt");
        consumerKey = conKey.read();
        ReadURL conSecret = new ReadURL(baseURL + "consumerSecret.txt");
        consumerSecret = conSecret.read();

    }

    private static void mainMenu() {
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
