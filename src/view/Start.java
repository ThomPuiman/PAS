package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This panel is shown after a user logged in, from here the user can navigate to the different sections of the application
 * @author Thom Puiman
 */
public class Start extends javax.swing.JPanel {

    /** Creates new form BeginScherm */
    public Start() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMember = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnInvoicing = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnManager = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnBarApplication = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        btnMember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/member.png"))); // NOI18N
        btnMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberActionPerformed(evt);
            }
        });

        jLabel1.setText("Ledenadministratie");

        btnInvoicing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/invoices.png"))); // NOI18N
        btnInvoicing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoicingActionPerformed(evt);
            }
        });

        jLabel2.setText("Facturatie");

        btnManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/config.png"))); // NOI18N
        btnManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerActionPerformed(evt);
            }
        });

        jLabel3.setText("Beheer");

        btnBarApplication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/bar.png"))); // NOI18N
        btnBarApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarApplicationActionPerformed(evt);
            }
        });

        jLabel4.setText("Barapplicatie");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jLabel1)
                        .addGap(140, 140, 140)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel4)
                                .addGap(157, 157, 157)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnBarApplication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(btnInvoicing, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnInvoicing, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMember, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBarApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
     * After a click, the panel MemberSearch is shown
     */
    private void btnMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberActionPerformed
        MainFrame.changePanel(MainFrame.SEARCHMEMBERS);
    }//GEN-LAST:event_btnMemberActionPerformed

    /*
     * After a click, the panel InvoiceSearch is shown
     */
    private void btnInvoicingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoicingActionPerformed
        MainFrame.changePanel(MainFrame.INVOICES);
    }//GEN-LAST:event_btnInvoicingActionPerformed

    /*
     * After a click, the panel CategoryList is shown
     */
    private void btnBarApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarApplicationActionPerformed
        MainFrame.changePanel(MainFrame.CATEGORYLIST);
    }//GEN-LAST:event_btnBarApplicationActionPerformed

    /*
     * After a click, the panel AccountSearch is shown
     */
    private void btnManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerActionPerformed
        // TODO add your handling code here:
        if(main.PASApplication.getQueryManager().getRol() >= MainFrame.ROL_BEHEER)
            MainFrame.changePanel(MainFrame.ACCOUNT);
        else
            JOptionPane.showMessageDialog(null, "U heeft geen rechten voor dit onderdeel");
        
    }//GEN-LAST:event_btnManagerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBarApplication;
    private javax.swing.JButton btnInvoicing;
    private javax.swing.JButton btnManager;
    private javax.swing.JButton btnMember;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
