/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Russell
 */
public class URLUnshortener {

    // TODO: Reimplment unshortener toggle (returns the 'unshort' parameter immediatley)
    
    public static String unshorten(String unshort){
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
   
    
}
