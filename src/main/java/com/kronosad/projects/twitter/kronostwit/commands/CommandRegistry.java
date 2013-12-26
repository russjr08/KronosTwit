
package com.kronosad.projects.twitter.kronostwit.commands;

import com.kronosad.projects.twitter.kronostwit.interfaces.ICommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author russjr08
 */
public class CommandRegistry {
    
    private static ArrayList<ICommand> commands = new ArrayList<ICommand>();
    public static boolean devMode = false;

    /**
     * Adds a new command to the database.
     * @param command An instance of ICommand.
     */
    public static void registerCommand(ICommand command){
        commands.add(command);
    }

    /**
     * Attempts to run the specified command.
     * @param cmdString The command AND the arguments. Basically what the user inputted.
     * @return Whether the command was found or not.
     */
    public static boolean runCommand(String cmdString){
        String[] parts = cmdString.split(" ");

        if(!devMode){
            for(ICommand command : commands){
                if(command.getName().equalsIgnoreCase(parts[0])){
                    ArrayList<String> args = new ArrayList<String>();

                    for(String part : parts){
                        if(!part.equalsIgnoreCase(parts[0])){
                            args.add(part);
                        }
                    }

                    if(!command.execute(args)){
                        System.out.println(command.getHelp());
                    }
                    return true;
                }
            }
        }else{
            // Stuff for dev mode
            try {
                Class clazz = Class.forName(parts[0]);
                for(Method method : clazz.getMethods()){
                    if(method.getName().equalsIgnoreCase(parts[1])){
                        if(parts.length > 3){
                            ArrayList<String> args = new ArrayList<String>();
                            for(String part : parts){
                                if(!part.equals(parts[0]) && !part.equals(parts[1])){
                                    args.add(part);
                                }
                            }
                            Object object = method.invoke(method, args);
                            System.out.println(object.toString());
                        }
                        if(parts.length == 3){
                            Object object = method.invoke(method, parts[2]);
                            if(object != null){
                                System.out.println(object.toString());
                            }
                        }
                        else{
                            Object object = method.invoke(method);
                            if(object != null){
                                System.out.println(object.toString());
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    
    
}
