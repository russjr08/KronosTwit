/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.twitter.kronostwit.gui.windows.popup;

import java.awt.Image;
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
import javax.swing.JScrollPane;

/**
 *
 * @author Russell
 */
public class WindowImagePopup extends JFrame{
    private URL imageUrl;
    private JLabel imageLabel;
    private JScrollPane scrollPane;
    
    public WindowImagePopup(String url){
        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WindowImagePopup.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        imageLabel = new JLabel();
        
        scrollPane = new JScrollPane(imageLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.add(scrollPane);
        this.pack();
        
        this.setTitle("Downloading Image...");
        this.setSize(200, 200);
        this.setVisible(true);
        downloadImage();
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 200, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 200);
        
        
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
