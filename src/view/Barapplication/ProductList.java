package view.Barapplication;

import java.awt.Color;
import main.*;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.PASApplication;
import model.Product;
import view.MainFrame;

/*
 * Class from old barapplication code, used in barapplication section.
 * Displays all products of the chosen category
 */
public class ProductList extends JPanel implements MouseListener, ActionListener {

    /*
     * Contains the categoryid of the products are shown
     */
    private final int categoryId;
    /*
     * Verticalposition of the labels
     */
    private final int verticalPosition = 60;
    /*
     * Offset of the labels
     */
    private final int offset = 40;

    /*
     * Calls methods to initialize layout and sets the attribute categoryid
     * @param categoryid Category id which product needs to be shown
     */
    public ProductList(int categoryId) {
        super();
        this.setLayout(null);
        this.categoryId = categoryId;
        initComponents();
    }

    /*
     * Calls the methods to initialize layout
     */
    private void initComponents() {
        this.setBackground(Color.white);
        addTitle();
        addProductItems();
        addBasket();
    }

    /*
     * Add products to the gui
     */
    private void addProductItems() {
        List<Product> products = PASApplication.getQueryManager().getProducts(categoryId);
         DecimalFormat df = new DecimalFormat("#0.00");

         //iterate the contents of the basket
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            
            JLabel lblDot = new JLabel("\u2022");
            lblDot.setFont(PASApplication.FONT_12_BOLD);
            lblDot.setBounds(20, verticalPosition + i * offset, 10, 20);
            add(lblDot);

            JLabel lblProduct = new JLabel(product.getName());
            lblProduct.setBounds(35, verticalPosition + i * offset, 340, 20);
            lblProduct.setFont(PASApplication.FONT_12_BOLD);
            lblProduct.setName(String.valueOf(product.getProductId()));
            lblProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblProduct.addMouseListener(this);
            add(lblProduct);

            JLabel lblDescription = new JLabel(product.getDescription());
            lblDescription.setBounds(35, verticalPosition + i * offset + 15, 340, 20);
            lblDescription.setFont(PASApplication.FONT_10_PLAIN);
            this.add(lblDescription);

            JLabel lblPrice = new JLabel(PASApplication.CURRENCY + df.format(product.getPrice()));
            lblPrice.setBounds(310, verticalPosition  + i * offset + 15, 80, 20);
            lblPrice.setFont(PASApplication.FONT_12_BOLD);
            add(lblPrice);

            JButton btnOrder = new JButton("+");
            btnOrder.setBounds(380, verticalPosition  + i * offset + 15, 50, 20);
            btnOrder.setFont(PASApplication.FONT_12_BOLD);
            btnOrder.setName(String.valueOf(product.getProductId()));
            btnOrder.addActionListener(this);
            add(btnOrder);
            
            JButton btnRollbackOrder = new JButton("-");
            btnRollbackOrder.setBounds(430, verticalPosition  + i * offset + 15, 50, 20);
            btnRollbackOrder.setFont(PASApplication.FONT_12_BOLD);
            btnRollbackOrder.setName(String.valueOf(product.getProductId()));
            btnRollbackOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRollbackOrderMouseClicked(evt);
            }
        });
            add(btnRollbackOrder);
        }

    }

    /*
     * Add basket to gui
     */
    private void addBasket() {
        BasketDisplay basket = new BasketDisplay();
        add(basket);
    }

    /*
     * Add uper bar to gui
     */
    private void addTitle() {
        JLabel lblTitle1 = new JLabel();
        lblTitle1.setText("Kies een product (2/4)");
        lblTitle1.setBounds(20, 20, 250, 20);
        lblTitle1.setFont(PASApplication.FONT_16_BOLD);
        lblTitle1.setName("-1");
        lblTitle1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle1.addMouseListener(this);
        add(lblTitle1);

        JLabel lblTitle2 = new JLabel();
        lblTitle2.setText("(vorige)");
        lblTitle2.setBounds(280, 20, 100, 20);
        lblTitle2.setFont(PASApplication.FONT_16_BOLD);
        lblTitle2.setName("-2");
        lblTitle2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle2.addMouseListener(this);
        add(lblTitle2);

        JLabel lblTitle3 = new JLabel();
        lblTitle3.setText("(nieuw)");
        lblTitle3.setBounds(380, 20, 100, 20);
        lblTitle3.setFont(PASApplication.FONT_16_BOLD);
        lblTitle3.setName("-3");
        lblTitle3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle3.addMouseListener(this);
        add(lblTitle3);
    }
    
    /*
     * When product clicked, go to product detail
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        JLabel label = (JLabel) event.getSource();
      //System.out.println("Label: " + label.getName().toString());
        if (label.getName().equals("-1")) {
            // the user clicked on the title, go back to the first screen
            
        } else if (label.getName().equals("-2")) {
            MainFrame.addPanel(new view.Barapplication.CategoryList(), "Product"+label.getName());
        } else if (label.getName().equals("-3")) {
            main.PASApplication.getBasket().empty();
            MainFrame.addPanel(new view.Barapplication.CategoryList(), "Product"+label.getName());
        } else {
            MainFrame.addPanel(new view.Barapplication.ProductDetails(Integer.parseInt(label.getName())), "Product"+label.getName());
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

    /*
     * Adds product to basket
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String product_id = ((JButton) event.getSource()).getName();
        Product product = main.PASApplication.getQueryManager().getProduct(Integer.parseInt(product_id));
        main.PASApplication.getBasket().addProduct(product);
    }
    
    /*
     * Removes product from basket
     */
    private void btnRollbackOrderMouseClicked(java.awt.event.MouseEvent event) {                                         
        // TODO add your handling code here:
        String product_id = ((JButton) event.getSource()).getName();
        Product product = main.PASApplication.getQueryManager().getProduct(Integer.parseInt(product_id));
        main.PASApplication.getBasket().rollbackProduct(product);

    }       

    
        
   /*  public void action2Performed(ActionEvent event) {
        String product_id = ((JButton) event.getSource()).getName();
        Product product = main.PASApplication.getQueryManager().getProduct(Integer.parseInt(product_id));
        main.PASApplication.getBasket().rollbackProduct(product);
    }*/


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawLine(20, 45, 470, 45);
    }
}
