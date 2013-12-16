/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.twitter.kronostwit.commands;

import java.util.ArrayList;

/**
 *
 * @author russjr08
 */
public class CommandRegistry {
    
    private static ArrayList<ICommand> commands = new ArrayList<ICommand>();

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
        return false;
    }

    
    
}
