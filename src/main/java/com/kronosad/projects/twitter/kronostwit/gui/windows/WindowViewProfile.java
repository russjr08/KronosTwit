package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshUserTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.URLUnshortener;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import com.kronosad.projects.twitter.kronostwit.user.KronosUser;
import com.kronosad.projects.twitter.kronostwit.user.UserRegistry;
import twitter4j.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author russjr08
 */
public class WindowViewProfile extends Window implements IStatus{
    private User user;
    private Twitter twitter;
    public Image profileImage;
    public Image betaImage;
    public Image verifiedImage;
    
    private KronosUser kronosUser;
    
    private static Relationship relationship;

    
    
    private ArrayList<Status> statuses = new ArrayList<Status>();
    private DefaultListModel tweetModel = new DefaultListModel();
    
    /**
     * Creates new form WindowViewProfile
     */
    public WindowViewProfile(String title, int SizeX, int SizeY, User user, Twitter twitter) {
        super(title, SizeX, SizeY);
        try {
            this.relationship = ConsoleMain.twitter.showFriendship(ConsoleMain.twitter.getId(), user.getId());
        } catch (TwitterException ex) {
            Logger.getLogger(WindowViewProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.user = user;
        this.twitter = twitter; 
        kronosUser = UserRegistry.getKronosUser(user.getScreenName());
        
        if(kronosUser.getTag() != null){
            this.setTitle(kronosUser.getTag());
        }else{
            this.setTitle(user.getScreenName());
        }
        
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
        tbdPaneTweets = new javax.swing.JTabbedPane();
        tweetsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tweetsList = new javax.swing.JList();
        jProgressBar1 = new javax.swing.JProgressBar();
        betaUserStar = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        userDetailsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblWebsite = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblLocation = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblBio = new javax.swing.JLabel();
        relationshipPanel = new javax.swing.JPanel();
        chckBoxFollowing = new javax.swing.JCheckBox();
        chckBoxFollows = new javax.swing.JCheckBox();
        chckBoxBlocked = new javax.swing.JCheckBox();
        twitterDetailsPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblFollowerCount = new javax.swing.JLabel();
        lblFollowingCount = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblUserConceiveDate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(450, 650));

        jScrollPane2.setViewportView(tweetsList);

        org.jdesktop.layout.GroupLayout tweetsPanelLayout = new org.jdesktop.layout.GroupLayout(tweetsPanel);
        tweetsPanel.setLayout(tweetsPanelLayout);
        tweetsPanelLayout.setHorizontalGroup(
            tweetsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tweetsPanelLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );
        tweetsPanelLayout.setVerticalGroup(
            tweetsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tweetsPanelLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbdPaneTweets.addTab("Tweets", tweetsPanel);

        betaUserStar.setToolTipText("Beta User");

        jLabel1.setText("Username:");

        lblUsername.setText("%u");

        jLabel2.setText("Website:");

        lblWebsite.setText("%w");

        jLabel3.setText("Location:");

        lblLocation.setText("%l");

        jLabel4.setText("Bio:");

        lblBio.setText("%bio");
        lblBio.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.layout.GroupLayout userDetailsPanelLayout = new org.jdesktop.layout.GroupLayout(userDetailsPanel);
        userDetailsPanel.setLayout(userDetailsPanelLayout);
        userDetailsPanelLayout.setHorizontalGroup(
            userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(userDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(jLabel4))
                .add(18, 18, 18)
                .add(userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblUsername, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblWebsite, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .add(lblLocation, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblBio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        userDetailsPanelLayout.setVerticalGroup(
            userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(userDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(lblUsername))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(lblWebsite))
                .add(18, 18, 18)
                .add(userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(lblLocation))
                .add(18, 18, 18)
                .add(userDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(userDetailsPanelLayout.createSequentialGroup()
                        .add(jLabel4)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(lblBio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("User Details", userDetailsPanel);

        chckBoxFollowing.setText("Do you follow %u?");
        chckBoxFollowing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBoxFollowingActionPerformed(evt);
            }
        });

        chckBoxFollows.setText("Does %u follow you?");
        chckBoxFollows.setEnabled(false);

        chckBoxBlocked.setText("Is %u blocked? (Upcoming Feature)");
        chckBoxBlocked.setEnabled(false);

        org.jdesktop.layout.GroupLayout relationshipPanelLayout = new org.jdesktop.layout.GroupLayout(relationshipPanel);
        relationshipPanel.setLayout(relationshipPanelLayout);
        relationshipPanelLayout.setHorizontalGroup(
            relationshipPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(relationshipPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(relationshipPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chckBoxFollowing)
                    .add(chckBoxFollows)
                    .add(chckBoxBlocked))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        relationshipPanelLayout.setVerticalGroup(
            relationshipPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(relationshipPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(chckBoxFollowing)
                .add(26, 26, 26)
                .add(chckBoxFollows)
                .add(29, 29, 29)
                .add(chckBoxBlocked)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Relationship", relationshipPanel);

        jLabel5.setText("Followers:");

        jLabel6.setText("Following:");

        lblFollowerCount.setText("0");

        lblFollowingCount.setText("0");

        jLabel7.setText("Member Since:");

        lblUserConceiveDate.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.layout.GroupLayout twitterDetailsPanelLayout = new org.jdesktop.layout.GroupLayout(twitterDetailsPanel);
        twitterDetailsPanel.setLayout(twitterDetailsPanelLayout);
        twitterDetailsPanelLayout.setHorizontalGroup(
            twitterDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(twitterDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(twitterDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(twitterDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(twitterDetailsPanelLayout.createSequentialGroup()
                            .add(jLabel5)
                            .add(18, 18, 18)
                            .add(lblFollowerCount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(twitterDetailsPanelLayout.createSequentialGroup()
                            .add(jLabel6)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(lblFollowingCount, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(jLabel7))
                .addContainerGap(125, Short.MAX_VALUE))
            .add(twitterDetailsPanelLayout.createSequentialGroup()
                .add(25, 25, 25)
                .add(lblUserConceiveDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        twitterDetailsPanelLayout.setVerticalGroup(
            twitterDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(twitterDetailsPanelLayout.createSequentialGroup()
                .add(17, 17, 17)
                .add(twitterDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(lblFollowerCount))
                .add(42, 42, 42)
                .add(twitterDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(lblFollowingCount))
                .add(27, 27, 27)
                .add(jLabel7)
                .add(18, 18, 18)
                .add(lblUserConceiveDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Twitter Details", twitterDetailsPanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, tbdPaneTweets)
                    .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(profilePictureLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTabbedPane1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(betaUserStar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(56, 56, 56)
                        .add(profilePictureLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(betaUserStar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 257, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tbdPaneTweets, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chckBoxFollowingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBoxFollowingActionPerformed
        
        if(chckBoxFollowing.isSelected()){
            try {
                ConsoleMain.twitter.createFriendship(user.getId());
            } catch (TwitterException ex) {
                chckBoxFollowing.setSelected(false);
                JOptionPane.showMessageDialog(null, "Failed to follow user!", "Following Failed!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }else{
            try {
                ConsoleMain.twitter.destroyFriendship(user.getId());
            } catch (TwitterException ex) {
                chckBoxFollowing.setSelected(true);
                JOptionPane.showMessageDialog(null, "Failed to unfollow user!", "Unfollowing Failed!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_chckBoxFollowingActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel betaUserStar;
    private javax.swing.JCheckBox chckBoxBlocked;
    private javax.swing.JCheckBox chckBoxFollowing;
    private javax.swing.JCheckBox chckBoxFollows;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBio;
    private javax.swing.JLabel lblFollowerCount;
    private javax.swing.JLabel lblFollowingCount;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblUserConceiveDate;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblWebsite;
    private javax.swing.JLabel profilePictureLabel;
    private javax.swing.JPanel relationshipPanel;
    private javax.swing.JTabbedPane tbdPaneTweets;
    private javax.swing.JList tweetsList;
    private javax.swing.JPanel tweetsPanel;
    private javax.swing.JPanel twitterDetailsPanel;
    private javax.swing.JPanel userDetailsPanel;
    // End of variables declaration//GEN-END:variables
    private HelperRefreshUserTimeline refreshTL = new HelperRefreshUserTimeline(this);
    
    public void init() {
        super.init();
        jTabbedPane1.setBackground(ConsoleLoader.themeReg.getActiveTheme().getCurrentColor());
        userDetailsPanel.setBackground(ConsoleLoader.themeReg.getActiveTheme().getCurrentColor());
        relationshipPanel.setBackground(ConsoleLoader.themeReg.getActiveTheme().getCurrentColor());
        twitterDetailsPanel.setBackground(ConsoleLoader.themeReg.getActiveTheme().getCurrentColor());

        
            
            
            
            final File betaPicture = ResourceDownloader.getResource("beta_user.png");
            final File verifiedPicture = ResourceDownloader.getResource("verified_account.png");
            
               
            new Thread(){
                @Override
                public void run(){
                 
            
                    try {
                        profileImage = ImageIO.read(new URL(user.getProfileImageURL()));
                        betaImage = ImageIO.read(betaPicture);
                        verifiedImage = ImageIO.read(verifiedPicture);
        //                for(String userName : ConsoleMain.BETA_USERS){
        //                    if(userName.equalsIgnoreCase(user.getScreenName())){
        //                        betaUserStar.setIcon(new ImageIcon(betaImage));
        //                        System.out.println("BETA USER!");
        //                    }
        //                }

                        if(kronosUser.isBetaUser()){
                            betaUserStar.setIcon(new ImageIcon(betaImage));

                        }

                        if(user.isVerified()){
                            betaUserStar.setIcon(new ImageIcon(verifiedImage));
                        }
                        profilePictureLabel.setIcon(new ImageIcon(profileImage));

                    } catch (IOException e) {
                        System.out.println("Error getting profile image!");
                        e.printStackTrace();
                    }
                }
            
            }.start();
            
            
            lblUsername.setText(user.getScreenName());
            new Thread(){
                public void run(){
                    if(user.getURL() != null && !user.getURL().isEmpty()){
                        lblWebsite.setText(URLUnshortener.unshorten(user.getURL()));

                    }else{
                        lblWebsite.setText("");
                    }
                }
            }.start();
            lblLocation.setText(user.getLocation());
            lblBio.setText("<html><p>" + user.getDescription() + "</p></html>");
            tweetsList.setModel(tweetModel);
            //populateTweets();
            refreshTL.refreshUserTimeline(user);
            
            this.setVisible(true);
            
            jProgressBar1.setIndeterminate(true);
            
        
        this.tweetsList.addMouseListener(new ViewProfileListAdapter(this));
        setupRelationship();
        setupStats();
    }
    
    public void setupRelationship(){
        // Are you following checkbox
        if(relationship.isSourceFollowingTarget()){
            chckBoxFollowing.setSelected(true);
        }else{
            chckBoxFollowing.setSelected(false);
        }
        
        // Is following you checkbox
        if(relationship.isSourceFollowedByTarget()){
            chckBoxFollows.setSelected(true);
        }else{
            chckBoxFollows.setSelected(false);
        }
        
        // Blocked Checkbox
        if(relationship.isSourceBlockingTarget()){
            chckBoxBlocked.setSelected(true);
        }else{
            chckBoxBlocked.setSelected(false);
        }
        
        // Setup labels
        chckBoxFollowing.setText(chckBoxFollowing.getText().replace("%u", user.getScreenName()));
        chckBoxFollows.setText(chckBoxFollows.getText().replace("%u", user.getScreenName()));
        chckBoxBlocked.setText(chckBoxBlocked.getText().replace("%u", user.getScreenName()));
        try {
            if(user.getId() == ConsoleMain.twitter.getId()){
                chckBoxFollowing.setEnabled(false);
            }
        } catch (TwitterException ex) {
            Logger.getLogger(WindowViewProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(WindowViewProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setupStats(){
        lblFollowerCount.setText(String.valueOf(user.getFollowersCount()));
        lblFollowingCount.setText(String.valueOf(user.getFriendsCount()));
        lblUserConceiveDate.setText(user.getCreatedAt().toString());
        
        
    }

   
    @Deprecated
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

                }
            }

        }else{
            System.out.println("We can not refresh at the time! (Twitter status refresh limit reached!)");
        }
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public int getSelectedStatus() {
        return tweetsList.getSelectedIndex();
    }

    public DefaultListModel getTweetList() {
        return tweetModel;
    }
}
