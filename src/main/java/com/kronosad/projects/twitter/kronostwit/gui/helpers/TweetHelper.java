/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.internal.org.json.JSONObject;
import twitter4j.json.DataObjectFactory;

import java.util.ArrayList;

/**
 *
 * @author Russell
 */
public class TweetHelper {
    
    public static ArrayList<String> getLinksFromTweet(String tweet){
        ArrayList<String> links = new ArrayList<String>();
        String[] composition = tweet.split(" ");
        
        for(String words : composition){
            if(words.startsWith("http://") || words.startsWith("https://")){
                if(!links.contains(words)){
                    links.add(words);
                    System.out.println("Link found!");

                }
            }
            
            if(words.startsWith("/r/")){
                if(!links.contains(words)){
                    links.add("http://reddit.com" + words);
                }
            }
            
            if(words.startsWith("r/")){
                if(!links.contains(words)){
                    links.add("http://reddit.com/" + words);
                }
            }
        }
        
        
        return links;
        
        
        
    }
    
    public static ArrayList<String> getHashtagsFromTweet(String tweet){
        ArrayList<String> hashtags = new ArrayList<String>();
        
        String[] composition = tweet.split(" ");
        
        for(String word : composition){
            if(word.startsWith("#")){
                System.out.println("Hashtag found!");
                if(!hashtags.contains(word)){
                    hashtags.add(word);
                }
            }
        }
        
        return hashtags;
    }
    
    public static ArrayList<String> getUsersFromTweet(String tweet){
        ArrayList<String> users = new ArrayList<String>();
        
        String[] composition = tweet.split(" ");
        for(String word  : composition){
            if(word.startsWith("@")){
                System.out.println("User Found!");
                if(!users.contains(word)){
                    users.add(word);
                }
            }
        }
        
        return users;
    }
    
    public static String unshortenTweet(String tweet){
        String[] words = tweet.split(" ");
        
        for(int i = 0; i < words.length; i++){
            String word = words[i];
            if(word.startsWith("http")){
                tweet = tweet.replace(word, URLUnshortener.unshorten(word));
                
            }
        }
        
        return tweet;
    }

    public static Status removeTwitterLinks(Status status){
        Status updatedStatus = null;


        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(DataObjectFactory.getRawJSON(status));

            String JSON_Text = jsonObject.getString("text");
            for(URLEntity entity : status.getURLEntities()){
                JSON_Text = JSON_Text.replace(entity.getText(), entity.getExpandedURL());
            }

            for(MediaEntity media : status.getMediaEntities()){
                JSON_Text = JSON_Text.replace(media.getText(), media.getMediaURL());
            }

            jsonObject.remove("text");
            jsonObject.put("text", JSON_Text);
            updatedStatus = DataObjectFactory.createStatus(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }






        return updatedStatus;

    }
    
    
}
