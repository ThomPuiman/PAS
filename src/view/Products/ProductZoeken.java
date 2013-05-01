/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LidZoeken.java
 *
 * Created on 4-mei-2012, 22:00:25
 */
package view.Products;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import view.MainFrame;

/**
 *
 * @author Thom
 */
public class ProductZoeken extends javax.swing.JPanel {

    private JTable table;

    /** Creates new form LidZoeken */
    public ProductZoeken() {
        initComponents();
        this.lblNotFound.setVisible(false);
           loadTable();

    }

    private void loadTable() {
        String[] columnNames = {"ID",
            "Naam",
            "Omschrijving",
            "Prijs",
            "Max. deelnemers",
            "Startdatum.",
            "Einddatum",
            "Categorie",
            "Status"};

        try {
            DefaultTableModel model = new DefaultTableModel(main.PASApplication.getQueryManager().getProductList(), columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(model);
                       

            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(0).setMinWidth(50);

            //verwijder de kolom categorie, gebruiken we alleen om op te filteren.
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(7));

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            table.setRowSorter(sorter);

            //Filter standaard op geselecteerde categorie
            //sorter.setRowFilter(RowFilter.regexFilter(cmbCategorie.getSelectedItem().toString(), 7));

            txtSearchField.addKeyListener(new KeyListener() {

                public void keyReleased(KeyEvent e) {
                    String zoektext = txtSearchField.getText();
                    String zoekcategorie = cmbCategorie.getSelectedItem().toString();

                    try {
                        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                        filters.add(RowFilter.regexFilter("(?i)" + zoektext, 1));
                        filters.add(RowFilter.regexFilter("(?i)" + zoekcategorie, 7));
                        RowFilter<Object, Object> txtcatFilter = RowFilter.andFilter(filters);
                        sorter.setRowFilter(txtcatFilter);
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

            cmbCategorie.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    String zoektext = txtSearchField.getText();
                    String zoekcategorie = cmbCategorie.getSelectedItem().toString();

                    try {
                        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                        filters.add(RowFilter.regexFilter("(?i)" + zoektext, 1));
                        filters.add(RowFilter.regexFilter("(?i)" + zoekcategorie, 7));
                        RowFilter<Object, Object> txtcatFilter = RowFilter.andFilter(filters);
                        sorter.setRowFilter(txtcatFilter);
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
            });
            
            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        MainFrame.setModifyProduct(Integer.parseInt(table.getValueAt(table.rowAtPoint(e.getPoint()), 0).toString()));
                        MainFrame.changePanel(MainFrame.PRODUCTTOEVOEGEN);
                    }
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setSize(new Dimension(750,600));
           
            this.pnlData.setLayout(new GridLayout());
            this.pnlData.add(scrollPane, BorderLayout.EAST);
            this.pnlData.setSize(750, 600);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductZoeken.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        lblNotFound = new javax.swing.JLabel();
        cmbCategorie = new javax.swing.JComboBox();
        pnlData = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        txtSearchField.setMaximumSize(new java.awt.Dimension(6, 20));

        jLabel1.setText("Zoek op productnaam:");

        jLabel2.setText("In categorie:");

        lblNotFound.setForeground(new java.awt.Color(255, 0, 0));
        lblNotFound.setText("Selectie niet gevonden!");

        cmbCategorie.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Broodjes", "Drankjes", "Snacks", "Cursussen", "Abonnementen", "Groepsactiviteiten", "Faciliteiten" }));
        cmbCategorie.setMaximumSize(new java.awt.Dimension(112, 20));

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblNotFound)
                .addContainerGap(275, Short.MAX_VALUE))
            .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbCategorie, lblNotFound, txtSearchField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cmbCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNotFound))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbCategorie, jLabel1, jLabel2, lblNotFound, txtSearchField});

    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbCategorie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblNotFound;
    private javax.swing.JPanel pnlData;
    private javax.swing.JTextField txtSearchField;
    // End of variables declaration//GEN-END:variables
}
