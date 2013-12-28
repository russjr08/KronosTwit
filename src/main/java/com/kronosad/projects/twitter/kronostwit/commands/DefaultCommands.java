package com.kronosad.projects.twitter.kronostwit.commands;

/**
 * User: russjr08
 * Date: 12/26/13
 * Time: 3:13 PM
 */

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.interfaces.ICommand;

import java.util.ArrayList;

/**
 * This class adds default 'short' commands, such as a 'versionNumber' command.
 * Beefy commands should still be made as their own class files (which extend
 * {@link com.kronosad.projects.twitter.kronostwit.interfaces.ICommand}).
 */
public class DefaultCommands {

    private static ArrayList<ICommand> commands = new ArrayList<ICommand>();

    public static void fabricateDefaultCommands(){
        commands.add(new ICommand() {
            @Override
            public String getName() {
                return "version";
            }

            @Override
            public boolean execute(ArrayList<String> args) {
                System.out.println(String.format("You are running KronosTwit v%s (%s)", ConsoleLoader.updater.versionNumber, ConsoleLoader.updater.getVersion().getReleaseType()));

                return true;
            }

            @Override
            public String getHelp() {
                return null;
            }
        });



        CommandRegistry.registerCommands(commands);
    }


}
