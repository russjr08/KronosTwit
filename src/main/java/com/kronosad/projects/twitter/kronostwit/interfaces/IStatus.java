package com.kronosad.projects.twitter.kronostwit.interfaces;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import twitter4j.Status;

/**
 *
 * @author russjr08
 */
public interface IStatus {
    
    /**
     * 
     * @return ArrayList<Status> A list of statuses.
     */
    
    public ArrayList<Status> getStatuses();
    
    /**
     * 
     * @return int - The selected tweet.
     */
    public int getSelectedStatus();
    
    
    /**
     * 
     * @return DefaultListModel - Gets the data for the JList.
     */
    
    public DefaultListModel getTweetList();
    
    
}
