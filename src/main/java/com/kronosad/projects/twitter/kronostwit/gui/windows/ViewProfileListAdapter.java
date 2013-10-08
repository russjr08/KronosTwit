/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowTweetDetails;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import twitter4j.TwitterException;

/**
 *
 * @author russjr08
 */
public class ViewProfileListAdapter extends MouseAdapter{
    
    private IStatus status;
    
    
    public ViewProfileListAdapter(IStatus status){
        this.status = status;
    }
    
    @Override
    public void mouseClicked(MouseEvent event){
    
        if(SwingUtilities.isLeftMouseButton(event)){
            if(event.getClickCount() == 2){
                try {
                    new WindowTweetDetails(ConsoleMain.twitter.showStatus(status.getStatuses().get(status.getSelectedStatus()).getId()));
                } catch (TwitterException ex) {
                    Logger.getLogger(ViewProfileListAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
}
