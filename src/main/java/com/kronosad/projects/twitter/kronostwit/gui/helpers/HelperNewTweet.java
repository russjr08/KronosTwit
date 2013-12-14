package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.enums.FinishedWork;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import java.io.File;

/**
 *
 * @author Russell
 */
public class HelperNewTweet {
    
    private IStatus statuses;
    private String tweet;
    private long replyID;
    private File attachment = null;
    
    public HelperNewTweet(IStatus status, String tweet){
        statuses = status;
        this.tweet = tweet;
        
    }
    
    public HelperNewTweet(IStatus status, String tweet, long reply){
        statuses = status;
        this.tweet = tweet;
        replyID = reply;
        
    }
    
    public FinishedWork tweet(){
        System.out.println("Tweet: " + tweet);
        StatusUpdate update = new StatusUpdate(tweet);

        if(tweet == null || tweet == ""){
            return FinishedWork.NOTWEET;
        }
        if(tweet.length() > 140){
            return FinishedWork.OUTOFBOUNDS;
        }else if(tweet.length() <= 140){
            if(attachment != null){
                update.setMedia(attachment);
            }
            try {
                ConsoleMain.twitter.updateStatus(update);
            } catch (TwitterException ex) {
                System.out.println("Error Posting Tweet!");
                ex.printStackTrace();
                if(ex.getErrorCode() == 187){
                    return FinishedWork.DUPLICATE;
                }
                return FinishedWork.TWITTERERROR;
            }finally{
                return FinishedWork.SUCCESS;
            }
        }
        
        
       return null;
    }
    
    public FinishedWork tweetWithReply(){
        if(tweet == null || tweet == ""){
            return FinishedWork.NOTWEET;
            
        }
        if(replyID == 0){
            return FinishedWork.UNKNOWN;
        }
        
        StatusUpdate update = new StatusUpdate(tweet);
        update.setInReplyToStatusId(replyID);

        if(attachment != null){
            update.setMedia(attachment);
        }

        if(tweet.length() > 140){
            return FinishedWork.OUTOFBOUNDS;
        }else if(tweet.length() <= 140){
            try {
                ConsoleMain.twitter.updateStatus(update);
            } catch (TwitterException ex) {
                System.out.println("Error Posting Tweet!");
                ex.printStackTrace();
                return FinishedWork.TWITTERERROR;
            }finally{
                return FinishedWork.SUCCESS;
            }
        }
        
        
        return null;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setFile(File file){
        attachment = file;
    }

    
    
    
    
    
    
    
    
}
