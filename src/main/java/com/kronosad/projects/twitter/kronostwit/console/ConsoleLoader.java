/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.console;

/**
 * Does all tasks that need to be performed before any heavy code / javax / swing / awt code is triggered.
 * @author russjr08
 */
public class ConsoleLoader {
    
    public static void main(String[] args){
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KronosTwit - Beta");
        ConsoleMain.load();
    }
    
}
