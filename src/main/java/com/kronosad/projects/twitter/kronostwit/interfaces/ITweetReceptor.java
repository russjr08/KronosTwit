package com.kronosad.projects.twitter.kronostwit.interfaces;

import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Created by Russell on 5/22/2014.
 */
public interface ITweetReceptor {

    public Status getSelectedStatus() throws TwitterException;

}
