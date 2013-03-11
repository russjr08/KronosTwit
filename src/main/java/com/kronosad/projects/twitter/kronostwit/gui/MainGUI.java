package com.kronosad.projects.twitter.kronostwit.gui;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
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
    private static JPanel customPanel;
    private static JFrame frame;
    private JList list1;
    private JList dataList;
    private JPanel panel1;
    public static DefaultListModel list = new DefaultListModel();


    public MainGUI() {


        frame = new JFrame();
        frame.setName("Window");
        frame.setBounds(0, 500, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Paging paging = new Paging(1, 80);
        try {
            for(Status status : ConsoleMain.twitter.getHomeTimeline(paging)){
                list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        dataList = new JList(list);

        dataList.setBounds(5, 5, 5, 5);
        JScrollPane scrollPane = new JScrollPane(dataList);    // This takes care of adding the dataList object

        scrollPane.setVisible(true);
        frame.add(scrollPane);
        frame.show();

        final Timer tm = new Timer(100000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Status Update Performed.");


                list.clear();

                try {
                    for(Status status : ConsoleMain.twitter.getHomeTimeline(new Paging(1, 80))){
                        list.addElement(String.format("[%s]%s: %s", status.getCreatedAt(), status.getUser().getScreenName(), status.getText()));

                    }
                } catch (TwitterException exception) {
                    exception.printStackTrace();
                }
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

        }
    }





}
