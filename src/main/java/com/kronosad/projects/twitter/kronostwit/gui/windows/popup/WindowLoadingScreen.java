/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows.popup;

import com.kronosad.projects.twitter.kronostwit.gui.windows.Window;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeDefault;

/**
 *
 * @author Russell
 */

public class WindowLoadingScreen extends Window {
    
    
    
    /**
     * Creates new form WindowLoadingScreen
     */
    public WindowLoadingScreen(String title, int sizeX, int sizeY) {
        super(title, sizeX, sizeY);
       
        initComponents();
        init();
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        progressStatus = new javax.swing.JProgressBar();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("KronosTwit is now loading!");

        lblStatus.setText("Loading Initial Code");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(progressStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblStatus)
                        .addGap(159, 159, 159))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(progressStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JProgressBar progressStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {

        this.setTitle("Loading Application!");
        ThemeDefault defaultTheme = new ThemeDefault();
        this.getContentPane().setBackground(defaultTheme.getCurrentColor());
        
    }

    @Override
    public void close() {

        
    
    }
    
    public void initialCode(){
        progressStatus.setValue(10);
        lblStatus.setText("Loading Inital Code...");
    }
    
    public void grabbingData(){
        progressStatus.setValue(25);
        lblStatus.setText("Grabbing Data from KronosAD server");
        
    }
    
    public void checkUpdate(){
        progressStatus.setValue(30);
        lblStatus.setText("Checking for Updates...");
    }
    
    public void checkUser(){
        progressStatus.setValue(50);
        lblStatus.setText("Checking if you're authorized to use this app...");
        progressStatus.setValue(75);
    }
    
    public void done(){
        progressStatus.setValue(100);
        lblStatus.setText("Opening Main Window...");
        this.dispose();
    }
    
    public void loadingResources(){
        progressStatus.setValue(5);
        lblStatus.setText("Checking / Download Resources");
        
    }
}
