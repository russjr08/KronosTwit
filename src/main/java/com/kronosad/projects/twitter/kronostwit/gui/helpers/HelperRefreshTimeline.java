package com.kronosad.projects.twitter.kronostwit.gui.helpers;


import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.StreamStatusListener;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.Preferences;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences.filter.Filter;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
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
        
        new UpdateRunner().execute();
        

        
        
        

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
                                statuses.getStatuses().add(timelineStatuses);
                            }


                        }
                        
                        System.out.println(String.format("%s tweets were not added due to being filtered out!", filteredHomeTweets));
                        
                        final WindowViewTimeline timelineWindow = (WindowViewTimeline) statuses;

                        System.out.println("Adding home timeline to window...");
                        
                        for(Status status : statuses.getStatuses()){
                            timelineWindow.tweetsList.addElement(String.format("[%s:%s]%s:\n %s", status.getCreatedAt().getHours(), status.getCreatedAt().getMinutes(), status.getUser().getName(), TweetHelper.unshortenTweet(status.getText())));
 
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
                                timelineWindow.mentions.add(timelineStatuses);
                            }
                            


                        }
                        System.out.println(String.format("%s mentions were not added due to being filtered out!", filteredMentions));

                        System.out.println("Adding mentions timeline to window...");
                        for(Status status : timelineWindow.mentions){
                            timelineWindow.mentionsList.addElement(String.format("[%s:%s]%s:\n %s", status.getCreatedAt().getHours(), status.getCreatedAt().getMinutes(), status.getUser().getName(), TweetHelper.unshortenTweet(status.getText())));
                            
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
            stream.setOAuthConsumer(ConsoleMain.consumerKey, ConsoleMain.consumerSecret);
            stream.user();
        
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run()
                {
                    stream.shutdown();
                    System.out.println("Closing App!");
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
