/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.helpers;

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
    
    
}
