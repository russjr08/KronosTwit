package com.kronosad.projects.twitter.kronostwit.commands;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleMain;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.URLUnshortener;
import com.kronosad.projects.twitter.kronostwit.interfaces.ICommand;

import java.util.ArrayList;

/**
 * User: russjr08
 * Date: 12/16/13
 * Time: 1:59 AM
 */
public class CommandUnshortenURL implements ICommand {

    @Override
    public String getName() {
        return "unshorten";
    }

    @Override
    public boolean execute(ArrayList<String> args) {
        if(ConsoleMain.arguments.contains("-nounshorten")){
            System.out.println("Unshortening is disabled, remove -unshorten from the program arguments to use.");
            return true;
        }
        for(String url : args){
            String unshortened = URLUnshortener.unshorten(url);
            System.out.println(String.format("%s => %s", url, unshortened));
        }

        if(args.size() > 0){
            return true;
        }

        return false;
    }

    @Override
    public String getHelp() {
        return "Unshortens every URL supplied as an argument.";
    }
}
