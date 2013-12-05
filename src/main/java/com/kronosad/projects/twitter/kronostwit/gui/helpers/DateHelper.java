package com.kronosad.projects.twitter.kronostwit.gui.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: russjr08
 * Date: 12/4/13
 * Time: 8:31 PM
 */
public class DateHelper {

    public static String getTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");

        return format.format(date);
    }


}
