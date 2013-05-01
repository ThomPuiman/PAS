package view.Barapplication;

import main.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Product;

/*
 * Class from old barapplication code, used in barapplication section.
 * Called from PASApplication class when application starts, and used by other barapplication classes
 */
public class BasketDisplay extends JPanel implements ActionListener, Observer {

    /*
     * Call methods to initialize this panel
     */
    public BasketDisplay() {
        super();
        setLayout(null);
        setBackground(new Color(151, 186, 225));
        setSize(190, 200);
        setLocation(500, 20);
        PASApplication.basket.addObserver(this);
        initComponents();
    }

    /*
     * Initialize layout panel
     */
    private void initComponents() {

        removeAll();
        model.Basket basket = PASApplication.basket;
        // if product basket is to big, resize the basket frame
        if (basket.size() > 6) {
            setSize(190, (300 + ((basket.size() - 6) * 10)));
        }
        DecimalFormat df = new DecimalFormat("#0.00");

        JLabel lblTitle = new JLabel();
        lblTitle.setText("Bestelling");
        lblTitle.setBounds(5, 5, 150, 20);
        lblTitle.setFont(PASApplication.FONT_12_BOLD);
        add(lblTitle);

        int verticalPosition = 40;

        /*
         * Display contents of basket
         */
        for (Product product : basket.getProducts()) {
            JLabel lblProduct = new JLabel(basket.getProductAmount(product) +
                    " - " + product.toString());
            lblProduct.setBounds(5, verticalPosition, 130, 20);
            lblProduct.setFont(PASApplication.FONT_10_PLAIN);
            add(lblProduct);

            JLabel lblPrice = new JLabel(PASApplication.CURRENCY + df.format(product.getPrice()));
            lblPrice.setBounds(140, verticalPosition, 150, 20);
            lblPrice.setFont(PASApplication.FONT_10_PLAIN);
            add(lblPrice);

            verticalPosition += 20;
        }

        JLabel lblTotal = new JLabel("Totaal: ");
        lblTotal.setBounds(5, verticalPosition, 50, 20);
        lblTotal.setFont(PASApplication.FONT_10_BOLD);
        add(lblTotal);

        JLabel lblTotalPrice = new JLabel(PASApplication.CURRENCY + df.format(basket.getTotalCosts()));
        lblTotalPrice.setBounds(140, verticalPosition, 50, 20);
        lblTotalPrice.setFont(PASApplication.FONT_10_BOLD);
        add(lblTotalPrice);

        int btnOffset = getHeight() - 25;
        JButton btnGoToPay = new JButton("Betalen...");
        btnGoToPay.setBounds(5, btnOffset, 180, 20);
        btnGoToPay.setFont(PASApplication.FONT_12_BOLD);
        btnGoToPay.addActionListener(this);
        /*
         * basket empty, disable pay button
         */
        if (basket.size() <= 0) {
            btnGoToPay.setEnabled(false);
        }
        add(btnGoToPay);
    }

    /*
     * Used to update panel
     */
    @Override
    public void update(Observable observable, Object arg) {
        initComponents();
        revalidate();
        repaint();
    }

    /*
     * If the button for to pay the order is pushed, go to Payment-panel
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        view.MainFrame.addPanel(new Payment(), "Payment");
    }
}
