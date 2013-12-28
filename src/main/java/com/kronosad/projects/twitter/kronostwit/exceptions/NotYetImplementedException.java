package com.kronosad.projects.twitter.kronostwit.exceptions;

/**
 * User: russjr08
 * Date: 12/26/13
 * Time: 5:12 PM
 */
public class NotYetImplementedException extends Exception {

    public NotYetImplementedException(String message){
        super(message);
    }

    public NotYetImplementedException(){
        super("This feature of KronosTwit has not been implemented yet.");
    }


}
