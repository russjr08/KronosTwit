package com.kronosad.projects.twitter.kronostwit.gui.helpers;


import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

public class HelperRefreshTimeline {

    public static boolean canRefresh = true;
    private static IStatus statuses;
    
    public HelperRefreshTimeline(IStatus status){
        this.statuses = status;
    }
    @Deprecated
    public void refreshTimeline(){
        
    }
    
    public void refreshTimelineFirstTime(){
        User authedUser = null;
        try {
            authedUser = ConsoleMain.twitter.showUser(ConsoleMain.twitter.getId());
        } catch (TwitterException ex) {
            Logger.getLogger(HelperRefreshTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(canRefresh){
            try {

                System.out.println("Status update, REFRESH!");
                statuses.getStatuses().clear();
                statuses.getTweetList().clear();




                for(Status timelineStatuses : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){
                    
                    statuses.getStatuses().add(timelineStatuses);

                    
                }

                for(Status status : statuses.getStatuses()){
                    statuses.getTweetList().addElement(String.format("[%s:%s]%s:\n %s", status.getCreatedAt().getHours(), status.getCreatedAt().getMinutes(), status.getUser().getName(), status.getText()));
                    
                }
                WindowViewTimeline timelineWindow = (WindowViewTimeline) statuses;

                for(Status timelineStatuses : ConsoleMain.twitter.getMentionsTimeline(new Paging(1, 80))){

                    timelineWindow.mentions.add(timelineStatuses);
                    
                    
                }

                for(Status status : timelineWindow.mentions){
                    timelineWindow.mentionsList.addElement(String.format("[%s:%s]%s:\n %s", status.getCreatedAt().getHours(), status.getCreatedAt().getMinutes(), status.getUser().getName(), status.getText()));
                    
                }
            } catch (TwitterException e) {
                e.printStackTrace();
                if(e.getStatusCode() == 429){
                    System.out.println("ERROR: Twitter limit reached. Shutting down auto update! You will need to" +
                            " restart this application to reuse refresh!");
                    canRefresh = false;
                }
            }

        }else{
            System.out.println("We can not refresh at the time! (Twitter status refresh limit reached!)");
        }

    }
    
    @Deprecated
    public static void autoUpdate(){
        System.out.println("Status Update Performed.");

        statuses.getStatuses().clear();
        statuses.getTweetList().clear();


        try {
            for(Status status : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){

                //list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
                statuses.getStatuses().add(status);
            }

            for(Status status : statuses.getStatuses()){
                statuses.getTweetList().addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

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
