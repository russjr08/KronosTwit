package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterStream;

import java.util.HashMap;

/**
 * Created by Russell on 1/29/14.
 */
public class TwitterContainer {
    public static ObservableList<Status> homeTweetList = FXCollections.observableArrayList();
    public static ObservableList<Status> mentionTweetList = FXCollections.observableArrayList();
    public static Twitter twitter;
    public static TwitterStream stream;

    public static HashMap<String, Image> imgCache = new HashMap<>();
}
