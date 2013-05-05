/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows.popup;

import com.kronosad.projects.twitter.kronostwit.enums.FinishedWork;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.DocumentLimitedInput;
import com.kronosad.projects.twitter.kronostwit.gui.helpers.HelperNewTweet;
import com.kronosad.projects.twitter.kronostwit.gui.windows.Window;
import com.kronosad.projects.twitter.kronostwit.interfaces.IStatus;
import javax.swing.JOptionPane;


/**
 *
 * @author Russell
 */
public class WindowNewTweet extends Window {
    private DocumentLimitedInput dli;
    /**
     * Creates new form WindowNewTweet
     */
    private IStatus statuses;
    private boolean isReply = false;
    private long replyID;
    public WindowNewTweet(String title, int sizeX, int sizeY, IStatus status) {
        super(title, sizeX, sizeY);
        statuses = status;
        initComponents();
        init();
        
    }
    
    public WindowNewTweet(String title, int sizeX, int sizeY, IStatus status, long reply){
        super(title, sizeX, sizeY);
        statuses = status;
        replyID = reply;
        isReply = true;
        initComponents();
        init();
    }
    
    public WindowNewTweet(String title, int sizeX, int sizeY, IStatus status, String tweet) {
        super(title, sizeX, sizeY);
        statuses = status;
        initComponents();
        init();
        
        txtAreaTweet.setText(tweet);
        
    }
    
    public WindowNewTweet(String title, int sizeX, int sizeY, IStatus status, long reply, String tweet){
        super(title, sizeX, sizeY);
        statuses = status;
        replyID = reply;
        isReply = true;
        initComponents();
        init();
        
        txtAreaTweet.setText(tweet);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCharsLeft = new javax.swing.JLabel();
        lblCharsLeftTxt = new javax.swing.JLabel();
        btnTweet = new javax.swing.JButton();
        progressBarCharsLeft = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaTweet = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblCharsLeft.setText("Characters Left:");

        lblCharsLeftTxt.setText("140");

        btnTweet.setText("Tweet!");
        btnTweet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTweetActionPerformed(evt);
            }
        });

        txtAreaTweet.setColumns(20);
        txtAreaTweet.setRows(5);
        txtAreaTweet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAreaTweetKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaTweet);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addComponent(btnTweet)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCharsLeft)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCharsLeftTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBarCharsLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(progressBarCharsLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCharsLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCharsLeftTxt)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTweet)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAreaTweetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaTweetKeyReleased
        progressBarCharsLeft.setValue(txtAreaTweet.getText().length());
        int left = 140 - txtAreaTweet.getText().length();
        
        lblCharsLeftTxt.setText(String.valueOf(left));
        
        if(left <= 0){
        }
    }//GEN-LAST:event_txtAreaTweetKeyReleased

    private void btnTweetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTweetActionPerformed
        if(!isReply || txtAreaTweet.getText() == null){
            
            HelperNewTweet newTweet = new HelperNewTweet(statuses, txtAreaTweet.getText());
            FinishedWork type = newTweet.tweet();
            
            if(type == type.NOTWEET){
                System.out.println("No Context!");
                
            }else if(type == type.OUTOFBOUNDS){
                JOptionPane.showMessageDialog(this, "Your tweet is too long!", "Error Posting Tweet!", JOptionPane.WARNING_MESSAGE);
                
            }else if(type == type.TWITTERERROR){
                JOptionPane.showMessageDialog(this, "There was a Twitter Error! Check Console!", "Twitter Error!", JOptionPane.ERROR);
            }else if(type == type.UNKNOWN){
                JOptionPane.showMessageDialog(this, "There was an Unknown Error! Check Console!", "Unknown Error!", JOptionPane.ERROR);
    
            }else if(type == type.SUCCESS){
                this.dispose();
            }else if(type == type.DUPLICATE){
                JOptionPane.showMessageDialog(this, "You cannot post a duplicate status!", "Duplicate Detected!", JOptionPane.WARNING_MESSAGE);
            }
        }else if(isReply){
            HelperNewTweet newTweet = new HelperNewTweet(statuses, txtAreaTweet.getText(), replyID);
            FinishedWork type = newTweet.tweetWithReply();
            
            if(type == type.NOTWEET){
                System.out.println("No Context!");
            }else if(type == type.OUTOFBOUNDS){
                JOptionPane.showMessageDialog(this, "Your tweet is too long!", "Error Posting Tweet!", JOptionPane.WARNING_MESSAGE);
                
            }else if(type == type.TWITTERERROR){
                JOptionPane.showMessageDialog(this, "There was a Twitter Error! Check Console!", "Twitter Error!", JOptionPane.ERROR);
            }else if(type == type.UNKNOWN){
                JOptionPane.showMessageDialog(this, "There was an Unknown Error! Check Console!", "Unknown Error!", JOptionPane.ERROR);
    
            }else if(type == type.SUCCESS){
                this.dispose();
            }
        }else{
            System.out.println("No Context!");
        }
    }//GEN-LAST:event_btnTweetActionPerformed
    
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTweet;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCharsLeft;
    private javax.swing.JLabel lblCharsLeftTxt;
    private javax.swing.JProgressBar progressBarCharsLeft;
    private javax.swing.JTextArea txtAreaTweet;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
        dli = new DocumentLimitedInput(140);
        txtAreaTweet.setDocument(dli);
        txtAreaTweet.setLineWrap(true);
        
        progressBarCharsLeft.setMaximum(140);
        this.setVisible(true);
    }

    @Override
    public void close() {

        
    
    }
}
