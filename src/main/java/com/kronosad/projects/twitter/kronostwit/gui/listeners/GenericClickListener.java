
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowImagePopup;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;

/**
 *
 * @author Russell
 */
public class GenericClickListener extends MouseAdapter {
    private String[] imageExtensions = {".png", ".jpg", ".jpeg", ".gif"};
            
    
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Click called!");
        
        if(e.getComponent() instanceof JMenuItem){
            
            JMenuItem item = (JMenuItem)e.getComponent();
            
            String[] words = item.getText().split(" ");
            
            for(String link : words){
                boolean containsImage = false;
                if(link.contains("http")){
                    System.out.println(link);
                    for(String extension : imageExtensions){
                        if(link.endsWith(extension)){
                            new WindowImagePopup(link);
                            containsImage = true;
                            break;
                        }
                    }
                    if(!containsImage){
                        try {
                            URL url = new URL(link);
                            try {
                                Desktop.getDesktop().browse(url.toURI());
                            } catch (IOException ex) {
                                Logger.getLogger(GenericClickListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(GenericClickListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(GenericClickListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if(link.contains("#")){
                    
                    WindowViewTimeline.search(link);
                    
                }
            }
            
        }
        
        
    }
    
    
    
}
