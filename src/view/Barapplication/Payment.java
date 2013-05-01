package view.Barapplication;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFPage;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import main.*;
import model.Member;
import model.Product;
import view.Login;
import view.MainFrame;

/*
 * Class from old barapplication code, used in barapplication section.
 * This panel handles the payment of an order.
 */
public class Payment extends JPanel implements MouseListener, ActionListener, KeyListener  {

    /*
     * Vertical position of the labels in this panel
     */
    private final int verticalPosition = 80;
    /*
     * The offset for the labels that displays the products
     */
    private final int productOffset = 20;
    /*
     * Offset of the form
     */
    private final int formOffset = 30;
    /*
     * Object of the birthday field
     */
    private JTextField tfBirthday;
    /*
     * Object of the fistname field
     */
    private JTextField tfFirstname;
    /*
     * Object of the membernumber field
     */
    private JTextField tfMembercard;
    /*
     * Object of the lastname field
     */
    private JTextField tfLastname;
    /*
     * Object of the notition field
     */
    private JTextField tfNote;
    /*
     * Object of the send-button
     */
    private JButton btnSend;
    /*
     * Object of the 'Betaal Producten'-label
     */
    private JLabel lblPayProducts;
    /*
     * Object of the 'Vorige'-label
     */
    private JLabel lblPrevious;
    /*
     * Object of the 'Nieuw'-label
     */
    JLabel lblNew;
    /*
     * Format for the amount
     */
    private DecimalFormat df = new DecimalFormat("#0.00");

    /*
     * Call methods to initialize layout
     */
    public Payment() {
        super();
        this.setLayout(null);
        initComponents();
        
    }

    /*
     * Call methods to initialize layout
     */
    private void initComponents() {
        this.setBackground(Color.white);
        addTitle();
        addProductList();
        addForm();
    }

    /*
     * Add upper bar of panel
     */
    private void addTitle() {
        lblPayProducts = new JLabel();
        lblPayProducts.setText("Betaal producten (3/4)");
        lblPayProducts.setBounds(20, 20, 250, 20);
        lblPayProducts.setFont(PASApplication.FONT_16_BOLD);
        lblPayProducts.setName("-1");
        lblPayProducts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblPayProducts.addMouseListener(this);
        add(lblPayProducts);

        lblPrevious = new JLabel();
        lblPrevious.setText("(vorige)");
        lblPrevious.setBounds(350, 20, 100, 20);
        lblPrevious.setFont(PASApplication.FONT_16_BOLD);
        lblPrevious.setName("-2");
        lblPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblPrevious.addMouseListener(this);
        add(lblPrevious);

        lblNew = new JLabel();
        lblNew.setText("(nieuw)");
        lblNew.setBounds(450, 20, 100, 20);
        lblNew.setFont(PASApplication.FONT_16_BOLD);
        lblNew.setName("-3");
        lblNew.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblNew.addMouseListener(this);
        add(lblNew);
    }

    /*
     * Initialize productlist from basket
     */
    private void addProductList() {
        List<Product> products = PASApplication.getBasket().getProducts();

        JLabel lblProductHeader = new JLabel();
        lblProductHeader.setText("Producten");
        lblProductHeader.setBounds(20, 60, 150, 20);
        lblProductHeader.setFont(PASApplication.FONT_10_BOLD);
        add(lblProductHeader);

        JLabel lblAmountHeader = new JLabel();
        lblAmountHeader.setText("Aantal");
        lblAmountHeader.setBounds(400, 60, 150, 20);
        lblAmountHeader.setFont(PASApplication.FONT_10_BOLD);
        add(lblAmountHeader);

        JLabel lblPriceHeader = new JLabel();
        lblPriceHeader.setText("Prijs");
        lblPriceHeader.setBounds(480, 60, 150, 20);
        lblPriceHeader.setFont(PASApplication.FONT_10_BOLD);
        add(lblPriceHeader);

        //Iterate the basket contents
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            JLabel lblProduct = new JLabel(product.getName());
            lblProduct.setBounds(20, verticalPosition + i * productOffset, 420, 20);
            lblProduct.setFont(PASApplication.FONT_10_PLAIN);
            add(lblProduct);

            JLabel lblAmount = new JLabel(String.valueOf(PASApplication.getBasket().getProductAmount(product)));
            lblAmount.setBounds(410, verticalPosition + i * productOffset, 70, 20);
            lblAmount.setFont(PASApplication.FONT_10_PLAIN);
            add(lblAmount);

            JLabel lblPrice = new JLabel(PASApplication.CURRENCY + df.format(product.getPrice()));
            lblPrice.setBounds(480, verticalPosition + i * productOffset, 70, 20);
            lblPrice.setFont(PASApplication.FONT_10_PLAIN);
            add(lblPrice);
        }

        // create total labelsve
        JLabel lblTotal = new JLabel("Totaal: ");
        lblTotal.setBounds(20, verticalPosition + products.size() * productOffset, 50, 20);
        lblTotal.setFont(PASApplication.FONT_10_BOLD);
        add(lblTotal);

        // create total labels
        JLabel lblTotalPrice = new JLabel(PASApplication.CURRENCY + df.format(PASApplication.getBasket().getTotalCosts()));
        lblTotalPrice.setBounds(480, verticalPosition + products.size() * productOffset, 70, 20);
        lblTotalPrice.setFont(PASApplication.FONT_10_BOLD);
        add(lblTotalPrice);
    }

    /*
     * Intialize form to fill in member details
     */
    private void addForm() {
        List<Product> products = PASApplication.getBasket().getProducts();
        JLabel lblFormTitle = new JLabel("Gegevens lid:");
        lblFormTitle.setBounds(20, verticalPosition + products.size() * productOffset + (formOffset * 2), 150, 20);
        lblFormTitle.setFont(PASApplication.FONT_12_BOLD);
        add(lblFormTitle);

        JLabel lblMembernumber = new JLabel("Pasnummer:");
        lblMembernumber.setBounds(20, verticalPosition + products.size() * productOffset + (formOffset * 3), 100, 20);
        lblMembernumber.setFont(PASApplication.FONT_10_BOLD);
        add(lblMembernumber);

        tfMembercard = new JTextField();
        tfMembercard.setBounds(120, verticalPosition + products.size() * productOffset + (formOffset * 3), 130, 20);
        tfMembercard.setFont(PASApplication.FONT_10_BOLD);
        /*
         * add eventlisteners to field for membercard-field
         */
        tfMembercard.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {

                 // text was changed                
            }

            public void removeUpdate(DocumentEvent e) {
                
                // text was deleted
            }

            public void insertUpdate(DocumentEvent e) {
                
                // text was inserted

                String lidnummer = tfMembercard.getText();
                int memberId = Integer.parseInt(lidnummer);
                System.out.println("memberId:" + memberId);
                Member member = PASApplication.getQueryManager().getMemberPas(memberId);
                if (!(member == null)) {
                    System.out.println(member.getBirthday().toString());

                    DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
                    tfBirthday.setText(df.format(member.getBirthday()));
                    tfFirstname.setText(member.getFirstname());
                    tfLastname.setText(member.getLastname());
                }


            }
        });
        add(tfMembercard);

        JLabel lblBirthday = new JLabel("Geboortedatum:");
        lblBirthday.setBounds(320, verticalPosition + products.size() * productOffset + (formOffset * 3), 100, 20);
        lblBirthday.setFont(PASApplication.FONT_10_BOLD);
        add(lblBirthday);

        tfBirthday = new JTextField();
        tfBirthday.setBounds(420, verticalPosition + products.size() * productOffset + (formOffset * 3), 130, 20);
        tfBirthday.setFont(PASApplication.FONT_10_BOLD);
        tfBirthday.setEnabled(false);
        add(tfBirthday);

        JLabel lblFirstname = new JLabel("Voornaam:");
        lblFirstname.setBounds(20, verticalPosition + products.size() * productOffset + (formOffset * 4), 100, 20);
        lblFirstname.setFont(PASApplication.FONT_10_BOLD);
        add(lblFirstname);

        tfFirstname = new JTextField();
        tfFirstname.setBounds(120, verticalPosition + products.size() * productOffset + (formOffset * 4), 130, 20);
        tfFirstname.setFont(PASApplication.FONT_10_BOLD);
        tfFirstname.setEnabled(false);
        add(tfFirstname);

        JLabel lblLastname = new JLabel("Achternaam:");
        lblLastname.setBounds(320, verticalPosition + products.size() * productOffset + (formOffset * 4), 100, 20);
        lblLastname.setFont(PASApplication.FONT_10_BOLD);
        add(lblLastname);

        tfLastname = new JTextField();
        tfLastname.setBounds(420, verticalPosition + products.size() * productOffset + (formOffset * 4), 130, 20);
        tfLastname.setFont(PASApplication.FONT_10_BOLD);
        tfLastname.setEnabled(false);
        add(tfLastname);

        /*JLabel lblPayMethod = new JLabel("Betaalmethode:");
        lblPayMethod.setBounds(20, verticalPosition + products.size() * productOffset + (formOffset * 5), 100, 20);
        lblPayMethod.setFont(BarApplication.FONT_10_BOLD);
        add(lblPayMethod);*/

        /*cmbPayMethod = new JComboBox(payMethods);
        cmbPayMethod.setBounds(120, verticalPosition + products.size() * productOffset + (formOffset * 5), 250, 20);
        cmbPayMethod.setFont(BarApplication.FONT_10_BOLD);
        add(cmbPayMethod);*/

        JLabel lblNote = new JLabel("Opmerking:");
        lblNote.setBounds(20, verticalPosition + products.size() * productOffset + (formOffset * 6), 100, 20);
        lblNote.setFont(PASApplication.FONT_10_BOLD);
        add(lblNote);

        tfNote = new JTextField();
        tfNote.setBounds(120, verticalPosition + products.size() * productOffset + (formOffset * 6), 250, 80);
        tfNote.setFont(PASApplication.FONT_10_BOLD);
        add(tfNote);

        btnSend = new JButton("Betaal bestelling");
        btnSend.setBounds(120, verticalPosition + products.size() * productOffset + (formOffset * 9), 150, 20);
        btnSend.setFont(PASApplication.FONT_10_BOLD);
        btnSend.addActionListener(this);
        this.add(btnSend);


    }

    /*
     * Save payment of order
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        
        if (tfLastname.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Geen geldig lidnummer !.");
        } else {


            try {
                lblPayProducts.setText("Fitshape Sportscholen");
                lblPrevious.setVisible(false);
                lblNew.setVisible(false);
                tfFirstname.setEnabled(true);
                tfLastname.setEnabled(true);
                tfBirthday.setEnabled(true);

                PDFDocument pdfDoc = new PDFDocument();
                PDFPage page = pdfDoc.createPage(null);
                Graphics2D g2d = page.createGraphics();
                btnSend.setVisible(false);
                this.print(g2d);
                pdfDoc.addPage(page);
                pdfDoc.saveDocument("fitshape.pdf");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            String lidnummer = tfMembercard.getText();
            int memberId = Integer.parseInt(lidnummer);
            String opmerking = tfNote.getText();
            PASApplication.getQueryManager().setOrder(PASApplication.getBasket(), memberId, opmerking, PASApplication.getBasket().getTotalCosts(), Login.employeecode);
            PASApplication.getBasket().empty();
            view.MainFrame.addPanel(new OrderSend(), "OrderSend");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
       printIt("Released", keyEvent);        
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        printIt("Typed", keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        printIt("Pressed", keyEvent);
    }

    private void printIt(String title, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        String keyText = KeyEvent.getKeyText(keyCode);
        System.out.println(title + " : " + keyText);
    }

    /*
     * When cancel/previous button is pressed, change panel
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        JLabel label = (JLabel) event.getSource();
        if (label.getName().equals("-1")) {
            // the user clicked on title 1
        } else if (label.getName().equals("-2")) {
            // the user clicked on title 2
            view.MainFrame.previousCard();
        } else if (label.getName().equals("-3")) {
            // the user clicked on title 3
            main.PASApplication.getBasket().empty();
            MainFrame.changePanel(MainFrame.CATEGORYLIST);
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Intentionally left blank.
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Intentionally left blank.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Intentionally left blank.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Intentionally left blank.
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawLine(20, 45, 540, 45);		// under H Title
    }
}
