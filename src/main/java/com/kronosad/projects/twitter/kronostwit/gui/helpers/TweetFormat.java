package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import twitter4j.Status;

/**
 * User: russjr08
 * Date: 12/4/13
 * Time: 8:48 PM
 */
public class TweetFormat {

    public static String formatTweet(Status status){
        return String.format("[%s] %s:\n %s", DateHelper.getTime(status.getCreatedAt()), status.getUser().getName(), status.getText());
    }


}
