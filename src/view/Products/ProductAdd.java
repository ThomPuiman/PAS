/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Products;

import java.awt.CardLayout;
import java.awt.Component;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.Product;
import view.Members.CourseRegistration;
import view.MainFrame;

/**
 *
 * @author Thom
 */
public class ProductAdd extends javax.swing.JPanel {

    /**
     * Creates new form Leden
     */
    public ProductAdd() {
        initComponents();
        this.cmbStatus.setVisible(false);
        this.lblStatus.setVisible(false);
        this.txtProductID.setVisible(false);
        getProductCategories();
    }

    private void getProductCategories() {
        List<Category> categories = main.PASApplication.getQueryManager().getProductCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            cmbCategorie.addItem(category.getName());
        }
    }

    public void clear() {
        this.txtNaam.setText("");
        this.txtOmschrijving.setText("");
        this.txtPrijs.setText("");
        this.txtMaxDeelnemers.setText("");
        this.txtStartdatum.setText("");
        this.txtEinddatum.setText("");
        this.jLabel1.setText("Product toevoegen");
        this.txtProductID.setText("0");
        this.btnOpslaan.setText("Opslaan");
        this.cmbStatus.setVisible(false);
        this.txtProductID.setVisible(false);
        this.lblStatus.setVisible(false);
        
        // maak alle velden weer actief zodat er een nieuwe product aangemaakt kan worden
        this.cmbCategorie.setEnabled(true);
        this.txtNaam.setEnabled(true);
        this.txtOmschrijving.setEnabled(true);
        this.txtPrijs.setEnabled(true);
        this.txtMaxDeelnemers.setEnabled(true);
        this.txtStartdatum.setEnabled(true);
        this.txtEinddatum.setEnabled(true);
    }

    private void save() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Product prdct = null;

            prdct = new Product( Integer.parseInt(this.txtProductID.getText()), 
                    cmbCategorie.getSelectedIndex() + 1,
                    this.txtNaam.getText(), 
                    this.txtOmschrijving.getText(), 
                    this.txtPrijs.getText().length() > 0 ? Double.parseDouble(this.txtPrijs.getText()) : -1.00, 
                    this.txtMaxDeelnemers.getText().length() > 0 ? Integer.parseInt(this.txtMaxDeelnemers.getText()) : 0, 
                    this.txtStartdatum.getText().length() > 0 ?(java.util.Date)sdf.parse(this.txtStartdatum.getText()) : null, 
                    this.txtEinddatum.getText().length() > 0 ? (java.util.Date)sdf.parse(this.txtEinddatum.getText()) : null,  
                    this.cmbStatus.getSelectedItem().toString());

            if (prdct.validate() && main.PASApplication.getQueryManager().insertProduct(prdct, Integer.parseInt(this.txtProductID.getText()))) {
                MainFrame.addPanel(new ProductZoeken(), MainFrame.PRODUCTZOEKEN);
                return;
            }
        } catch (ParseException ex) {
             JOptionPane.showMessageDialog(null, "Ongeldige datum ingevoerd: \n\n" + ex.getMessage());
        }

    }

    public void edit(int productId) {

        model.Product editProduct = main.PASApplication.getQueryManager().getProduct(productId);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.jLabel1.setText("Product wijzigen");
        this.btnOpslaan.setText("Wijzigen");
        this.txtProductID.setText(Integer.toString(productId));
        this.cmbCategorie.setSelectedIndex(editProduct.getCategorieId() - 1);
        this.txtNaam.setText(editProduct.getName().trim());
        this.txtOmschrijving.setText(editProduct.getDescription());
        this.txtPrijs.setText(Double.toString(editProduct.getPrice()));
        this.txtMaxDeelnemers.setText(Integer.toString(editProduct.getMaxDeelnemers()));
        this.cmbStatus.setVisible(true);
        this.lblStatus.setVisible(true);

        int statusIndex = 0;
        if ("Actief".equals(editProduct.getStatus())) {
            statusIndex = 0;
            this.cmbCategorie.setEnabled(true);
            this.txtNaam.setEnabled(true);
            this.txtOmschrijving.setEnabled(true);
            this.txtPrijs.setEnabled(true);
            this.txtMaxDeelnemers.setEnabled(true);
            this.txtStartdatum.setEnabled(true);
            this.txtEinddatum.setEnabled(true);
        } else if ("Niet actief".equals(editProduct.getStatus())) {
            statusIndex = 1;
            this.cmbCategorie.setEnabled(false);
            this.txtNaam.setEnabled(false);
            this.txtOmschrijving.setEnabled(false);
            this.txtPrijs.setEnabled(false);
            this.txtMaxDeelnemers.setEnabled(false);
            this.txtStartdatum.setEnabled(false);
            this.txtEinddatum.setEnabled(false);
        }

        this.cmbStatus.setSelectedIndex(statusIndex);

        if (editProduct.getStartDatum() != null) {
            this.txtStartdatum.setText(dateFormat.format(editProduct.getStartDatum()).toString());
        }
        if (editProduct.getEindDatum() != null) {
            this.txtEinddatum.setText(dateFormat.format(editProduct.getEindDatum()).toString());
        }
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
        cmbCategorie = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        lblNaam = new javax.swing.JLabel();
        txtNaam = new javax.swing.JTextField();
        lblOmschrijving = new javax.swing.JLabel();
        txtOmschrijving = new javax.swing.JTextField();
        lblPrijs = new javax.swing.JLabel();
        lblDeelnemers = new javax.swing.JLabel();
        lblStartDatum = new javax.swing.JLabel();
        lblEindDatum = new javax.swing.JLabel();
        txtMaxDeelnemers = new javax.swing.JTextField();
        txtStartdatum = new javax.swing.JTextField();
        txtEinddatum = new javax.swing.JTextField();
        txtPrijs = new javax.swing.JTextField();
        btnOpslaan = new javax.swing.JButton();
        txtProductID = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Product toevoegen");

        cmbCategorie.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategorieItemStateChanged(evt);
            }
        });

        jLabel2.setText("Categorie:");

        lblNaam.setText("Naam:");

        lblOmschrijving.setText("Omschrijving:");

        lblPrijs.setText("Prijs:");

        lblDeelnemers.setText("Max. deelnemers:");

        lblStartDatum.setText("Start datum:");

        lblEindDatum.setText("Eind datum:");

        btnOpslaan.setLabel("Opslaan");
        btnOpslaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpslaanActionPerformed(evt);
            }
        });

        txtProductID.setText("0");
        txtProductID.setEnabled(false);

        lblStatus.setText("Status:");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Actief", "Niet actief" }));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnOpslaan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNaam)
                                    .addComponent(lblOmschrijving)
                                    .addComponent(lblPrijs))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPrijs)
                                    .addComponent(txtOmschrijving)
                                    .addComponent(txtNaam)
                                    .addComponent(cmbCategorie, 0, 195, Short.MAX_VALUE)
                                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblStartDatum)
                                    .addComponent(lblEindDatum)
                                    .addComponent(lblDeelnemers))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEinddatum)
                                    .addComponent(txtStartdatum)
                                    .addComponent(txtMaxDeelnemers, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)))
                            .addComponent(txtProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNaam)
                    .addComponent(txtNaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaxDeelnemers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDeelnemers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOmschrijving)
                    .addComponent(txtOmschrijving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStartDatum)
                    .addComponent(txtStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrijs)
                    .addComponent(lblEindDatum)
                    .addComponent(txtEinddatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrijs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpslaan)
                .addContainerGap(217, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCategorieItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategorieItemStateChanged
        // TODO add your handling code here:      
        if (!"Cursussen".equals(cmbCategorie.getSelectedItem().toString().trim())) {
            this.txtMaxDeelnemers.setVisible(false);
            this.txtStartdatum.setVisible(false);
            this.txtEinddatum.setVisible(false);
            this.lblDeelnemers.setVisible(false);
            this.lblStartDatum.setVisible(false);
            this.lblEindDatum.setVisible(false);
        } else {
            this.txtMaxDeelnemers.setVisible(true);
            this.txtStartdatum.setVisible(true);
            this.txtEinddatum.setVisible(true);
            this.lblDeelnemers.setVisible(true);
            this.lblStartDatum.setVisible(true);
            this.lblEindDatum.setVisible(true);
        }
    }//GEN-LAST:event_cmbCategorieItemStateChanged

    private void btnOpslaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpslaanActionPerformed
        // TODO add your handling code here:
        save();

    }//GEN-LAST:event_btnOpslaanActionPerformed

    private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
        // TODO add your handling code here:
        
        if ("Actief".equals(this.cmbStatus.getSelectedItem())) {
            this.cmbCategorie.setEnabled(true);
            this.txtNaam.setEnabled(true);
            this.txtOmschrijving.setEnabled(true);
            this.txtPrijs.setEnabled(true);
            this.txtMaxDeelnemers.setEnabled(true);
            this.txtStartdatum.setEnabled(true);
            this.txtEinddatum.setEnabled(true);
        }
        else if ("Niet actief".equals(this.cmbStatus.getSelectedItem())) {
            this.cmbCategorie.setEnabled(false);
            this.txtNaam.setEnabled(false);
            this.txtOmschrijving.setEnabled(false);
            this.txtPrijs.setEnabled(false);
            this.txtMaxDeelnemers.setEnabled(false);
            this.txtStartdatum.setEnabled(false);
            this.txtEinddatum.setEnabled(false);
        }
        
    }//GEN-LAST:event_cmbStatusItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpslaan;
    private javax.swing.JComboBox cmbCategorie;
    private javax.swing.JComboBox cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDeelnemers;
    private javax.swing.JLabel lblEindDatum;
    private javax.swing.JLabel lblNaam;
    private javax.swing.JLabel lblOmschrijving;
    private javax.swing.JLabel lblPrijs;
    private javax.swing.JLabel lblStartDatum;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField txtEinddatum;
    private javax.swing.JTextField txtMaxDeelnemers;
    private javax.swing.JTextField txtNaam;
    private javax.swing.JTextField txtOmschrijving;
    private javax.swing.JTextField txtPrijs;
    private javax.swing.JTextField txtProductID;
    private javax.swing.JTextField txtStartdatum;
    // End of variables declaration//GEN-END:variables
}
