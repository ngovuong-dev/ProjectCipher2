package edu.stu.cipher2.view.panelModel;

import edu.stu.cipher2.view.MainFrame;
import javax.swing.*;


public class CipherPanel extends javax.swing.JPanel {
    private MainFrame mainFrame;
    private boolean typeSwitchED = true;
    
    
    public CipherPanel(MainFrame mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        syncTitleSwitchED();
    }

    public void syncTitleSwitchED() {
        if (typeSwitchED)
            lblTitleED.setText("Encrypt");
        else
            lblTitleED.setText("Decrypt");
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionSwitchED = new javax.swing.JOptionPane();
        bgbA5CR4 = new javax.swing.ButtonGroup();
        pnpTitle0 = new javax.swing.JPanel();
        pnpTitle1 = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        lblTitleED = new javax.swing.JLabel();
        btnSwitchED = new javax.swing.JButton();
        pnpSplit = new javax.swing.JPanel();
        pnpMain = new javax.swing.JPanel();
        btnRunning = new javax.swing.JButton();
        lblTypeCipher = new javax.swing.JLabel();
        cbcA5 = new javax.swing.JCheckBox();
        cbcRC4 = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(204, 204, 204));

        pnpTitle0.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pnpTitle0Layout = new javax.swing.GroupLayout(pnpTitle0);
        pnpTitle0.setLayout(pnpTitle0Layout);
        pnpTitle0Layout.setHorizontalGroup(
            pnpTitle0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        pnpTitle0Layout.setVerticalGroup(
            pnpTitle0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnpTitle1.setBackground(new java.awt.Color(51, 51, 51));

        btnExit.setBackground(new java.awt.Color(255, 0, 51));
        btnExit.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.setFocusPainted(false);
        btnExit.setFocusable(false);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblTitleED.setFont(new java.awt.Font("Droid Sans", 1, 40)); // NOI18N
        lblTitleED.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleED.setText("Title");

        btnSwitchED.setBackground(new java.awt.Color(153, 0, 255));
        btnSwitchED.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        btnSwitchED.setForeground(new java.awt.Color(255, 255, 255));
        btnSwitchED.setText("Switch");
        btnSwitchED.setFocusPainted(false);
        btnSwitchED.setFocusable(false);
        btnSwitchED.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchEDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnpTitle1Layout = new javax.swing.GroupLayout(pnpTitle1);
        pnpTitle1.setLayout(pnpTitle1Layout);
        pnpTitle1Layout.setHorizontalGroup(
            pnpTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpTitle1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitleED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnpTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSwitchED, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnpTitle1Layout.setVerticalGroup(
            pnpTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpTitle1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnSwitchED, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnpTitle1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblTitleED)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnpSplit.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pnpSplitLayout = new javax.swing.GroupLayout(pnpSplit);
        pnpSplit.setLayout(pnpSplitLayout);
        pnpSplitLayout.setHorizontalGroup(
            pnpSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnpSplitLayout.setVerticalGroup(
            pnpSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        pnpMain.setBackground(new java.awt.Color(255, 255, 255));
        pnpMain.setPreferredSize(new java.awt.Dimension(850, 370));

        btnRunning.setBackground(new java.awt.Color(0, 0, 255));
        btnRunning.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        btnRunning.setForeground(new java.awt.Color(255, 255, 255));
        btnRunning.setText("Run");
        btnRunning.setFocusPainted(false);
        btnRunning.setFocusable(false);
        btnRunning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunningActionPerformed(evt);
            }
        });

        lblTypeCipher.setFont(new java.awt.Font("Droid Sans", 1, 24)); // NOI18N
        lblTypeCipher.setForeground(new java.awt.Color(51, 51, 51));
        lblTypeCipher.setText("Type Cipher:");

        cbcA5.setBackground(new java.awt.Color(255, 255, 255));
        bgbA5CR4.add(cbcA5);
        cbcA5.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        cbcA5.setForeground(new java.awt.Color(0, 0, 0));
        cbcA5.setSelected(true);
        cbcA5.setText(" A5");
        cbcA5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbcA5.setFocusPainted(false);

        cbcRC4.setBackground(new java.awt.Color(255, 255, 255));
        bgbA5CR4.add(cbcRC4);
        cbcRC4.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        cbcRC4.setForeground(new java.awt.Color(0, 0, 0));
        cbcRC4.setText("RC4");
        cbcRC4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbcRC4.setFocusPainted(false);

        javax.swing.GroupLayout pnpMainLayout = new javax.swing.GroupLayout(pnpMain);
        pnpMain.setLayout(pnpMainLayout);
        pnpMainLayout.setHorizontalGroup(
            pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRunning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnpMainLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTypeCipher)
                .addGap(43, 43, 43)
                .addComponent(cbcA5)
                .addGap(110, 110, 110)
                .addComponent(cbcRC4)
                .addContainerGap(425, Short.MAX_VALUE))
        );
        pnpMainLayout.setVerticalGroup(
            pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnpMainLayout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addGroup(pnpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTypeCipher)
                    .addComponent(cbcA5)
                    .addComponent(cbcRC4))
                .addGap(18, 18, 18)
                .addComponent(btnRunning, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnpTitle0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnpTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnpSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(pnpMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnpTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnpTitle0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnpSplit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnpMain, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnRunningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunningActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRunningActionPerformed

    private void btnSwitchEDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchEDActionPerformed
        // TODO add your handling code here:
        int result = optionSwitchED.showConfirmDialog(this, 
        "Do you want switch?",
        "Yes", 
        javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            typeSwitchED = !typeSwitchED;
        }
        
        syncTitleSwitchED();
    }//GEN-LAST:event_btnSwitchEDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgbA5CR4;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRunning;
    private javax.swing.JButton btnSwitchED;
    private javax.swing.JCheckBox cbcA5;
    private javax.swing.JCheckBox cbcRC4;
    private javax.swing.JLabel lblTitleED;
    private javax.swing.JLabel lblTypeCipher;
    private javax.swing.JOptionPane optionSwitchED;
    private javax.swing.JPanel pnpMain;
    private javax.swing.JPanel pnpSplit;
    private javax.swing.JPanel pnpTitle0;
    private javax.swing.JPanel pnpTitle1;
    // End of variables declaration//GEN-END:variables
}
