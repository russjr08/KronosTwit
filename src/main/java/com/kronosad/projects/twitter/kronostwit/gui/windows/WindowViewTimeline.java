/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.DeleteMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.FavoriteMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.NewTweetMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RTMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RefreshMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.ReplyMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.ViewProfileMenuListener;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.util.Timer;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import twitter4j.TwitterException;
import org.apache.commons.io.*;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author Russell
 */
public class WindowViewTimeline extends Window implements IStatus {
    private DefaultListModel tweetsList = new DefaultListModel();
    private ArrayList<Status> statuses = new ArrayList<Status>();
    private User user;
    protected HelperRefreshTimeline refresh = new HelperRefreshTimeline(this);
    protected JPopupMenu popUp = new JPopupMenu();
    protected static JMenuItem viewProfileMenuItem = new JMenuItem("View %u's Profile");
    protected static JMenuItem newTweetMenuItem = new JMenuItem("New Tweet");
    protected static JMenuItem retweetMenuItem = new JMenuItem("RT");
    protected static JMenuItem refreshMenuItem = new JMenuItem("Refresh");
    protected static JMenuItem favoriteMenuItem = new JMenuItem("Favorite");
    protected static JMenuItem replyMenuItem = new JMenuItem("Reply to %u");
    protected static JMenuItem deleteMenuItem = new JMenuItem("Delete");
    private static JSeparator separator = new JSeparator();

    
    /**
     * Creates new form WindowViewTimeline
     */
    public WindowViewTimeline(String title, int sizeX, int sizeY) {
        super(title, sizeX, sizeY);
        try {
            this.user = ConsoleMain.twitter.showUser(ConsoleMain.twitter.getId());
            initComponents();
            init();
            
        } catch (TwitterException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        userPictureLbl = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        timelinePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tweetsView = new javax.swing.JList();
        lblBanner = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tweetsView.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(tweetsView);

        javax.swing.GroupLayout timelinePanelLayout = new javax.swing.GroupLayout(timelinePanel);
        timelinePanel.setLayout(timelinePanelLayout);
        timelinePanelLayout.setHorizontalGroup(
            timelinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        timelinePanelLayout.setVerticalGroup(
            timelinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Timeline", timelinePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userPictureLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(421, 421, 421)
                        .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jTabbedPane1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userPictureLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JPanel timelinePanel;
    protected javax.swing.JList tweetsView;
    private javax.swing.JLabel userPictureLbl;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
        try {
            loadGreetings();
            //displayBanner();
            displayProfilePicture();
            
        } catch (IOException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tweetsView.setModel(tweetsList);
        
        System.out.println("Attempting to load timeline...");
        
        
        //initTimer();
        loadTimeline();
        setupAdapters();
        
        this.setVisible(true);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void loadGreetings() throws IOException, TwitterException{
        User user = ConsoleMain.twitter.showUser(ConsoleMain.twitter.getId());
        File file = new File("greetings.txt");
       
        List<String> lines = FileUtils.readLines(file);
        
        Random random = new Random();
        
        int randomGreeting = random.nextInt(lines.size());
        
        this.setTitle(lines.get(randomGreeting).replaceAll("%u", user.getName()));
        
       
        
        
        
    }
    
    @Deprecated
    public void displayBanner() throws TwitterException, MalformedURLException, IOException{
        URL bannerImageURL = null;
        bannerImageURL = new URL(ConsoleMain.twitter.showUser(ConsoleMain.twitter.getId()).getProfileBannerURL());
        
        Image bannerImage = ImageIO.read(bannerImageURL);
        
        lblBanner.setIcon(new ImageIcon(bannerImage));
        
        
    }
    
    public void displayProfilePicture() throws MalformedURLException, IOException{
        URL profilePictureURL = null;
        profilePictureURL = new URL(user.getProfileImageURL());
        
        Image profileImage = ImageIO.read(profilePictureURL);
        
        userPictureLbl.setIcon(new ImageIcon(profileImage));
        
        
    }
    
    public void loadTimeline(){
        
        System.out.println("Load Timeline Method called!");
        refresh.refreshTimeline();
    }
    
    public void setupAdapters(){
        tweetsView.addMouseListener(new ViewTimelineListAdapter(this));
        
        viewProfileMenuItem.addMouseListener(new ViewProfileMenuListener(this));
        retweetMenuItem.addMouseListener(new RTMenuItemListener(this));
        refreshMenuItem.addMouseListener(new RefreshMenuItemListener(this));
        favoriteMenuItem.addMouseListener(new FavoriteMenuItemListener(this));
        replyMenuItem.addMouseListener(new ReplyMenuItemListener(this));
        newTweetMenuItem.addMouseListener(new NewTweetMenuItemListener(this));
        deleteMenuItem.addMouseListener(new DeleteMenuItemListener(this));
        
        popUp.add(viewProfileMenuItem);
        popUp.add(newTweetMenuItem);
        popUp.add(refreshMenuItem);
        popUp.add(separator);
        popUp.add(deleteMenuItem);
        popUp.add(favoriteMenuItem);
        popUp.add(replyMenuItem);
        popUp.add(retweetMenuItem);
        
        

        
    }
    
    public void initTimer(){

        Timer timer = new Timer("Auto update Timer");
        
        updateTask update = new updateTask(this);
        
        timer.schedule(update, 0, 300000);
        
        System.out.println("Auto update timer scheduled.");
       
    }
    
    
    
    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public int getSelectedStatus() {
        return tweetsView.getSelectedIndex();
    }

    public DefaultListModel getTweetList() {
        return tweetsList; 
    }
    
    
}

class updateTask extends TimerTask{
        WindowViewTimeline timeline;
        public updateTask(WindowViewTimeline timeline){
            this.timeline = timeline;
        }
        @Override
        public void run() {
            timeline.refresh.refreshTimeline();
            System.out.println("Auto Refresh!");
        }
    }
