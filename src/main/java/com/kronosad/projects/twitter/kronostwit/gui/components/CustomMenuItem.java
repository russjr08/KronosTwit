package com.kronosad.projects.twitter.kronostwit.gui.components;

import com.kronosad.projects.twitter.kronostwit.interfaces.ITweetReceptor;
import javafx.scene.control.MenuItem;

/**
 * Created by Russell on 5/22/2014.
 */
public class CustomMenuItem extends MenuItem {

    private ITweetReceptor tweetReceptor;

    public CustomMenuItem(ITweetReceptor tweetReceptor) {
        this.tweetReceptor = tweetReceptor;
    }
}
