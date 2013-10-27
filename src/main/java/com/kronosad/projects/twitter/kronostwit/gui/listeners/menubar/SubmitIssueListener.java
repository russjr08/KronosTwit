/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.listeners.menubar;

import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowReportIssue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author russjr08
 */
public class SubmitIssueListener implements ActionListener{

    public void actionPerformed(ActionEvent ae) {

        new WindowReportIssue();
        
    }
    
}
