package view.Barapplication;

import utils.QueryManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Category;
import view.MainFrame;
import main.*;

/*
 * Class from old barapplication code, used in barapplication section.
 * This panel displays the categories of the available products.
 */
public class CategoryList extends JPanel implements MouseListener {
    /*
     * Vertical position of the labels for the categories
     */
    private final int verticalPosition = 60;
    /*
     * Offset of the labels displayed
     */
    private final int offset = 40;

    /*
     * Call methods to intialize layout
     */
    public CategoryList() {
        super();
        setLayout(null);
        initComponents();
    }

    /** create the gui for this page */
    private void initComponents() {
        this.setBackground(Color.white);
        addTitle();
        addcategoryItems();
        addBasket();
    }

    /** add the page's title */
    private void addTitle() {


        JLabel lblTitle = new JLabel();
        lblTitle.setText("Kies een categorie (1/4)");
        lblTitle.setBounds(20, 20, 300, 20);
        lblTitle.setFont(PASApplication.FONT_16_BOLD);
        add(lblTitle);

    
    }

    /** add the different catergories to the page */
    private void addcategoryItems() {
        List<Category> categories = PASApplication.getQueryManager().getCategories();
        
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            JLabel lblDot = new JLabel("\u2022");
            lblDot.setFont(PASApplication.FONT_12_BOLD);
            lblDot.setBounds(20, verticalPosition + i * offset, 10, 20);
            add(lblDot);

            JLabel lblCategorie = new JLabel();
            lblCategorie.setName(String.valueOf(category.getCategoryId()));
            lblCategorie.setText(category.getName());
            lblCategorie.setBounds(35, verticalPosition + i * offset, 200, 20);
            lblCategorie.setFont(PASApplication.FONT_12_BOLD);
            lblCategorie.addMouseListener(this);
            lblCategorie.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            add(lblCategorie);

            JLabel lblDescription = new JLabel();
            lblDescription.setText(category.getDescription());
            lblDescription.setBounds(35, verticalPosition  + i * offset + 15, 200, 20);
            lblDescription.setFont(PASApplication.FONT_10_PLAIN);
            add(lblDescription);
        }
    }

    /** add the basket view into this page */
    private void addBasket() {
        BasketDisplay basket = new BasketDisplay();
        this.add(basket);
    }

    /*
     * Whenever a label is clicked, set categoryid and swith panel to ProductList
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        JLabel label = (JLabel) event.getSource();
        int categoryId = Integer.parseInt(label.getName());
        ProductList productList = new ProductList(categoryId);
        //BarApplication.getInstance().showPanel(productList);
        MainFrame.addPanel(productList, "Cat"+categoryId);
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
        graphics.drawLine(20, 45, 440, 45);
    }
}
