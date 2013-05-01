package view.Barapplication;

import java.awt.Color;
import main.*;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Product;
import view.MainFrame;

/*
 * Class from old barapplication code, used in barapplication section.
 * Detailed information of selected product
 */
public class ProductDetails extends JPanel implements MouseListener, ActionListener {

    /*
     * Contains the product id selected
     */
    private final int product_id;

    /*
     * Calls the methods to initialize layout and sets attribute product_id
     * @param product_id Product id to select info
     */
    public ProductDetails(int product_id) {
        super();
        /** Set name, layout and Background */
        this.setLayout(null);
        this.product_id = product_id;
        initComponents();
    }

    /*
     * Calls methods to initialize layout
     */
    private void initComponents() {
        this.setBackground(Color.white);
        addTitle();
        addSubTitle();
        addProduct();
        addBasket();
    }

    /*
     * Add title to gui
     */
    private void addTitle() {
        JLabel lblTitle1 = new JLabel();
        lblTitle1.setText("Product informatie (2/4)");
        lblTitle1.setBounds(20, 20, 300, 20);
        lblTitle1.setFont(PASApplication.FONT_16_BOLD);
        lblTitle1.setName("-1");
        lblTitle1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle1.addMouseListener(this);
        add(lblTitle1);
    }

    /*
     * Add upper bar to gui
     */
    private void addSubTitle() {
        JLabel lblTitle2 = new JLabel();
        lblTitle2.setText("(vorige)");
        lblTitle2.setBounds(350, 20, 100, 20);
        lblTitle2.setFont(PASApplication.FONT_16_BOLD);
        lblTitle2.setName("-2");
        lblTitle2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle2.addMouseListener(this);
        add(lblTitle2);
        JLabel lblTitle3 = new JLabel();
        lblTitle3.setText("(nieuw)");
        lblTitle3.setBounds(450, 20, 100, 20);
        lblTitle3.setFont(PASApplication.FONT_16_BOLD);
        lblTitle3.setName("-3");
        lblTitle3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle3.addMouseListener(this);
        add(lblTitle3);
    }

    /*
     * Add product info to gui
     */
    private void addProduct() {
        Product currentProduct = PASApplication.getQueryManager().getProduct(product_id);

        JLabel lblProductName = new JLabel();
        lblProductName.setText(currentProduct.getName());
        lblProductName.setBounds(20, 60, 500, 20);
        lblProductName.setFont(PASApplication.FONT_16_BOLD);
        add(lblProductName);

        JLabel lblDescription = new JLabel();
        lblDescription.setText(currentProduct.getDescription());
        lblDescription.setBounds(20, 80, 500, 20);
        lblDescription.setFont(PASApplication.FONT_10_PLAIN);
        add(lblDescription);

        JLabel lblProductPrice = new JLabel();
        lblProductPrice.setText("Price: " + PASApplication.CURRENCY + currentProduct.getPrice());
        lblProductPrice.setBounds(20, 100, 500, 20);
        lblProductPrice.setFont(PASApplication.FONT_10_PLAIN);
        add(lblProductPrice);

        JButton btnOrder = new JButton("Toevoegen aan Bestelling");
        btnOrder.setBounds(20, 140, 230, 20);
        btnOrder.setFont(PASApplication.FONT_12_BOLD);
        btnOrder.addActionListener(this);
        add(btnOrder);
    }

    /*
     * add basket to gui
     */
    private void addBasket() {
        BasketDisplay basket = new BasketDisplay();
        add(basket);
    }

    /*
     * When button clicked, add product
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Product currentProduct = PASApplication.getQueryManager().getProduct(product_id);
        main.PASApplication.getBasket().addProduct(currentProduct);
    }

    /*
     * Change panel on mouse click
     */
    @Override
    public void mouseClicked(MouseEvent event) {

        System.out.println(event.getComponent().toString());
        // the user clicked on the title, go back to the first screen
        //BarApplication.getInstance().showPanel(new view.CategoryList());
        MainFrame.changePanel(MainFrame.CATEGORYLIST);
        JLabel label = (JLabel) event.getSource();
      //System.out.println("Label: " + label.getName().toString());
        if (label.getName().equals("-1")) {
            // the user clicked on the title, go back to the first screen

        } else if (label.getName().equals("-2")) {
            MainFrame.changePanel(MainFrame.CATEGORYLIST);
        } else if (label.getName().equals("-3")) {
            main.PASApplication.getBasket().empty();
            MainFrame.changePanel(MainFrame.CATEGORYLIST);
        } else {

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
        graphics.drawLine(20, 45, 540, 45);
    }
}
