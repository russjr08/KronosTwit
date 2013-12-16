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
public interface ICommand {

    /**
     * Gets the name of the command.
     * @return Name of command
     */
    public String getName();

    /**
     * Runs the command
     * @param args Arguments for command
     * @return Whether the supplied arguments were valid
     */
    public boolean execute(ArrayList<String> args);

    /**
     * The help text of this command.
     * @return Help text.
     */
    public String getHelp();
    
    
}
