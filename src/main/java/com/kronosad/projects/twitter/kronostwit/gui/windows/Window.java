package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeDefault;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public abstract class Window extends JFrame{

    private String title;
    private int sizeX, sizeY;

    public Window(String title, int sizeX, int sizeY){

        this.setTitle(title);

        this.setSize(sizeX, sizeY);
        
        ConsoleLoader.windows.add(this);
        
        this.addWindowListener(new WindowListener(){

            public void windowOpened(WindowEvent we) {}

            public void windowClosing(WindowEvent we) {
                close();
            }

            public void windowClosed(WindowEvent we) {
                close();
            }

            public void windowIconified(WindowEvent we) {}

            public void windowDeiconified(WindowEvent we) {}

            public void windowActivated(WindowEvent we) {}
            
            public void windowDeactivated(WindowEvent we) {}
            
            
            
            
            
        });
        
        
        


    }

    public void init(){
        
        this.getContentPane().setBackground(ConsoleLoader.themeReg.getActiveTheme().getCurrentColor());
        initFonts(this);
    }
    
    protected void initFonts(Component comp) {
//        for(Component component : this.getContentPane().getComponents()){
//            if(component instanceof JLabel){
//                component.setForeground(new ThemeDefault().getCurrentFontColor());
//            }
//            
//        }
//        
//        this.getContentPane().setBackground(new ThemeDefault().getCurrentColor());
        
        if (comp instanceof JLabel) {
            
            comp.setForeground(ConsoleLoader.themeReg.getActiveTheme().getCurrentFontColor());
            
        }
        if (comp instanceof Container) {
            Component[] comps = ((Container) comp).getComponents();
            for (int x = 0, y = comps.length; x < y; x++) {
                initFonts(comps[x]);
            }
        }
        
        
    }

    public void close(){
        ConsoleLoader.windows.remove(this);
    }
    
    public void recolor(){
        this.getContentPane().setBackground(ConsoleLoader.themeReg.getActiveTheme().getCurrentColor());
        initFonts(this);
        SwingUtilities.updateComponentTreeUI(this);
    }



    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
        this.setSize(this.sizeX, this.sizeY);
    }

    public int getSizeY() {
        return sizeY;

    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
        this.setSize(this.sizeX, this.sizeY);

    }
}
