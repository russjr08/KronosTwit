package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twitter4j.Twitter;
import twitter4j.TwitterStream;

/**
 * Created by Russell on 1/29/14.
 */
public class TwitterContainer {
    public static ObservableList<String> homeTweetList = FXCollections.observableArrayList();
    public static ObservableList<String> mentionTweetList = FXCollections.observableArrayList();
    public static Twitter twitter;
    public static TwitterStream stream;
}
