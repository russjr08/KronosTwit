/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author russjr08
 */
public class DeleteUserDataListener implements ActionListener{

    public void actionPerformed(ActionEvent ae) {

        int delete = JOptionPane.showConfirmDialog(null, "Are you sure you want"
                + " to delete your userdata? This will force you to reauthenticate"
                + " to Twitter and re-sign in."
                + "\n This could also be used to sign in as a different user.",
                "Delete Userdata?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        System.out.println(delete);
        if(delete == 0){
            deleteUserData();
        }
    
    }
    
    public void deleteUserData(){
        File userData = new File("twitter4j.properties");
        
        if(userData.exists()){
            try{
                userData.delete();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "There was an error deleting"
                        + " your userdata!", "Error!", JOptionPane.ERROR_MESSAGE);
            }finally{
                JOptionPane.showMessageDialog(null, "User Data Deleted. KronosTwit"
                        + " will now close, re-open KronosTwit and you will be"
                        + " guided through the New User Setup once again.",
                        "Closing App!", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        }
        
    }
    
}
