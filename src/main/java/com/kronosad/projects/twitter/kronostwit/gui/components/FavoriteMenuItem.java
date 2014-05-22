package com.kronosad.projects.twitter.kronostwit.gui.components;

import com.kronosad.projects.twitter.kronostwit.gui.TwitterContainer;
import com.kronosad.projects.twitter.kronostwit.interfaces.ITweetReceptor;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Created by Russell on 5/22/2014.
 */
public class FavoriteMenuItem extends CustomMenuItem {
    public FavoriteMenuItem(ITweetReceptor tweetReceptor) {
        super(tweetReceptor);

        this.setOnAction((event) -> {
            Action response = Dialogs.create().masthead("Twitter Action...").message("Are you sure you want to favorite this tweet?").showConfirm();
            if(response == Dialog.Actions.YES) {
                Status status = null;
                try {
                    status = tweetReceptor.getSelectedStatus();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                if (!status.isFavorited()) {
                    try {
                        TwitterContainer.twitter.createFavorite(status.getId());
                    } catch (TwitterException e) {
                        Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not create favorite!").showException(e);
                        e.printStackTrace();
                    }
                } else {
                    try {
                        TwitterContainer.twitter.destroyFavorite(status.getId());
                    } catch (TwitterException e) {
                        Dialogs.create().masthead("Twitter Error...").title("Error!").message("Could not destroy favorite!").showException(e);

                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
