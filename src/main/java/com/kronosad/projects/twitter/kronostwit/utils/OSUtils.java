package com.kronosad.projects.twitter.kronostwit.utils;


import com.apple.eawt.Application;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * User: russjr08
 * Date: 12/26/13
 * Time: 5:41 PM
 */
public class OSUtils {


    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void performMacOps() {
        if(System.getProperty("os.name").contains("OS X")){
            System.out.println("Running OS X Ops");

            try {
                Class util = Class.forName("com.apple.eawt.FullScreenUtilities");

                Class params[] = new Class[]{Window.class, Boolean.TYPE};

                Image image = new ImageIcon(new URL("http://api.kronosad.com/KronosTwit/resources/bird_blue_hi_res.png")).getImage();
                Application.getApplication().setDockIconImage(image);

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("End OS X OPs");
        }
    }

}
