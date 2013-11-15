/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kronosad.projects.twitter.kronostwit.gui.windows.popup;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.logging.OverridePrintStream;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeDefault;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Russell
 */
public class WindowConsole extends javax.swing.JFrame {
    
    public PrintStream old;
    /**
     * Creates new form WindowConsole
     */
    public WindowConsole() {
        
       
        
     
            
        initComponents();
        System.setOut(new OverridePrintStream(System.out, this));
        System.setErr(new OverridePrintStream(System.err, this));
        this.setVisible(true);
        this.getContentPane().setBackground(new ThemeDefault().getCurrentColor());
        if(!new ThemeDefault().isDaytime){
        lblWarning.setForeground(Color.WHITE);
        }
        
        if(ConsoleLoader.updater.releaseType.toLowerCase().contains("Alpha".toLowerCase()) || ConsoleLoader.updater.releaseType.toLowerCase().contains("Beta".toLowerCase())){
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter(){
                
                @Override
                public void windowClosing(WindowEvent event){
                    // Nothing
                }
                
            });
        }else{
            lblWarning.setText("");

        }
        areaConsole.setEditable(false);
        
    }
    
    public void redraw(){

        SwingUtilities.updateComponentTreeUI(this);
        System.out.println("Console Window has been redrawn...");
        
    }
    
    public void appendToConsole(String appended){
        if(!appended.equalsIgnoreCase("\n")){
            this.areaConsole.append("[KronosTwit]: " + appended + "\n");
        }
        DefaultCaret caret = (DefaultCaret)areaConsole.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        areaConsole.setCaretPosition(areaConsole.getDocument().getLength());
        
    }
    
    public void appendObjectToConsole(Object object){
        this.areaConsole.append(object.toString() + "\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWarning = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaConsole = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblWarning.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblWarning.setText("You are running a dev version of KronosTwit, the console has been forced open.");

        areaConsole.setColumns(20);
        areaConsole.setRows(5);
        jScrollPane1.setViewportView(areaConsole);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(lblWarning)
                .addGap(119, 119, 119))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWarning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaConsole;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblWarning;
    // End of variables declaration//GEN-END:variables
}