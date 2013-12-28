package com.kronosad.projects.twitter.kronostwit.gui.helpers;


import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.data.SerializeUtils;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.StreamStatusListener;
import com.kronosad.projects.twitter.kronostwit.gui.windows.Window;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.Preferences;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.filter.Filter;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.*;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        new UpdateRunner().execute();
        

        
        
        

    }

    
    class UpdateRunner extends SwingWorker<Void, Void>{

        @Override
        protected Void doInBackground() throws Exception {
                System.out.println("Initializing Filters...");
                
                ArrayList<Filter> filters = Preferences.getFilters();
                System.out.println(String.format("Retrieved %s filters.", filters.size()));

                if(canRefresh){
                    try {
                        
                        // Home Timeline Tweets
                        System.out.println("Loading home timeline...");
                        int filteredHomeTweets = 0;
                        for(Status timelineStatuses : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){
                            boolean filtered = false;
                            for(Filter filter : filters){
                                if(filter.isUsername()){
                                    
                                    
                                    if(timelineStatuses.getUser().getScreenName().toLowerCase().contains(filter.getFilter().toLowerCase())){
                                        
                                        filtered = true;
                                        System.out.println("Filtered Tweet found.. Ignoring.");
                                        filteredHomeTweets++;

                                        break;
                                    }
                                }else{
                                    if(timelineStatuses.getText().toLowerCase().contains(filter.getFilter().toLowerCase())){
                                        
                                        filtered = true;
                                        System.out.println("Filtered Tweet found.. Ignoring.");

                                        filteredHomeTweets++;
                                        break;
                                    }
                                }
                            }
                            
                            
                            if(!filtered){
                                boolean found = false;
                                String searchedTweet = TweetHelper.removeTwitterLinks(timelineStatuses).getText();
                                for(Status status : statuses.getStatuses()){
                                    if(status.getText().equalsIgnoreCase(searchedTweet)){
                                        found = true;
                                    }
                                }
                                if(!found){
                                    statuses.getStatuses().add(TweetHelper.removeTwitterLinks(timelineStatuses));
                                }

                            }


                        }
                        
                        System.out.println(String.format("%s tweets were not added due to being filtered out!", filteredHomeTweets));
                        
                        final WindowViewTimeline timelineWindow = (WindowViewTimeline) statuses;

                        System.out.println("Adding home timeline to window...");
                        
                        for(Status status : statuses.getStatuses()){
                            timelineWindow.tweetsList.addElement(TweetFormat.formatTweet(status));
 
                        }
                        
                        
                        
                        System.out.println("Loading Mentions Timeline...");
                        int filteredMentions = 0;
                        
                        for(Status timelineStatuses : ConsoleMain.twitter.getMentionsTimeline(new Paging(1, 80))){
                            
                            boolean filtered = false;
                            
                            for(Filter filter : filters){
                                if(filter.isUsername()){
                                    if(timelineStatuses.getUser().getScreenName().toLowerCase().contains(filter.getFilter().toLowerCase())){
                                        filtered = true;
                                        filteredMentions++;
                                        System.out.println("Filtered Mention found.. Ignoring.");
                                        break;
                                    }
                                }else{
                                    if(timelineStatuses.getText().toLowerCase().contains(filter.getFilter().toLowerCase())){
                                        filtered = true;
                                        filteredMentions++;
                                        System.out.println("Filtered Mention found.. Ignoring.");
                                        break;
                                    }
                                }
                            }
                            
                            if(!filtered){
                                String searchedTweet = TweetHelper.removeTwitterLinks(timelineStatuses).getText();
                                boolean found = false;

                                for(Status status : timelineWindow.mentions){
                                    if(status.getText().equalsIgnoreCase(searchedTweet)){
                                        found = true;
                                    }
                                }

                                if(!found){
                                    timelineWindow.mentions.add(TweetHelper.removeTwitterLinks(timelineStatuses));
                                }
                            }
                            


                        }
                        System.out.println(String.format("%s mentions were not added due to being filtered out!", filteredMentions));

                        System.out.println("Adding mentions timeline to window...");
                        for(Status status : timelineWindow.mentions){
                            timelineWindow.mentionsList.addElement(TweetFormat.formatTweet(status));
                            
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
            
            System.out.println("Done loading tweets..");
            ConsoleMain.loading.done();
            
            System.out.println("Initializing Twitter Stream...");
            final TwitterStream stream = new TwitterStreamFactory().getInstance();
            stream.addListener(new StreamStatusListener(statuses));

            Field consumerKeyField = ConsoleMain.class.getDeclaredField("consumerKey");
            consumerKeyField.setAccessible(true);

            Field consumerSekratField = ConsoleMain.class.getDeclaredField("consumerSecret");
            consumerSekratField.setAccessible(true);

            stream.setOAuthConsumer((String)consumerKeyField.get(consumerKeyField), (String)consumerSekratField.get(consumerSekratField));
            stream.user();
        
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run()
                {
                    ConsoleLoader.console.appendToConsole("Closing App!");

                    for(Window window : ConsoleLoader.windows){
                        window.setTitle("Closing Application...");
                    }
                    stream.shutdown();

                    try {
                        if(statuses != null)
                            SerializeUtils.serializeStatuses(statuses);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Tweets were unable to persist!");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Tweets were unable to persist!");
                    }finally{
                        System.out.println("Tweets have been persisted.");
                    }

                }
            });
            
            new Thread(){
                @Override
                public void run(){
                    WindowViewTimeline timelineView = (WindowViewTimeline)statuses;
                    try {

                        timelineView.setVisible(true);
                        timelineView.loadGreetings();

                    } catch (IOException ex) {
                        Logger.getLogger(HelperRefreshTimeline.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TwitterException ex) {
                        Logger.getLogger(HelperRefreshTimeline.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ConsoleLoader.console.redraw();
                }
            }.start();
            
            return null;
        }
        
    }

}
