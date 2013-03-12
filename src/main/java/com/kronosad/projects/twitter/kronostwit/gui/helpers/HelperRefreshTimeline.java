package com.kronosad.projects.twitter.kronostwit.gui.helpers;


import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

public class HelperRefreshTimeline {

    public static boolean canRefresh = true;

    public static void refresh(){
        if(canRefresh){
            try {

                System.out.println("Status update, REFRESH!");
                MainGUI.statuses.clear();
                MainGUI.list.clear();




                for(Status timelineStatuses : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){
                    MainGUI.statuses.add(timelineStatuses);
                }

                for(Status status : MainGUI.statuses){
                    MainGUI.list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

                }
            } catch (TwitterException e) {
                e.printStackTrace();
                if(e.getStatusCode() == 429){
                    System.out.println("ERROR: Twitter limit reached. Shutting down auto update! You will need to" +
                            " restart this application to reuse refresh!");
                    if(MainGUI.tm != null){
                        MainGUI.tm.stop();
                        canRefresh = false;

                    }
                }
            }

        }else{
            System.out.println("We can not refresh at the time! (Twitter status refresh limit reached!)");
        }

    }

    public static void autoUpdate(){
        System.out.println("Status Update Performed.");

        MainGUI.statuses.clear();
        MainGUI.list.clear();


        try {
            for(Status status : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){

                //list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
                MainGUI.statuses.add(status);
            }

            for(Status status : MainGUI.statuses){
                MainGUI.list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

            }
        } catch (TwitterException exception) {
            exception.printStackTrace();
            if(exception.getStatusCode() == 429){
                System.out.println("ERROR: Twitter limit reached. Shutting down auto update! You will need to " +
                        "reopen this application to start using again.");
                if(MainGUI.tm != null){
                    MainGUI.tm.stop();
                    canRefresh = false;

                }
            }
        }



    }

}
