/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.windows.WindowViewProfile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author russjr08
 */
public class ShowProfileListener implements ActionListener{

    public void actionPerformed(ActionEvent ae) {

       String userName = JOptionPane.showInputDialog(null, "Whose Profile Do You Want To Visit?", "Which User?", JOptionPane.QUESTION_MESSAGE);
       User user = null;
       if(userName != null && !userName.isEmpty()){ 
        try {
             user = ConsoleMain.twitter.showUser(userName);
         } catch (TwitterException ex) {
             JOptionPane.showMessageDialog(null, "An Error Occured Trying to Locate the user: " + userName, "Twitter Error", JOptionPane.ERROR_MESSAGE);
         }finally{
             WindowViewProfile profileWindow = new WindowViewProfile(userName, 500, 600, user, ConsoleMain.twitter);
         } 
       }else{
           System.out.println("No Context");
       }
    
    }
    
    
    
    
//    @Override
//    public void mousePressed(MouseEvent event){
//       System.out.println("Called ProfileListener");
//       String userName = JOptionPane.showInputDialog("Whose Profile Do You Want To Visit?");
//       User user = null;
//        try {
//            user = ConsoleMain.twitter.showUser(userName);
//        } catch (TwitterException ex) {
//            JOptionPane.showMessageDialog(null, "An Error Occured Trying to Locate the user: " + userName, "Twitter Error", JOptionPane.ERROR_MESSAGE);
//        }finally{
//            WindowViewProfile profileWindow = new WindowViewProfile(userName, 500, 600, user, ConsoleMain.twitter);
//        }
//        
//        
//    }
    
    
}
