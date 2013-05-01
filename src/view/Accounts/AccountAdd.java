package view.Accounts;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.*;
import main.*;
import model.Account;

/**
 * In this panel a user can add an account. This is used by the manager role.
 * @author Vincent S
 */
public class AccountAdd extends javax.swing.JPanel {

    /*
     * Creates new form AccountAanmaken 
     */
    public AccountAdd() {
        initComponents();
    }

    /*
     * Clears the form
     */
    public void clear() {
        this.txtVoornaam.setText("");
        this.txtTussenvoegsel.setText("");
        this.txtAchternaam.setText("");
        this.txtWachtwoord.setText("");
        this.cmbGeslacht.setSelectedIndex(0);
        this.cmbRol.setSelectedIndex(0);
        this.cmbVestiging.setSelectedIndex(0);
        this.txtAccountID.setText("0");
        this.btn_opslaan.setText("Opslaan");
        this.txtAccountID.setVisible(false);
        this.cmb_status.setVisible(false);
        this.lbl_status.setVisible(false);
        
        // maak alle velden weer actief zodat er een nieuw account aangemaakt kan worden
        this.txtVoornaam.setEnabled(true);
        this.txtTussenvoegsel.setEnabled(true);
        this.txtAchternaam.setEnabled(true);
        this.txtWachtwoord.setEnabled(true);
        this.cmbGeslacht.setEnabled(true);
        this.cmbRol.setEnabled(true);
        this.cmbVestiging.setEnabled(true);
    }

    /*
     * Saves the form
     */
    private void save() {
       Account acc = null;
       
       String vestigingscode = "";

        switch (cmbVestiging.getSelectedIndex()) {
            case 0:
                vestigingscode = "alk";
                break;
            case 1:
                vestigingscode = "haa";
                break;
            case 2:
                vestigingscode = "pur";
                break;
        }
       
       acc = new Account(Integer.parseInt(this.txtAccountID.getText()),
               this.txtVoornaam.getText(),
               this.txtTussenvoegsel.getText(),
               this.txtAchternaam.getText(),
               this.txtWachtwoord.getText(),
               this.cmbGeslacht.getSelectedItem().toString().substring(0, 1),
               vestigingscode,
               this.cmbRol.getSelectedIndex()+1,
               this.cmb_status.getSelectedItem().toString());
       
        if (acc.validate() && main.PASApplication.getQueryManager().insertAccount(acc, Integer.parseInt(this.txtAccountID.getText()))) {
            MainFrame.addPanel(new AccountSearch(), MainFrame.ACCOUNTZOEKEN);
            return;
        }
    }

    /*
     * Load the data of the selected account
     * @param medewerkers_nr This represents the accountnummber
     */
    public void edit(int accountNr) {

        model.Account maccnt = PASApplication.getQueryManager().getAccount(accountNr);

        int geslachtIndex = 0;
        if ("M".equals(maccnt.getMedew_geslacht())) 
            geslachtIndex = 0;
        else 
            geslachtIndex = 1;
        
        this.cmbGeslacht.setSelectedIndex(geslachtIndex);
        this.txtVoornaam.setText(maccnt.getmedew_voornaam());
        this.txtTussenvoegsel.setText(maccnt.getmedew_tussenvoegsel());
        this.txtAchternaam.setText(maccnt.getmedew_achternaam());
        this.txtWachtwoord.setText(maccnt.getMedew_wachtwoord());
        this.txtAccountID.setText(Integer.toString(accountNr));


        int vestigingIndex = 0;
        if ("alk".equals(maccnt.getVestigingscode())) 
            vestigingIndex = 0;
        else if ("haa".equals(maccnt.getVestigingscode())) 
            vestigingIndex = 1;
        else 
            vestigingIndex = 2;
        
        this.cmbVestiging.setSelectedIndex(vestigingIndex);

        int rolIndex = 0;
        if (maccnt.getRolcode() == 1) 
            rolIndex = 0;
        else if (maccnt.getRolcode() == 2)
            rolIndex = 1;
        else if (maccnt.getRolcode() == 3)
            rolIndex = 2;
               
        this.cmbRol.setSelectedIndex(rolIndex);

        int statusIndex = 0;
        if ("Actief".equals(maccnt.getMedew_status())) {
            statusIndex = 0;
            this.txtVoornaam.setEnabled(true);
            this.txtTussenvoegsel.setEnabled(true);
            this.txtAchternaam.setEnabled(true);
            this.txtWachtwoord.setEnabled(true);
            this.cmbGeslacht.setEnabled(true);
            this.cmbRol.setEnabled(true);
            this.cmbVestiging.setEnabled(true);
        } else if ("Niet actief".equals(maccnt.getMedew_status())) {
            statusIndex = 1;
            this.txtVoornaam.setEnabled(false);
            this.txtTussenvoegsel.setEnabled(false);
            this.txtAchternaam.setEnabled(false);
            this.txtWachtwoord.setEnabled(false);
            this.cmbGeslacht.setEnabled(false);
            this.cmbRol.setEnabled(false);
            this.cmbVestiging.setEnabled(false);
        }

        this.cmb_status.setSelectedIndex(statusIndex);

        
        this.lblAccountAanmaken.setText("Wijzigen Account");
        this.btn_opslaan.setText("Wijzigen");
        this.txtAccountID.setVisible(false);
        this.cmb_status.setVisible(true);
        this.lbl_status.setVisible(true);

        this.repaint();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAccountAanmaken = new javax.swing.JLabel();
        txtVoornaam = new javax.swing.JTextField();
        txtAchternaam = new javax.swing.JTextField();
        txtTussenvoegsel = new javax.swing.JTextField();
        lblVoornaam = new javax.swing.JLabel();
        lblAchternaam = new javax.swing.JLabel();
        lblTussenvoegsel = new javax.swing.JLabel();
        lblGeslacht = new javax.swing.JLabel();
        cmbGeslacht = new javax.swing.JComboBox();
        lblWachtwoord = new javax.swing.JLabel();
        txtWachtwoord = new javax.swing.JTextField();
        lblVestiging = new javax.swing.JLabel();
        cmbVestiging = new javax.swing.JComboBox();
        lblRol = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox();
        btn_opslaan = new javax.swing.JButton();
        txtAccountID = new javax.swing.JTextField();
        cmb_status = new javax.swing.JComboBox();
        lbl_status = new javax.swing.JLabel();

        lblAccountAanmaken.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAccountAanmaken.setText("Account aanmaken");

        lblVoornaam.setText("Voornaam:");

        lblAchternaam.setText("Achternaam:");

        lblTussenvoegsel.setText("Tussenvoegsel:");

        lblGeslacht.setText("Geslacht: ");

        cmbGeslacht.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Man", "Vrouw" }));

        lblWachtwoord.setText("Wachtwoord:");

        lblVestiging.setText("Vestiging:");

        cmbVestiging.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fitshape Alkmaar", "Fitshape Haarlem", "Fitshape Purmerend" }));

        lblRol.setText("Rol:");

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Manager", "Beheerder", "Medewerker" }));

        btn_opslaan.setText("Opslaan");
        btn_opslaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_opslaanMouseClicked(evt);
            }
        });

        txtAccountID.setText("0");
        txtAccountID.setEnabled(false);

        cmb_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Actief", "Niet actief" }));
        cmb_status.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_statusItemStateChanged(evt);
            }
        });

        lbl_status.setText("Status:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblAccountAanmaken)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblVoornaam, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblWachtwoord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRol, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVoornaam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtWachtwoord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTussenvoegsel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblGeslacht, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblVestiging, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbVestiging, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbGeslacht, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTussenvoegsel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAchternaam, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_status, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtAchternaam, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_opslaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_status, 0, 144, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbRol, txtVoornaam, txtWachtwoord});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbGeslacht, cmbVestiging, txtTussenvoegsel});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_opslaan, cmb_status, txtAchternaam});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAccountAanmaken)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVoornaam)
                    .addComponent(lblTussenvoegsel)
                    .addComponent(txtTussenvoegsel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAchternaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAchternaam, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVoornaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGeslacht)
                    .addComponent(txtWachtwoord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWachtwoord)
                    .addComponent(cmbGeslacht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRol)
                    .addComponent(lblVestiging, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbVestiging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_opslaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbRol, txtVoornaam, txtWachtwoord});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbGeslacht, cmbVestiging, txtTussenvoegsel});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmb_status, txtAchternaam});

    }// </editor-fold>//GEN-END:initComponents

    private void btn_opslaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_opslaanMouseClicked
        Component frame = null;
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_btn_opslaanMouseClicked

    private void cmb_statusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_statusItemStateChanged
        // TODO add your handling code here:
        if ("Actief".equals(this.cmb_status.getSelectedItem())) {
            this.txtVoornaam.setEnabled(true);
            this.txtTussenvoegsel.setEnabled(true);
            this.txtAchternaam.setEnabled(true);
            this.txtWachtwoord.setEnabled(true);
            this.cmbGeslacht.setEnabled(true);
            this.cmbRol.setEnabled(true);
            this.cmbVestiging.setEnabled(true);
        }
        else if ("Niet actief".equals(this.cmb_status.getSelectedItem())) {
            this.txtVoornaam.setEnabled(false);
            this.txtTussenvoegsel.setEnabled(false);
            this.txtAchternaam.setEnabled(false);
            this.txtWachtwoord.setEnabled(false);
            this.cmbGeslacht.setEnabled(false);
            this.cmbRol.setEnabled(false);
            this.cmbVestiging.setEnabled(false);
        }
    }//GEN-LAST:event_cmb_statusItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_opslaan;
    private javax.swing.JComboBox cmbGeslacht;
    private javax.swing.JComboBox cmbRol;
    private javax.swing.JComboBox cmbVestiging;
    private javax.swing.JComboBox cmb_status;
    private javax.swing.JLabel lblAccountAanmaken;
    private javax.swing.JLabel lblAchternaam;
    private javax.swing.JLabel lblGeslacht;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTussenvoegsel;
    private javax.swing.JLabel lblVestiging;
    private javax.swing.JLabel lblVoornaam;
    private javax.swing.JLabel lblWachtwoord;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JTextField txtAccountID;
    private javax.swing.JTextField txtAchternaam;
    private javax.swing.JTextField txtTussenvoegsel;
    private javax.swing.JTextField txtVoornaam;
    private javax.swing.JTextField txtWachtwoord;
    // End of variables declaration//GEN-END:variables
}
