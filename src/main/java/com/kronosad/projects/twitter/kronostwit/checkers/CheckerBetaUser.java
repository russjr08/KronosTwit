package com.kronosad.projects.twitter.kronostwit.checkers;

import com.kronosad.api.internet.ReadURL;
import com.kronosad.projects.twitter.kronostwit.user.KronosUser;
import com.kronosad.projects.twitter.kronostwit.user.UserRegistry;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CheckerBetaUser {

    private static Properties prop = new Properties();


    public static boolean isBetaUser(String betaKey, String username){

        ReadURL readURL = new ReadURL("http://twit.locklin.info/api/getBetaUser.php?key=" + betaKey);

        String serverUsername = "NONE";

        try {
            serverUsername = readURL.read();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to verify your access to this beta program. Terminating.", "Error verifying userlevel",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }

        if(serverUsername.equalsIgnoreCase(username)){
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Sorry. This Beta Key was not meant for the currently signed in user.", "Access Denied.",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);


        }


        return false;
    }
    
    public static boolean isBetaUser(String username){
        
        KronosUser user = UserRegistry.getKronosUser(username);
        if(user.isBetaUser()){
            return true;
        }else{
            JOptionPane.showMessageDialog(null,
                    "Sorry, you are not enrolled in"
                    + " the KronosTwit Beta Testing Program",
                    "User Not Authorized", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        return false;
    }


    public static void setBetaKeyProperties(String betaKey){
        prop.setProperty("BetaKey", betaKey);
        System.out.println("Attempting to save Beta Key...");
        try {
            prop.store(new FileOutputStream("Beta.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("Attempt success! Beta Key is stored!");
        }
    }

    public static String getBetaKeyProperties(){

        String betaKey;

        try {
            prop.load(new FileInputStream("Beta.properties"));
        } catch (IOException e) {
            System.out.println("Error getting previous beta properties!");
            e.printStackTrace();
        }
        betaKey = prop.getProperty("BetaKey");

        if(betaKey != null){
            return betaKey;
        }


        return null;
    }





}
