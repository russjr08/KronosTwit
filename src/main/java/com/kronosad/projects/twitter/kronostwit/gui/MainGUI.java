package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.*;
import twitter4j.Status;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;


public class MainGUI extends JFrame{
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

        /*try {


            for(Status status : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){

                statuses.add(status);
            }

            for(Status status : statuses){
                list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }*/

        HelperRefreshTimeline.refresh();

        dataList = new JList(list);
        dataList.addMouseListener(new ListMouseAdapter());

        dataList.setBounds(5, 5, 5, 5);
        JScrollPane scrollPane = new JScrollPane(dataList);    // This takes care of adding the dataList object

        scrollPane.setVisible(true);

        viewProfileMenuItem.addMouseListener(new ViewProfileMenuListener());
        retweetMenuItem.addMouseListener(new RTMenuItemListener());
        refreshMenuItem.addMouseListener(new RefreshMenuItemListener());
        favoriteMenuItem.addMouseListener(new FavoriteMenuItemListner());
        newTweetMenuItem.addMouseListener(new NewTweetMenuItemListener());
        replyMenuItem.addMouseListener(new ReplyMenuItemListener());
        
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



    class updateTask extends TimerTask{

        @Override
        public void run() {
            HelperRefreshTimeline.autoUpdate();
            System.out.println("Auto Refresh!");
        }
    }





}
