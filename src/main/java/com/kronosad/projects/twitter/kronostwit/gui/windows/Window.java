package com.kronosad.projects.twitter.kronostwit.gui.windows;

import javax.swing.*;

public abstract class Window extends JFrame{

    private String title;
    private int sizeX, sizeY;

    public Window(String title, int sizeX, int sizeY){

        this.setTitle(title);

        this.setSize(sizeX, sizeY);



    }

    public abstract void init();

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
