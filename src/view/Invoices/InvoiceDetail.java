package view.Invoices;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.PASApplication;
import view.MainFrame;
import model.*;

/*
 * Displays the invoicerules of the given invoice
 * @author Thom
 */
public class InvoiceDetail extends javax.swing.JPanel {

    /*
     * The invoice number that is loaded
     */
    private int iInvoiceNr = 0;

    /** Creates new form FactuurDetail */
    public InvoiceDetail() {
        initComponents();
    }

    /*
     * Initialize the data of invoice that needs to displayed
     * @param iInvoiceNr The invoicenumber of the invoice
     */
    public void loadInvoice(int iInvoiceNr) {
        Invoice selectedInvoice = null;
        this.iInvoiceNr = iInvoiceNr;
        try {
            selectedInvoice = PASApplication.getQueryManager().getInvoice(iInvoiceNr);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDetail.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (selectedInvoice != null) {
            this.factuurDatum.setText(selectedInvoice.getInvoiceDate().toGMTString());
            this.factuurNummer.setText(Integer.toString(selectedInvoice.getInvoiceNumber()));
            this.lidNaam.setText(selectedInvoice.getLidNaam());
            this.lidNummer.setText(Integer.toString(selectedInvoice.getLidNr()));
            this.vervalDatum.setText(selectedInvoice.getInvoiceExpire().toGMTString());
            if (selectedInvoice.getStatus() != null && selectedInvoice.getStatus().equals("betaald")) {
                this.invoiceStatus.setText("Factuur betaald");
            } else if (selectedInvoice.getStatus() != null && selectedInvoice.getStatus().equals("storno")) {
                this.invoiceStatus.setText("Factuur gestorneerd");
            } else {
                this.invoiceStatus.setText("Nog niet betaald");
            }

        }
        java.util.List<model.InvoiceRule> invoiceRules = null;
        try {
            invoiceRules = PASApplication.getQueryManager().getInvoiceRules(iInvoiceNr);
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDetail.class.getName()).log(Level.SEVERE, null, ex);
        }

        javax.swing.table.DefaultTableModel aModel = new javax.swing.table.DefaultTableModel() {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] columns = new Object[4];
        columns[0] = "Omschrijving";
        columns[1] = "Aantal";
        columns[2] = "Prijs";
        columns[3] = "Productnr.";

        aModel.setColumnIdentifiers(columns);

        Object[] contents = new Object[4];
        int i = 0;
        while (i < invoiceRules.size()) {
            model.InvoiceRule current = invoiceRules.iterator().next();
            contents[0] = current.getOmschrijving();
            contents[1] = current.getAantal();
            contents[2] = current.getPrijs();
            contents[3] = current.getProductNummer();
            aModel.addRow(contents);
            i++;
        }
        this.jTable1.setModel(aModel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        factuurNummer = new javax.swing.JLabel();
        factuurDatum = new javax.swing.JLabel();
        vervalDatum = new javax.swing.JLabel();
        lidNummer = new javax.swing.JLabel();
        lidNaam = new javax.swing.JLabel();
        btnPrevious = new javax.swing.JButton();
        btnPaid = new javax.swing.JButton();
        btnReturned = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        invoiceStatus = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Factuurnummer");

        jLabel2.setText("Factuurdatum");

        jLabel3.setText("Vervaldatum");

        jLabel4.setText("Lidnummer");

        jLabel5.setText("Lidnaam");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        factuurDatum.setText("jLabel6");

        vervalDatum.setText("jLabel6");

        lidNummer.setText("lidnummer");

        lidNaam.setText("jLabel6");

        btnPrevious.setText("< Vorige");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnPaid.setText("Betalen");
        btnPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaidActionPerformed(evt);
            }
        });

        btnReturned.setText("Storno");
        btnReturned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnedActionPerformed(evt);
            }
        });

        jLabel6.setText("Status");

        invoiceStatus.setText("status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(invoiceStatus)
                            .addComponent(lidNaam)
                            .addComponent(lidNummer)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(factuurNummer)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnReturned)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPaid)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(btnPrevious))
                            .addComponent(vervalDatum)
                            .addComponent(factuurDatum))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(factuurNummer)
                    .addComponent(btnPrevious)
                    .addComponent(btnPaid)
                    .addComponent(btnReturned))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(factuurDatum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(vervalDatum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(invoiceStatus))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lidNummer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lidNaam))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Shows the panel InvoiceSearch
     */
    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        MainFrame.changePanel(MainFrame.INVOICES);
    }//GEN-LAST:event_btnPreviousActionPerformed

    /*
     * Updates the status of the invoice to paid
     */
    private void btnPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaidActionPerformed
        PASApplication.getQueryManager().setInvoicePaid(this.iInvoiceNr);
    }//GEN-LAST:event_btnPaidActionPerformed

    /*
     * Updates the status of the invoice to returned
     */
    private void btnReturnedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnedActionPerformed
        PASApplication.getQueryManager().setInvoiceNotPaid(this.iInvoiceNr);
    }//GEN-LAST:event_btnReturnedActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPaid;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnReturned;
    private javax.swing.JLabel factuurDatum;
    private javax.swing.JLabel factuurNummer;
    private javax.swing.JLabel invoiceStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lidNaam;
    private javax.swing.JLabel lidNummer;
    private javax.swing.JLabel vervalDatum;
    // End of variables declaration//GEN-END:variables
}