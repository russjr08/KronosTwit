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
    
    
}
