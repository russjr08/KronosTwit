
package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewTimeline;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;

/**
 *
 * @author Russell
 */
public class GenericClickListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Click called!");
        if(e.getComponent() instanceof JMenuItem){
            
            JMenuItem item = (JMenuItem)e.getComponent();
            
            String[] words = item.getText().split(" ");
            
            for(String link : words){
                if(link.contains("http")){
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
                if(link.contains("#")){
                    
                    WindowViewTimeline.search(link);
                    
                }
            }
            
        }
        
        
    }
    
    
    
}
