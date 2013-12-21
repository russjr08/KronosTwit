package com.kronosad.projects.twitter.kronostwit.data;

import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import twitter4j.Status;

import java.io.*;
import java.util.ArrayList;

/**
 * User: russjr08
 * Date: 12/20/13
 * Time: 7:39 PM
 */
public class SerializeUtils {

    /**
     * De-serializes statuses that were serialized with the APIs serialize method.
     * @see com.kronosad.projects.twitter.kronostwit.data.SerializeUtils#serializeStatuses(com.kronosad.projects.twitter.kronostwit.interfaces.IStatus)
     * @param status The instance of the class that implements IStatus.
     * @return An ArrayList of statuses {@link com.kronosad.projects.twitter.kronostwit.interfaces.IStatus}
     * @throws IOException In case the file was not found.
     * @throws ClassNotFoundException Should only happen if Twitter4J is not on the classpath.
     */
    public static ArrayList<Status> getSerializedStatuses(IStatus status) throws IOException, ClassNotFoundException {
        System.out.println("Attempting to unserialize statuses for: " + status.getClass().getName());
        File file = new File("persistence" + File.separator + status.getClass().getName() + File.separator + "tweets.ser");

        if(file.exists()){
            FileInputStream inStream = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(inStream);

            @SuppressWarnings("unchecked")
            ArrayList<Status> statuses = (ArrayList<Status>)objectInput.readObject();
            objectInput.close();
            inStream.close();
            return statuses;


        }
        throw new FileNotFoundException("Serialized statuses not found.");
    }

    /**
     * Allows any class that has Twitter statuses AND uses the KronosTwit API to serialize their Tweets.
     * @see com.kronosad.projects.twitter.kronostwit.data.SerializeUtils#getSerializedStatuses(com.kronosad.projects.twitter.kronostwit.interfaces.IStatus)
     * @param status
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void serializeStatuses(IStatus status) throws IOException, ClassNotFoundException{
        File file = new File("persistence" + File.separator + status.getClass().getName() + File.separator + "tweets.ser");

        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        FileOutputStream inStream = new FileOutputStream(file);
        ObjectOutputStream objectStream = new ObjectOutputStream(inStream);

        objectStream.writeObject(status.getStatuses());

        objectStream.close();
        inStream.close();

    }

}
