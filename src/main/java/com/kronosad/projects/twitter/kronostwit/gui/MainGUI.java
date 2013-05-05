package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.*;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.Status;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;


public class MainGUI extends JFrame implements IStatus{
    private static JFrame frame;
    private static JTextField tweetField = new JTextField();
    public static JList dataList;
    public static DefaultListModel list = new DefaultListModel();
    public static Timer tm;
    public static JPopupMenu popUp = new JPopupMenu();
    public static JMenuItem viewProfileMenuItem = new JMenuItem("View %u's Profile");
    private static JMenuItem newTweetMenuItem = new JMenuItem("New Tweet");
    public static JMenuItem retweetMenuItem = new JMenuItem("RT");
    private static JMenuItem refreshMenuItem = new JMenuItem("Refresh");
    public static JMenuItem favoriteMenuItem = new JMenuItem("Favorite");
    public static JMenuItem replyMenuItem = new JMenuItem("Reply to %u");
    private static JSeparator separator = new JSeparator();
    public static ArrayList<Status> statuses = new ArrayList<Status>();
    
    public MainGUI() {


        frame = new JFrame();
        frame.setTitle("KronosTwit - Beta");
        frame.setBounds(0, 500, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        HelperRefreshTimeline refreshTL = new HelperRefreshTimeline(this);
        refreshTL.refreshTimeline();

        dataList = new JList(list);
        dataList.addMouseListener(new ListMouseAdapter());

        dataList.setBounds(5, 5, 5, 5);
        JScrollPane scrollPane = new JScrollPane(dataList);    // This takes care of adding the dataList object

        scrollPane.setVisible(true);

        retweetMenuItem.addMouseListener(new RTMenuItemListener(this));
        refreshMenuItem.addMouseListener(new RefreshMenuItemListener(this));
        favoriteMenuItem.addMouseListener(new FavoriteMenuItemListener(this));
        newTweetMenuItem.addMouseListener(new NewTweetMenuItemListener(this));
        replyMenuItem.addMouseListener(new ReplyMenuItemListener(this));
        viewProfileMenuItem.addMouseListener(new ViewProfileMenuListener(this));
        
        popUp.add(viewProfileMenuItem);
        popUp.add(newTweetMenuItem);
        popUp.add(refreshMenuItem);
        popUp.add(separator);
        popUp.add(favoriteMenuItem);
        popUp.add(replyMenuItem);
        popUp.add(retweetMenuItem);


        frame.add(tweetField);
        frame.add(scrollPane);

        frame.setVisible(true);

        tm = new Timer(200000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        tm.setRepeats(true);

        tm.start();



    }
    
    @Override
    public ArrayList<Status> getStatuses(){
        return statuses;
    }
    
    @Override
    public int getSelectedStatus(){
        
        return dataList.getSelectedIndex();
    }
    
    @Override
    public DefaultListModel getTweetList() {
        return list;
    }



    class updateTask extends TimerTask{

        @Override
        public void run() {
            HelperRefreshTimeline.autoUpdate();
            System.out.println("Auto Refresh!");
        }
    }





}
