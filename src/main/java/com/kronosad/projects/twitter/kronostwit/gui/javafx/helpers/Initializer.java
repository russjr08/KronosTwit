package com.kronosad.projects.twitter.kronostwit.gui.javafx.helpers;

import com.kronosad.projects.twitter.kronostwit.gui.javafx.WindowLoading;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.WindowTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.listener.stream.TwitterStreamListener;
import javafx.concurrent.Task;
import twitter4j.*;

/**
 * User: russjr08
 * Date: 1/28/14
 * Time: 7:13 PM
 */
public class Initializer extends Task{
    private String conKey, conSecret;
    private WindowLoading loading;

    public Initializer(String conKey, String conSecret, WindowLoading loading){
        this.conKey = conKey;
        this.conSecret = conSecret;
        this.loading = loading;
    }

    @Override
    protected Object call() throws Exception {
        ResponseList<Status> statuses = WindowLoading.twitter.getHomeTimeline(new Paging(1, 80));

        for(Status s : statuses){
            WindowTimeline.instance.addTweet(s, true);
            System.out.println(s);
        }

        for(Status s : WindowLoading.twitter.getMentionsTimeline(new Paging(1, 80))){
            WindowTimeline.instance.addMention(s, true);
        }


        loading.setStatus("Activating Streaming Features...");
        TwitterStream stream = TwitterStreamFactory.getSingleton();
        stream.addListener(new TwitterStreamListener());
        stream.setOAuthConsumer(conKey, conSecret);
        stream.user();

        return null;
    }
}
