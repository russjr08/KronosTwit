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
            if(words.contains("http://") || words.contains("https://")){
                links.add(words);
            }
        }
        
        
        return links;
        
        
        
    }
    
    
}
