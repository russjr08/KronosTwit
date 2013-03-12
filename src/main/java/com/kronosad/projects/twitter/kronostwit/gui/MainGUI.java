package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperRefreshTimeline;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.ListMouseAdapter;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RTMenuItemListener;
import com.kronosad.projects.twitter.kronostwit.gui.listeners.RefreshMenuItemListener;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.swing.*;
import javax.swing.table.TableColumn;
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
    public static ArrayList<Status> statuses = new ArrayList<Status>();


    public MainGUI() {


        frame = new JFrame();
        frame.setName("Window");
        frame.setBounds(0, 500, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Paging paging = new Paging(1, 80);
        try {
            /*for(Status status : ConsoleMain.twitter.getHomeTimeline(paging)){
                list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }*/

            for(Status status : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){

                statuses.add(status);
            }

            for(Status status : statuses){
                list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

            }
        } catch (TwitterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        dataList = new JList(list);
        dataList.addMouseListener(new ListMouseAdapter());

        dataList.setBounds(5, 5, 5, 5);
        JScrollPane scrollPane = new JScrollPane(dataList);    // This takes care of adding the dataList object

        scrollPane.setVisible(true);

        retweetMenuItem.addMouseListener(new RTMenuItemListener());
        refreshMenuItem.addMouseListener(new RefreshMenuItemListener());

        popUp.add(retweetMenuItem);
        popUp.add(refreshMenuItem);

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
