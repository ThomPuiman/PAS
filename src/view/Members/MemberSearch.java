/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LidZoeken.java
 *
 * Created on 4-mei-2012, 22:00:25
 */
package view.Members;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import view.MainFrame;

/**
 *
 * @author Thom
 */
public class MemberSearch extends javax.swing.JPanel {

    private JTable table;

    /** Creates new form LidZoeken */
    public MemberSearch() {
        this.setLayout(new GridLayout(0, 1));
        initComponents();
        loadTable();

        this.lblNotFound.setVisible(false);
        this.txtSearchField.repaint();
    }

    private void loadTable() {
        String[] columnNames = {"LidNr",
            "Geslacht",
            "Naam",
            "Woonplaats",
            "Inschrijfdatum",
            "Status",
            "Ingecheckt"};

        try {
            DefaultTableModel model = new DefaultTableModel(main.PASApplication.getQueryManager().getMemberList(), columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(model);

            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(0).setMinWidth(50);
            
            //verwijder de kolom ingecheckt, gebruiken we alleen om op te filteren.
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(6));

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            table.setRowSorter(sorter);


            txtSearchField.addKeyListener(new KeyListener() {

                public void keyReleased(KeyEvent e) {
                    String zoektext = txtSearchField.getText();
                    try {

                        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                        if (chkIngecheckt.isSelected()) {
                            filters.add(RowFilter.regexFilter("Ja", 6));
                        }
                        if (rbAchternaam.isSelected()) {
                            filters.add(RowFilter.regexFilter("(?i)" + zoektext, 2));
                        } else if (rbWoonplaats.isSelected()) {
                            filters.add(RowFilter.regexFilter("(?i)" + zoektext, 3));
                        } else if (rbLidnummer.isSelected()) {
                            filters.add(RowFilter.regexFilter("(?i)" + zoektext, 0));
                        }

                        RowFilter<Object, Object> txtcheckFilter = RowFilter.andFilter(filters);
                        sorter.setRowFilter(txtcheckFilter);

                        if (table.getRowCount() == 0) {
                            lblNotFound.setVisible(true);
                            repaint();
                        } else {
                            lblNotFound.setVisible(false);
                            repaint();
                        }
                    } catch (PatternSyntaxException pse) {
                        System.err.println("Bad regex pattern");
                    }
                }

                public void keyTyped(KeyEvent e) {
                    // TODO: Do something for the keyTyped event
                }

                public void keyPressed(KeyEvent e) {
                    // TODO: Do something for the keyPressed event
                }
            });

            chkIngecheckt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                if (chkIngecheckt.isSelected()) {
                        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                        filters.add(RowFilter.regexFilter("Ja", 6));
                        RowFilter<Object, Object> txtcheckFilter = RowFilter.andFilter(filters);
                        sorter.setRowFilter(txtcheckFilter);
                    }
                    else {
                         sorter.setRowFilter(null);
                    }
                }
            });

            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        MainFrame.setModifyMember(Integer.parseInt(table.getValueAt(table.rowAtPoint(e.getPoint()), 0).toString()));
                        MainFrame.changePanel(MainFrame.MEMBERS);
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setSize(new Dimension(750, 600));

            this.pnlData.setLayout(new GridLayout());
            this.pnlData.add(scrollPane, BorderLayout.EAST);
            this.pnlData.setSize(750, 600);
        } catch (SQLException ex) {
            Logger.getLogger(MemberSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean loadModify() {

        return true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtSearchField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rbAchternaam = new javax.swing.JRadioButton();
        rbWoonplaats = new javax.swing.JRadioButton();
        rbLidnummer = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        lblNotFound = new javax.swing.JLabel();
        pnlData = new javax.swing.JPanel();
        chkIngecheckt = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Zoekterm:");

        rbAchternaam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbAchternaam);
        rbAchternaam.setSelected(true);
        rbAchternaam.setText("Op naam");
        rbAchternaam.setActionCommand("Op achternaam");

        rbWoonplaats.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbWoonplaats);
        rbWoonplaats.setText("Op woonplaats");

        rbLidnummer.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbLidnummer);
        rbLidnummer.setText("Op lidnummer");

        jLabel2.setText("Zoeken naar:");

        lblNotFound.setForeground(new java.awt.Color(255, 0, 0));
        lblNotFound.setText("Selectie niet gevonden!");

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        chkIngecheckt.setBackground(new java.awt.Color(255, 255, 255));
        chkIngecheckt.setText("Alleen ingecheckte leden");
        chkIngecheckt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIngechecktActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkIngecheckt)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbAchternaam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbWoonplaats)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbLidnummer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                        .addComponent(lblNotFound)
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(rbAchternaam)
                    .addComponent(rbWoonplaats)
                    .addComponent(rbLidnummer)
                    .addComponent(lblNotFound))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkIngecheckt)
                .addGap(1, 1, 1)
                .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkIngechecktActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIngechecktActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkIngechecktActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkIngecheckt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblNotFound;
    private javax.swing.JPanel pnlData;
    private javax.swing.JRadioButton rbAchternaam;
    private javax.swing.JRadioButton rbLidnummer;
    private javax.swing.JRadioButton rbWoonplaats;
    private javax.swing.JTextField txtSearchField;
    // End of variables declaration//GEN-END:variables
}
