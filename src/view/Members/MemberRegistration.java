package view.Members;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.List;
import model.Category;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import view.Login;
import view.MainFrame;
import model.*;

/**
 * 
 * @author Thom
 */
public class MemberRegistration extends javax.swing.JPanel {

    /**
     * Creates new form Leden
     */
    public MemberRegistration() {
        initComponents();
        getAbonnementen();
        this.txtMemberId.setVisible(false);
        this.jButton1.setEnabled(false);
    }
    
    public class KeyValue {

        String value;
        int key;

        public KeyValue(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getKey() {
            return key;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
    private void getAbonnementen() {
        cmbAbonnementen.removeAllItems();
        List<Category> abonnementen = main.PASApplication.getQueryManager().getAbonnementen();
        cmbAbonnementen.addItem(new KeyValue(-1, "Kies een abonnement"));

        for (int i = 0; i < abonnementen.size(); i++) {
            Category abonnement = abonnementen.get(i);
            cmbAbonnementen.addItem(new KeyValue(abonnement.getCategoryId(), abonnement.getName()));
        }
    }

    public void clear() {

        this.txtVoorNaam.setText("");
        this.txtTussenVoegsel.setText("");
        this.txtAchterNaam.setText("");
        this.txtAdres.setText("");
        this.txtHuisnummer.setText("");
        this.txtToevoeging.setText("");
        this.txtPostcode.setText("");
        this.txtWoonplaats.setText("");
        this.txtTelDag.setText("");
        this.txtTelNacht.setText("");
        this.txtGebDatum.setText("");
        this.txtRekNr.setText("");
        this.txtEmailadress.setText("");
        this.txtMemberId.setText("0");
        this.cmbAbonnementen.setSelectedIndex(0);
        this.cmbGeslacht.setSelectedIndex(0);
        this.jLabel1.setText("Nieuwe inschrijving");
        this.jButton2Opslaanlid.setText("Opslaan");
        java.net.URL imageURL = MemberRegistration.class.getResource("/Resources/images/nophoto.jpg");
        this.lblFoto.setIcon(new javax.swing.ImageIcon(imageURL));
        this.btnEmail.setEnabled(false);
        this.jButton1.setEnabled(false);
    }

    public void edit(int memberId) {
        model.Member mmbr = main.PASApplication.getQueryManager().getMember(memberId);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.txtMemberId.setText(Integer.toString(memberId));
        this.txtVoorNaam.setText(mmbr.getFirstname());
        this.txtTussenVoegsel.setText(mmbr.getTussenVoeg());
        this.txtAchterNaam.setText(mmbr.getLastname());
        this.txtAdres.setText(mmbr.getStraat());
        this.txtHuisnummer.setText(Integer.toString(mmbr.getHuisnr()));
        this.txtToevoeging.setText(mmbr.getHuisnrtoevoeg());
        this.txtPostcode.setText(mmbr.getPostcode());
        this.txtWoonplaats.setText(mmbr.getWoonplaats());
        this.txtTelDag.setText(mmbr.getTelefoondag());
        this.txtTelNacht.setText(mmbr.getTelefoonnacht());
        this.txtGebDatum.setText(dateFormat.format(mmbr.getBirthday()));
        this.txtRekNr.setText(mmbr.getReknr());
        this.txtEmailadress.setText(mmbr.getEmailAddress());
        int genderIndex = 0;
        if ("M".equals(mmbr.getGender().toString())) {
            genderIndex = 0;
        } else {
            genderIndex = 1;
        }
        if (this.txtEmailadress.getText().length() < 1) {
            this.btnEmail.setEnabled(false);
        } else {
            this.btnEmail.setEnabled(true);
        }
        this.jButton1.setEnabled(true);
        if (mmbr.getFoto() != null && mmbr.getFoto().length() > 0) {

            try {
                if (new File(mmbr.getFoto()).exists()) {
                    java.awt.Image img = ImageIO.read(new File(mmbr.getFoto()));
                    java.awt.Image img2 = img.getScaledInstance(80, 120, java.awt.Image.SCALE_SMOOTH);
                    this.lblFoto.setIcon(new javax.swing.ImageIcon(img2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < cmbAbonnementen.getItemCount(); i++) {

            if (((KeyValue) cmbAbonnementen.getItemAt(i)).getKey() == mmbr.getAbonnement()) {
                System.out.println(mmbr.getAbonnement());

                cmbAbonnementen.setSelectedIndex(i);
            }
        }

        /*
        @TODO Display photo user      
        try
        {
        System.out.println(mmbr.getFoto().length);
        File temp = File.createTempFile("mmbrimg_", ".jpg");
        temp.deleteOnExit();
        java.io.DataOutputStream dos = new java.io.DataOutputStream(new FileOutputStream(temp));
        dos.write(mmbr.getFoto());
        dos.close();
        System.out.println(temp.getAbsolutePath());
        java.awt.Image img = ImageIO.read(temp);
        this.jLabel5.setIcon(new javax.swing.ImageIcon(img));
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
         */

        this.cmbGeslacht.setSelectedIndex(genderIndex);
        this.jLabel1.setText("Wijzigen inschrijving");
        this.jButton2Opslaanlid.setText("Wijzigen");
        this.repaint();
    }
    /*
     * @ author Oscar (met hulp)
     * @ param save methode om de velden op te slaan
     */

    private void save() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
        Member mmbr = null;
        try {
            mmbr = new Member(Integer.parseInt(this.txtMemberId.getText()),
                    this.txtVoorNaam.getText(),
                    this.txtAchterNaam.getText(),
                    this.cmbGeslacht.getSelectedItem().toString().substring(0, 1),
                    (java.util.Date) sdf.parse(this.txtGebDatum.getText()),
                    this.txtTussenVoegsel.getText(),
                    this.txtAdres.getText(),
                    this.txtHuisnummer.getText().length() > 0 ? Integer.parseInt(this.txtHuisnummer.getText()) : 0,
                    this.txtToevoeging.getText(),
                    this.txtPostcode.getText(),
                    this.txtWoonplaats.getText(),
                    this.txtTelDag.getText(),
                    this.txtTelNacht.getText(),
                    this.txtRekNr.getText(),
                    "Actief",
                    null,
                    null,
                    new java.util.Date(),
                    Login.employeecode,
                    vestigingscode,
                    this.txtEmailadress.getText(),
                    ((KeyValue) cmbAbonnementen.getSelectedItem()).getKey());
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(null, "Ongeldige datum ingevoerd: \n\n" + e.getMessage());
        }

        if (mmbr.validate() && main.PASApplication.getQueryManager().insertLid(mmbr, Integer.parseInt(this.txtMemberId.getText()))) {
            MainFrame.addPanel(new MemberSearch(), MainFrame.SEARCHMEMBERS);
            return;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblVoorNaam = new javax.swing.JLabel();
        txtVoorNaam = new javax.swing.JTextField();
        lblTussenVoegsel = new javax.swing.JLabel();
        txtTussenVoegsel = new javax.swing.JTextField();
        lblAchterNaam = new javax.swing.JLabel();
        txtAchterNaam = new javax.swing.JTextField();
        lblAdres = new javax.swing.JLabel();
        txtAdres = new javax.swing.JTextField();
        lblHuisNummer = new javax.swing.JLabel();
        txtHuisnummer = new javax.swing.JTextField();
        lblToevoeging = new javax.swing.JLabel();
        txtToevoeging = new javax.swing.JTextField();
        lblPostcode = new javax.swing.JLabel();
        txtPostcode = new javax.swing.JTextField();
        lblWoonplaats = new javax.swing.JLabel();
        txtWoonplaats = new javax.swing.JTextField();
        cmbGeslacht = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtTelNacht = new javax.swing.JTextField();
        lblWoonplaats1 = new javax.swing.JLabel();
        txtTelDag = new javax.swing.JTextField();
        lblPostcode1 = new javax.swing.JLabel();
        lblPostcode2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRekNr = new javax.swing.JTextField();
        jButton2Opslaanlid = new javax.swing.JButton();
        txtEmailadress = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbVestiging = new javax.swing.JComboBox();
        txtGebDatum = new javax.swing.JTextField();
        txtMemberId = new javax.swing.JTextField();
        cmbAbonnementen = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        lblFoto = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnEmail = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Nieuwe inschrijving");

        lblVoorNaam.setLabelFor(txtVoorNaam);
        lblVoorNaam.setText("Voornaam *");

        lblTussenVoegsel.setLabelFor(txtTussenVoegsel);
        lblTussenVoegsel.setText("Tussenvoegsel");

        lblAchterNaam.setLabelFor(txtAchterNaam);
        lblAchterNaam.setText("Achternaam *");

        lblAdres.setText("Adres *");

        lblHuisNummer.setText("Huisnummer *");

        lblToevoeging.setText("Toev.");

        lblPostcode.setText("Postcode *");

        txtPostcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPostcodeActionPerformed(evt);
            }
        });

        lblWoonplaats.setText("Woonplaats *");

        cmbGeslacht.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Man", "Vrouw" }));
        cmbGeslacht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGeslachtActionPerformed(evt);
            }
        });

        jLabel3.setText("Geslacht");

        lblWoonplaats1.setText("Tel. nacht");

        lblPostcode1.setText("Tel. dag *");

        lblPostcode2.setText("Geb. datum *");

        jLabel4.setText("Rekening *");

        jButton2Opslaanlid.setText("Opslaan");
        jButton2Opslaanlid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2OpslaanlidActionPerformed(evt);
            }
        });

        txtEmailadress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailadressActionPerformed(evt);
            }
        });

        jLabel2.setText("E-mail");

        cmbVestiging.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FitShape Alkmaar", "FitShape Haarlem", "FitShape Purmerend" }));
        cmbVestiging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbVestigingActionPerformed(evt);
            }
        });

        txtMemberId.setText("0");
        txtMemberId.setEnabled(false);

        jButton1.setText("Foto uploaden");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblFoto.setBackground(new java.awt.Color(204, 204, 204));
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/images/nophoto.jpg"))); // NOI18N

        jLabel6.setText("Vestiging");

        jLabel7.setText("Abonnement");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 9));
        jLabel8.setText("Formaat: dd-mm-jjjj");

        jLabel9.setText("Velden met een * zijn verplicht");

        btnEmail.setFont(new java.awt.Font("Tahoma", 0, 12));
        btnEmail.setText("Een e-mail sturen");
        btnEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmailActionPerformed(evt);
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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 354, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblVoorNaam)
                                            .addComponent(lblAdres)
                                            .addComponent(lblPostcode)))
                                    .addComponent(lblPostcode2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAdres)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtVoorNaam, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblTussenVoegsel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTussenVoegsel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtGebDatum)
                                            .addComponent(txtTelDag)
                                            .addComponent(txtPostcode, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(lblWoonplaats1)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblWoonplaats)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtTelNacht, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtRekNr)
                                                    .addComponent(txtWoonplaats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)))))))
                            .addComponent(lblPostcode1)
                            .addComponent(lblFoto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(lblHuisNummer)
                            .addComponent(lblAchterNaam)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cmbGeslacht, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtHuisnummer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblToevoeging)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtToevoeging, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                            .addComponent(txtAchterNaam, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtMemberId, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbAbonnementen, 0, 157, Short.MAX_VALUE)
                                    .addComponent(txtEmailadress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                    .addComponent(cmbVestiging, 0, 157, Short.MAX_VALUE)
                                    .addComponent(btnEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                                .addGap(3, 3, 3))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 461, Short.MAX_VALUE)
                        .addComponent(jButton2Opslaanlid)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVoorNaam)
                    .addComponent(txtTussenVoegsel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVoorNaam)
                    .addComponent(lblTussenVoegsel)
                    .addComponent(lblAchterNaam)
                    .addComponent(txtAchterNaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdres)
                    .addComponent(txtAdres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHuisNummer)
                    .addComponent(txtHuisnummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblToevoeging)
                    .addComponent(txtToevoeging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPostcode)
                    .addComponent(txtPostcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWoonplaats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWoonplaats)
                    .addComponent(jLabel3)
                    .addComponent(cmbGeslacht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelNacht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPostcode1)
                    .addComponent(lblWoonplaats1)
                    .addComponent(txtTelDag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmailadress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPostcode2)
                        .addComponent(jLabel4)
                        .addComponent(txtRekNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGebDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(35, 35, 35))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbVestiging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbAbonnementen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(txtMemberId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2Opslaanlid))
                .addGap(48, 48, 48))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbGeslachtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGeslachtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbGeslachtActionPerformed

    private void jButton2OpslaanlidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2OpslaanlidActionPerformed
        save();

    }//GEN-LAST:event_jButton2OpslaanlidActionPerformed

    private void txtPostcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPostcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPostcodeActionPerformed

    private void txtEmailadressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailadressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailadressActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    private void cmbVestigingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbVestigingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbVestigingActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        if (fc.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {

            try {
                String outFile = main.PASApplication.PHOTO_PATH + this.txtMemberId.getText() + "_" + fc.getSelectedFile().getName();
                java.io.FileInputStream fis = new java.io.FileInputStream(fc.getSelectedFile().getAbsolutePath());
                java.io.FileOutputStream fos = new java.io.FileOutputStream(outFile);
                System.out.println(outFile);

                byte[] buf = new byte[1024];
                int i = 0;
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }

                main.PASApplication.getQueryManager().insertPhoto(outFile, this.txtMemberId.getText());
                int memberid = Integer.parseInt(this.txtMemberId.getText());
                this.clear();
                this.edit(memberid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}//GEN-LAST:event_jButton1ActionPerformed

    private void btnEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmailActionPerformed
        try {
            if (this.txtEmailadress.getText().length() > 0) {
                Desktop.getDesktop().mail(new java.net.URI("mailto:" + this.txtEmailadress.getText()));
            } else {
                JOptionPane.showMessageDialog(null, "Er is geen e-mailadres ingevuld voor dit lid");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "De e-mailclient kan niet worden geopend");
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(null, "Het ingevulde e-mailadres is niet geldig");
        }
    }//GEN-LAST:event_btnEmailActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmail;
    private javax.swing.JComboBox cmbAbonnementen;
    private javax.swing.JComboBox cmbGeslacht;
    private javax.swing.JComboBox cmbVestiging;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2Opslaanlid;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblAchterNaam;
    private javax.swing.JLabel lblAdres;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblHuisNummer;
    private javax.swing.JLabel lblPostcode;
    private javax.swing.JLabel lblPostcode1;
    private javax.swing.JLabel lblPostcode2;
    private javax.swing.JLabel lblToevoeging;
    private javax.swing.JLabel lblTussenVoegsel;
    private javax.swing.JLabel lblVoorNaam;
    private javax.swing.JLabel lblWoonplaats;
    private javax.swing.JLabel lblWoonplaats1;
    private javax.swing.JTextField txtAchterNaam;
    private javax.swing.JTextField txtAdres;
    private javax.swing.JTextField txtEmailadress;
    private javax.swing.JTextField txtGebDatum;
    private javax.swing.JTextField txtHuisnummer;
    private javax.swing.JTextField txtMemberId;
    private javax.swing.JTextField txtPostcode;
    private javax.swing.JTextField txtRekNr;
    private javax.swing.JTextField txtTelDag;
    private javax.swing.JTextField txtTelNacht;
    private javax.swing.JTextField txtToevoeging;
    private javax.swing.JTextField txtTussenVoegsel;
    private javax.swing.JTextField txtVoorNaam;
    private javax.swing.JTextField txtWoonplaats;
    // End of variables declaration//GEN-END:variables
}
