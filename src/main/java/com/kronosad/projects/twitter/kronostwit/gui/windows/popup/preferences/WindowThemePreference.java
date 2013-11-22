/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.gui.windows.popup.preferences;

import com.kronosad.projects.twitter.kronostwit.console.ConsoleLoader;
import com.kronosad.projects.twitter.kronostwit.gui.windows.Window;
import com.kronosad.projects.twitter.kronostwit.theme.ITheme;
import com.kronosad.projects.twitter.kronostwit.theme.ThemeUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author russjr08
 */
public class WindowThemePreference extends Window {
    private DefaultListModel model;
    
    /**
     * Creates new form WindowThemePreference
     */
    public WindowThemePreference() {

        super("Theme Preferences", 500, 500);
        
        
        model = new DefaultListModel();

        
        initComponents();
        init();
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        themeList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnSetTheme = new javax.swing.JButton();
        btnReloadThemes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(themeList);

        jLabel1.setText("Choose a theme to use!");

        jButton1.setText("New Theme (NYI)");
        jButton1.setEnabled(false);

        btnSetTheme.setText("Set Theme");
        btnSetTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetThemeActionPerformed(evt);
            }
        });

        btnReloadThemes.setText("Reload Themes");
        btnReloadThemes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadThemesActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel1)
                .add(183, 183, 183))
            .add(layout.createSequentialGroup()
                .add(17, 17, 17)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 481, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jButton1)
                        .add(26, 26, 26)
                        .add(btnReloadThemes)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(btnSetTheme)
                        .add(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 224, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 9, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(btnSetTheme)
                    .add(btnReloadThemes)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSetThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetThemeActionPerformed
        
        String themeName = (String)model.get(themeList.getSelectedIndex());
        Preferences.setActiveTheme(themeName);
        
        try {
            if(!themeName.equalsIgnoreCase("Default")){
                ConsoleLoader.themeReg.add(ThemeUtils.fabricateThemeFromFile(themeName));
            }
        } catch (IOException ex) {
            Logger.getLogger(WindowThemePreference.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConsoleLoader.themeReg.getNewActive();
        

        
        for(Window window : ConsoleLoader.windows){
            window.recolor();
        }
        
        
    }//GEN-LAST:event_btnSetThemeActionPerformed

    private void btnReloadThemesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadThemesActionPerformed
        model.clear();
        ConsoleLoader.themeReg.clearThemes();

        try {

            ThemeUtils.initThemes();
        } catch (IOException ex) {
            Logger.getLogger(WindowThemePreference.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(ITheme theme : ConsoleLoader.themeReg.getThemes()){
            if(theme != null){
                model.addElement(theme.getName());

            }
        }
    }//GEN-LAST:event_btnReloadThemesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReloadThemes;
    private javax.swing.JButton btnSetTheme;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList themeList;
    // End of variables declaration//GEN-END:variables
    
    
    @Override
    public void init(){
        
        super.init();
        for(ITheme theme : ConsoleLoader.themeReg.getThemes()){
            if(theme != null){
                model.addElement(theme.getName());

            }
        }
        
        themeList.setModel(model);
        
        this.setVisible(true);
        
    }
    
    
}
