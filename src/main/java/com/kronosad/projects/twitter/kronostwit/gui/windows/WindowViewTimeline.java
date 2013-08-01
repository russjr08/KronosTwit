/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.MenuBarHelper;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.DeleteMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.FavoriteMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.NewTweetMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RTMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RefreshMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.ReplyMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.StreamStatusListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.ViewProfileMenuListener;
import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowNewTweet;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeDefault;
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
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import twitter4j.TwitterException;
import org.apache.commons.io.*;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author Russell
 */
public class WindowViewTimeline extends Window implements IStatus {
    public DefaultListModel tweetsList = new DefaultListModel();
    public DefaultListModel mentionsList = new DefaultListModel();
    public static DefaultListModel searchList = new DefaultListModel();
    public ArrayList<Status> statuses = new ArrayList<Status>();
    public ArrayList<Status> mentions = new ArrayList<Status>();
    public static ArrayList<Status> searches = new ArrayList<Status>();
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
    private static boolean isMentionsSelected = false;
    
    public static JMenuBar menuBar = new JMenuBar();

    
    /**
     * Creates new form WindowViewTimeline
     */
    public WindowViewTimeline(String title, int sizeX, int sizeY) {
        super(title, sizeX, sizeY);
        this.setLocationRelativeTo(null);
        MenuBarHelper.initMenu();
        this.setJMenuBar(menuBar);
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
        tweetsTabbedPane = new javax.swing.JTabbedPane();
        timelinePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tweetsView = new javax.swing.JList();
        mentionsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mentionsView = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        searchView = new javax.swing.JList();
        lblBanner = new javax.swing.JLabel();
        lblQuickActions = new javax.swing.JLabel();
        btnNewTweet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tweetsTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tweetsTabbedPaneMouseClicked(evt);
            }
        });

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );
        timelinePanelLayout.setVerticalGroup(
            timelinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );

        tweetsTabbedPane.addTab("Timeline", timelinePanel);

        mentionsView.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(mentionsView);

        javax.swing.GroupLayout mentionsPanelLayout = new javax.swing.GroupLayout(mentionsPanel);
        mentionsPanel.setLayout(mentionsPanelLayout);
        mentionsPanelLayout.setHorizontalGroup(
            mentionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );
        mentionsPanelLayout.setVerticalGroup(
            mentionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );

        tweetsTabbedPane.addTab("Mentions", mentionsPanel);

        jScrollPane3.setViewportView(searchView);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );

        tweetsTabbedPane.addTab("Search", jPanel1);

        lblQuickActions.setText("Quick Actions");

        btnNewTweet.setText("New Tweet");
        btnNewTweet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTweetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userPictureLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewTweet)
                        .addGap(46, 46, 46)
                        .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(tweetsTabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(lblQuickActions)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userPictureLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblQuickActions)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNewTweet)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tweetsTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewTweetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTweetActionPerformed

        System.out.println("Calling new Tweet window");
        WindowNewTweet newTweet = new WindowNewTweet("New Tweet", 500, 500, this);

    }//GEN-LAST:event_btnNewTweetActionPerformed

    private void tweetsTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tweetsTabbedPaneMouseClicked
        if(tweetsTabbedPane.getSelectedIndex() == 1){
            isMentionsSelected = true;
            System.out.println("Mentions Tab Selected...");
        }else if(tweetsTabbedPane.getSelectedIndex() == 2){
            String query = JOptionPane.showInputDialog(null, "What would you like to search for?", "Enter your Query", JOptionPane.QUESTION_MESSAGE);
            // Duplicate Code
//            isMentionsSelected = false;
//            searchList.removeAllElements();
//            searches.clear();
//            QueryResult queryResult = null;
//            try {
//                Query searchQuery = new Query(query);
//                searchQuery.setCount(100);
//                queryResult = ConsoleMain.twitter.search(searchQuery);
//            } catch (TwitterException ex) {
//                Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
//            }finally{
//                for(Status status : queryResult.getTweets()){
//                    searches.add(0,status);
//                    searchList.add(0, String.format("[%s]%s:\n %s", status.getCreatedAt(), status.getUser().getName(), status.getText()));
//                    
//                }
//                
//            }
            search(query);
            
        }

    }//GEN-LAST:event_tweetsTabbedPaneMouseClicked

    public static void search(String query){
        isMentionsSelected = false;
        searchList.removeAllElements();
        searches.clear();
        tweetsTabbedPane.setSelectedIndex(2);
        QueryResult queryResult = null;
            try {
                Query searchQuery = new Query(query);
                searchQuery.setCount(100);
                queryResult = ConsoleMain.twitter.search(searchQuery);
            } catch (TwitterException ex) {
                Logger.getLogger(WindowViewTimeline.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                for(Status status : queryResult.getTweets()){
                    searches.add(status);
                    searchList.addElement(String.format("[%s]%s:\n %s", status.getCreatedAt(), status.getUser().getName(), status.getText()));
                    
                }
                
            }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewTweet;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JLabel lblQuickActions;
    private javax.swing.JPanel mentionsPanel;
    public javax.swing.JList mentionsView;
    private javax.swing.JList searchView;
    private javax.swing.JPanel timelinePanel;
    private static javax.swing.JTabbedPane tweetsTabbedPane;
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
        mentionsView.setModel(mentionsList);
        searchView.setModel(searchList);
        
        System.out.println("Attempting to load timeline...");
        
        
        //initTimer();
        loadTimeline();
        setupAdapters();
        
        this.setVisible(true);
        final TwitterStream stream = new TwitterStreamFactory().getInstance();
        stream.addListener(new StreamStatusListener(this));
        stream.setOAuthConsumer(ConsoleMain.consumerKey, ConsoleMain.consumerSecret);
        stream.user();
        
        Runtime.getRuntime().addShutdownHook(new Thread()
{
            @Override
            public void run()
            {
                stream.shutdown();
                System.out.println("Closing App!");
            }
        });
        this.setBackground(new ThemeDefault().getCurrentColor());
        
        
        
    }

    @Override
    public void close() {
        
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
        refresh.refreshTimelineFirstTime();
    }

    public static boolean isIsMentionsSelected() {
        return isMentionsSelected;
    }
    
    
    
    public void setupAdapters(){
        tweetsView.addMouseListener(new ViewTimelineListAdapter(this));
        mentionsView.addMouseListener(new ViewTimelineListAdapter(this));
        searchView.addMouseListener(new ViewTimelineListAdapter(this));
        
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
    @Deprecated
    public void initTimer(){

        Timer timer = new Timer("Auto update Timer");
        
        updateTask update = new updateTask(this);
        
        timer.schedule(update, 0, 300000);
        
        System.out.println("Auto update timer scheduled.");
       
    }
    
    
    
    public ArrayList<Status> getStatuses() {
        
//        if(isMentionsSelected){
//            return mentions;
//        }else{
//            return statuses;
//        }
        
        switch(tweetsTabbedPane.getSelectedIndex()){
            case 0:
                return statuses;
            case 1:
                return mentions;
            case 2:
                return searches;
            default:
                throw new IllegalArgumentException("Invalid Tab Selected!");
                
        }
        
    }

    public int getSelectedStatus() {
//        if(isMentionsSelected){
//            return mentionsView.getSelectedIndex();
//        }else{
//            return tweetsView.getSelectedIndex();
//        }
          switch(tweetsTabbedPane.getSelectedIndex()){
              case 0:
                  return tweetsView.getSelectedIndex();
              case 1:
                  return mentionsView.getSelectedIndex();
              case 2:
                  return searchView.getSelectedIndex();
              default:
                  throw new IllegalArgumentException("Invalid Tab Selected!");
          }
        
        
    }

    public DefaultListModel getTweetList() {
//        if(isMentionsSelected){
//            return mentionsList;
//        }else{
//            return tweetsList; 
//        }
        
        switch(tweetsTabbedPane.getSelectedIndex()){
            case 0:
                return tweetsList;
            case 1:
                return mentionsList;
            case 2:
                return searchList;
            default:
                throw new IllegalArgumentException("Invalid Tab Selected!");
        }
    }
    
    
    
    
}
@Deprecated
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
