package com.kronosad.projects.twitter.kronostwit.utils;


import java.awt.*;
import java.lang.reflect.Method;

/**
 * User: russjr08
 * Date: 12/26/13
 * Time: 5:41 PM
 */
public class OSUtils {

    /**
     * @param window
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void enableOSXFullscreen(Window window) {
        if(System.getProperty("os.name").contains("OS X")){
            try {
                Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
                Class params[] = new Class[]{Window.class, Boolean.TYPE};
                Method method = util.getMethod("setWindowCanFullScreen", params);
                method.invoke(util, window, true);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
