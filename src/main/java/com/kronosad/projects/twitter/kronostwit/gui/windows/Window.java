package com.kronosad.projects.twitter.kronostwit.gui.windows;

import com.kronosad.projects.twitter.kronostwit.theme.ThemeDefault;
import java.awt.Component;
import java.awt.Container;
import javax.swing.*;

public abstract class Window extends JFrame{

    private String title;
    private int sizeX, sizeY;

    public Window(String title, int sizeX, int sizeY){

        this.setTitle(title);

        this.setSize(sizeX, sizeY);
        
        
        


    }

    public void init(){
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
            
            comp.setForeground(new ThemeDefault().getCurrentFontColor());
            
        }
        if (comp instanceof Container) {
            Component[] comps = ((Container) comp).getComponents();
            for (int x = 0, y = comps.length; x < y; x++) {
                initFonts(comps[x]);
            }
        }
        
        
    }

    public abstract void close();



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
