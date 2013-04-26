/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;



/**
 *
 * @author russjr08
 */
public class WindowViewProfile extends JFrame {
    private User user;
    private Twitter twitter;
    public Image profileImage;
    
    private ArrayList<Status> statuses = new ArrayList<Status>();
    private DefaultListModel tweetModel = new DefaultListModel();
    
    /**
     * Creates new form WindowViewProfile
     */
    public WindowViewProfile(String title, int SizeX, int SizeY, User user, Twitter twitter) {
        this.user = user;
        this.twitter = twitter;               
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profilePictureLabel = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblUsernameField = new javax.swing.JLabel();
        lblWebsite = new javax.swing.JLabel();
        lblWebsiteTxt = new javax.swing.JLabel();
        lblLocation = new javax.swing.JLabel();
        lblLocationText = new javax.swing.JLabel();
        lblBio = new javax.swing.JLabel();
        tbdPaneTweets = new javax.swing.JTabbedPane();
        tweetsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tweetsList = new javax.swing.JList();
        bioLblTxt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblUsername.setText("Username: ");

        lblUsernameField.setText("%u");

        lblWebsite.setText("Website: ");

        lblWebsiteTxt.setText("%w");

        lblLocation.setText("Location: ");

        lblLocationText.setText("%l");

        lblBio.setText("Bio: ");

        jScrollPane2.setViewportView(tweetsList);

        org.jdesktop.layout.GroupLayout tweetsPanelLayout = new org.jdesktop.layout.GroupLayout(tweetsPanel);
        tweetsPanel.setLayout(tweetsPanelLayout);
        tweetsPanelLayout.setHorizontalGroup(
            tweetsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tweetsPanelLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );
        tweetsPanelLayout.setVerticalGroup(
            tweetsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tweetsPanelLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbdPaneTweets.addTab("Tweets", tweetsPanel);

        bioLblTxt.setAutoscrolls(true);
        bioLblTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tbdPaneTweets)
                    .add(layout.createSequentialGroup()
                        .add(profilePictureLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(lblUsername)
                                    .add(lblWebsite)
                                    .add(lblLocation)
                                    .add(lblBio))
                                .add(18, 18, 18)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(lblWebsiteTxt)
                                    .add(lblUsernameField)
                                    .add(lblLocationText)))
                            .add(bioLblTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 304, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblUsername)
                            .add(lblUsernameField))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblWebsite)
                            .add(lblWebsiteTxt)))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(profilePictureLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblLocation)
                    .add(lblLocationText))
                .add(18, 18, 18)
                .add(lblBio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(bioLblTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(tbdPaneTweets)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bioLblTxt;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBio;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblLocationText;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsernameField;
    private javax.swing.JLabel lblWebsite;
    private javax.swing.JLabel lblWebsiteTxt;
    private javax.swing.JLabel profilePictureLabel;
    private javax.swing.JTabbedPane tbdPaneTweets;
    private javax.swing.JList tweetsList;
    private javax.swing.JPanel tweetsPanel;
    // End of variables declaration//GEN-END:variables

    
    public void init() {
        URL profileImageURL = null;
        try {
            profileImageURL = new URL(user.getProfileImageURL());
            
        } catch (MalformedURLException e) {
            System.out.println("Error getting URL of profile image");
            e.printStackTrace();
        }

        try {
            profileImage = ImageIO.read(profileImageURL);
            profilePictureLabel.setIcon(new ImageIcon(profileImage));

        } catch (IOException e) {
            System.out.println("Error getting profile image!");
            e.printStackTrace();
        }
        
        lblUsernameField.setText(user.getScreenName());
        lblWebsiteTxt.setText(user.getURL());
        lblLocationText.setText(user.getLocation());
        bioLblTxt.setText(user.getDescription());
        tweetsList.setModel(tweetModel);
        populateTweets();
        
        this.setVisible(true);

    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public void populateTweets(){
        if(HelperRefreshTimeline.canRefresh){
            try {

                System.out.println("Status update, REFRESH!");
                this.statuses.clear();
                this.tweetModel.clear();




                for(Status timelineStatuses : twitter.getUserTimeline(user.getScreenName())){
                    this.statuses.add(timelineStatuses);
                }

                for(Status status : this.statuses){
                    this.tweetModel.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

                }
            } catch (TwitterException e) {
                e.printStackTrace();
                if(e.getStatusCode() == 429){
                    System.out.println("ERROR: Twitter limit reached. Shutting down auto update! You will need to" +
                            " restart this application to reuse refresh!");
                    if(MainGUI.tm != null){
                        MainGUI.tm.stop();
                        HelperRefreshTimeline.canRefresh = false;

                    }
                }
            }

        }else{
            System.out.println("We can not refresh at the time! (Twitter status refresh limit reached!)");
        }
    }
}
