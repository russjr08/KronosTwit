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

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author Russell
 */
public class TweetHelper {

    /**
     * @deprecated Use {@link twitter4j.Status#getURLEntities()} for grabbing URLs
     * @param tweet The tweet to parse for links
     * @return An {@link java.util.ArrayList} of Links.
     */
    @Deprecated
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
        String json = DataObjectFactory.getRawJSON(status);

        if(json == null){
            // In case DataObjectFactory fails, fall back on Java Reflection.
            try {
                Field text = status.getClass().getDeclaredField("text");
                text.setAccessible(true);
                String statusText = (String) text.get(status);

                for(URLEntity entity : status.getURLEntities()){
                    statusText = statusText.replace(entity.getText(), entity.getExpandedURL());
                }

                for(MediaEntity media : status.getMediaEntities()){
                    statusText = statusText.replace(media.getText(), media.getMediaURL());
                }

                text.set(status, statusText);

                return status;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }


        }
        try {
            jsonObject = new JSONObject(json);

            String jsontext = jsonObject.getString("text");
            for(URLEntity entity : status.getURLEntities()){
                jsontext = jsontext.replace(entity.getText(), entity.getExpandedURL());
            }

            for(MediaEntity media : status.getMediaEntities()){
                jsontext = jsontext.replace(media.getText(), media.getMediaURL());
            }

            jsonObject.remove("text");
            jsonObject.put("text", jsontext);
            updatedStatus = DataObjectFactory.createStatus(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updatedStatus;

    }
    
    
}
