/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.twitter.kronostwit.gui.windows.popup;

import java.awt.Image;
import java.awt.Window;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

/**
 *
 * @author Russell
 */
public class WindowImagePopup extends JFrame{
    private URL imageUrl;
    private JLabel imageLabel;
    
    public WindowImagePopup(String url){
        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WindowImagePopup.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        imageLabel = new JLabel();
        
        this.add(imageLabel);
        this.pack();
        
        this.setTitle("Downloading Image...");
        this.setSize(200, 200);
        this.setVisible(true);
        downloadImage();
        
    }
    
    private void downloadImage(){
        ImageIcon image = null;
        System.out.println("Downloading Image from... " + imageUrl.toString());
        try {
            Image rawImage = ImageIO.read(imageUrl);
            image = new ImageIcon(rawImage);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error Downloading Image!", "Error Grabbing Image", JOptionPane.ERROR_MESSAGE);
            this.setTitle("Failed to Download Image");
        }
        
        this.setTitle("Image");
        imageLabel.setIcon(image);
        this.setSize(image.getIconWidth(), image.getIconHeight());
        
        
    }
    
    
    
    
    
}
