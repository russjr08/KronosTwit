package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.MainGUI;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshUserTimeline;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import twitter4j.Relationship;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;



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
    
    private static Relationship following;

    
    
    private ArrayList<Status> statuses = new ArrayList<Status>();
    private DefaultListModel tweetModel = new DefaultListModel();
    
    /**
     * Creates new form WindowViewProfile
     */
    public WindowViewProfile(String title, int SizeX, int SizeY, User user, Twitter twitter) {
        super(title, SizeX, SizeY);
        try {
            this.following = ConsoleMain.twitter.showFriendship(ConsoleMain.twitter.getId(), user.getId());
        } catch (TwitterException ex) {
            Logger.getLogger(WindowViewProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jProgressBar1 = new javax.swing.JProgressBar();
        btnFollow = new javax.swing.JButton();
        betaUserStar = new javax.swing.JLabel();

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
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addContainerGap())
        );
        tweetsPanelLayout.setVerticalGroup(
            tweetsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tweetsPanelLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbdPaneTweets.addTab("Tweets", tweetsPanel);

        bioLblTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnFollow.setText("Follow");
        btnFollow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFollowActionPerformed(evt);
            }
        });

        betaUserStar.setToolTipText("Beta User");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(tbdPaneTweets, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .add(profilePictureLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(lblUsername, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(layout.createSequentialGroup()
                                        .add(lblWebsite, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(13, 13, 13))
                                    .add(layout.createSequentialGroup()
                                        .add(lblLocation, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(8, 8, 8))
                                    .add(layout.createSequentialGroup()
                                        .add(lblBio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(43, 43, 43)))
                                .add(18, 18, 18)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(lblWebsiteTxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .add(layout.createSequentialGroup()
                                                .add(lblUsernameField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(2, 2, 2))
                                            .add(layout.createSequentialGroup()
                                                .add(lblLocationText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(6, 6, 6)))
                                        .add(119, 119, 119)
                                        .add(betaUserStar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(59, 59, 59)
                                        .add(btnFollow)
                                        .add(0, 0, Short.MAX_VALUE))))
                            .add(bioLblTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 304, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(54, 54, 54))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(lblUsername)
                                .add(lblUsernameField))
                            .add(18, 18, 18)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(lblWebsite)
                                .add(lblWebsiteTxt))
                            .add(56, 56, 56))
                        .add(profilePictureLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(betaUserStar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblLocation)
                    .add(lblLocationText))
                .add(14, 14, 14)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblBio)
                    .add(btnFollow))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(bioLblTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(tbdPaneTweets, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFollowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFollowActionPerformed
        if(following.isSourceFollowingTarget()){
            try {
                ConsoleMain.twitter.destroyFriendship(user.getId());
            } catch (TwitterException ex) {
                JOptionPane.showMessageDialog(null, "Could not unfollow user!", "Error", JOptionPane.ERROR_MESSAGE);

            }finally{
                btnFollow.setText("Follow");

            }
            
        }else{
            try {
                ConsoleMain.twitter.createFriendship(user.getId());
            } catch (TwitterException ex) {
                JOptionPane.showMessageDialog(null, "Could not follow user!", "Error", JOptionPane.ERROR_MESSAGE);
            }finally{
                btnFollow.setText("Unfollow");

            }
        }
        
    }//GEN-LAST:event_btnFollowActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel betaUserStar;
    private javax.swing.JLabel bioLblTxt;
    private javax.swing.JButton btnFollow;
    private javax.swing.JProgressBar jProgressBar1;
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
    private HelperRefreshUserTimeline refreshTL = new HelperRefreshUserTimeline(this);
    
    public void init() {
        try {
            if(this.user.getScreenName().equalsIgnoreCase(ConsoleMain.twitter.getScreenName())){
                btnFollow.setEnabled(false);
            }
            
            URL profileImageURL = null;
            File betaPicture = new File("beta_user.png");
            File verifiedPicture = new File("verified_account.png");
            try {
                profileImageURL = new URL(user.getProfileImageURL());
                
            } catch (MalformedURLException e) {
                System.out.println("Error getting URL of profile image");
                e.printStackTrace();
            }

            try {
                profileImage = ImageIO.read(profileImageURL);
                betaImage = ImageIO.read(betaPicture);
                verifiedImage = ImageIO.read(verifiedPicture);
                for(String userName : ConsoleMain.BETA_USERS){
                    if(userName.equalsIgnoreCase(user.getScreenName())){
                        betaUserStar.setIcon(new ImageIcon(betaImage));
                        System.out.println("BETA USER!");
                    }
                }
                if(user.isVerified()){
                    betaUserStar.setIcon(new ImageIcon(verifiedImage));
                }
                profilePictureLabel.setIcon(new ImageIcon(profileImage));

            } catch (IOException e) {
                System.out.println("Error getting profile image!");
                e.printStackTrace();
            }
            
            lblUsernameField.setText(user.getScreenName());
            lblWebsiteTxt.setText(user.getURL());
            lblLocationText.setText(user.getLocation());
            bioLblTxt.setText("<html><p>" + user.getDescription() + "</p></html>");
            tweetsList.setModel(tweetModel);
            //populateTweets();
            refreshTL.refreshUserTimeline(user);
            
            this.setVisible(true);
            
            jProgressBar1.setIndeterminate(true);
            
            if(following.isSourceFollowingTarget()){
                btnFollow.setText("Unfollow");
            }else{
                btnFollow.setText("Follow");
            }
        } catch (TwitterException ex) {
            Logger.getLogger(WindowViewProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(WindowViewProfile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); 
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
