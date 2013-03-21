package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.FavoriteMenuItemListner;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.ListMouseAdapter;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RTMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RefreshMenuItemListener;
import twitter4j.Status;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;


public class MainGUI extends JFrame{
    private static JFrame frame;
    public static JList dataList;
    public static DefaultListModel list = new DefaultListModel();
    public static Timer tm;
    public static JPopupMenu popUp = new JPopupMenu();
    private static JMenuItem retweetMenuItem = new JMenuItem("RT");
    private static JMenuItem refreshMenuItem = new JMenuItem("Refresh");
    private static JMenuItem favoriteMenuItem = new JMenuItem("Favorite");
    public static ArrayList<Status> statuses = new ArrayList<Status>();


    public MainGUI() {


        frame = new JFrame();
        frame.setTitle("KronosTwit");
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

        retweetMenuItem.addMouseListener(new RTMenuItemListener());
        refreshMenuItem.addMouseListener(new RefreshMenuItemListener());
        favoriteMenuItem.addMouseListener(new FavoriteMenuItemListner());

        popUp.add(retweetMenuItem);
        popUp.add(refreshMenuItem);
        popUp.add(favoriteMenuItem);

        frame.add(scrollPane);
        frame.show();

        tm = new Timer(200000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tm.setRepeats(true);

        tm.start();



    }



    public static void main(String[] args){



    }

    class updateTask extends TimerTask{

        @Override
        public void run() {
            HelperRefreshTimeline.autoUpdate();
        }
    }





}
