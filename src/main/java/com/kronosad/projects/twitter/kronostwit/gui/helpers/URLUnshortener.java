/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author Russell
 */
public class URLUnshortener {
    
    public static String unshorten(String unshort){
        if(!ConsoleMain.arguments.contains("-nounshorten")){
            if(unshort != null || !unshort.isEmpty()){
                URL unshortener = null;

                try {
                    unshortener = new URL("http://expandurl.appspot.com/expand?url=" + URLEncoder.encode(unshort, "ISO-8859-1"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(URLUnshortener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(URLUnshortener.class.getName()).log(Level.SEVERE, null, ex);
                }

                String json = null;
                InputStream stream;
                try {
                    stream = unshortener.openStream();
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    while((line = reader.readLine()) != null){
                        json += line;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(URLUnshortener.class.getName()).log(Level.SEVERE, null, ex);
                }

                String[] parsed = json.split(" ");

                return parsed[3].replace("\"", "").replace(",", "");
            }else{
                return "";
            }
        }
        
        return unshort;
        
        
        
        
        

    }
   
    
}
