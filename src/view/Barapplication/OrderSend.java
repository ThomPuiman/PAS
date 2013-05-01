package view.Barapplication;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.*;
import view.MainFrame;

/*
 * Class from old barapplication code, used in barapplication section.
 * This panel is displayed after an order has been paid.
 */
public class OrderSend extends JPanel implements ActionListener, MouseListener {

    /*
     * Calls methods to initialize the layout of this panel
     */
    public OrderSend() {

        super();
        this.setLayout(null);
        initComponents();
    }

    /*
     * Initialize the layout of this panel
     */
    private void initComponents() {
        this.setBackground(Color.white);
        // display title
        JLabel lblTitle1 = new JLabel();
        lblTitle1.setText("Print bon (4/4)");
        lblTitle1.setBounds(20, 20, 150, 20);
        lblTitle1.setFont(PASApplication.FONT_16_BOLD);
        lblTitle1.setName("-1");
        lblTitle1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle1.addMouseListener(this);
        add(lblTitle1);

        JLabel lblTitle2 = new JLabel();
        lblTitle2.setText("");
        lblTitle2.setBounds(350, 20, 100, 20);
        lblTitle2.setFont(PASApplication.FONT_16_BOLD);
        lblTitle2.setName("-2");
        lblTitle2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle2.addMouseListener(this);
        add(lblTitle2);

        JLabel lblTitle3 = new JLabel();
        lblTitle3.setText("");
        lblTitle3.setBounds(450, 20, 100, 20);
        lblTitle3.setFont(PASApplication.FONT_16_BOLD);
        lblTitle3.setName("-3");
        lblTitle3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTitle3.addMouseListener(this);

        add(lblTitle3);

        JLabel lblMessage = new JLabel();
        lblMessage.setText("");
        lblMessage.setBounds(20, 80, 500, 20);
        lblMessage.setFont(PASApplication.FONT_12_BOLD);
        add(lblMessage);

        JButton btnPrintBon = new JButton("Print Bon");
        btnPrintBon.setBounds(20, 100, 250, 20);
        btnPrintBon.setFont(PASApplication.FONT_10_BOLD);
        btnPrintBon.addActionListener(this);
        add(btnPrintBon);

        JButton btnGoBack = new JButton("Nieuwe barorder");
        btnGoBack.setBounds(20, 140, 250, 20);
        btnGoBack.setFont(PASApplication.FONT_10_BOLD);
        btnGoBack.addActionListener(this);
        add(btnGoBack);
    }

    /*
     * One of the buttons on this screen has been clicked, switch back to categorylist panel
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        MainFrame.changePanel(MainFrame.CATEGORYLIST);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // Intentionally left blank.
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // Intentionally left blank.
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // Intentionally left blank.
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // Intentionally left blank.
    }
    
    /*
     * One of the buttons on this screen has been clicked, switch back to categorylist panel
     * @TODO add posibility (from old barapplication code) to print invoice of the order
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        JLabel label = (JLabel) event.getSource();
        if (label.getName().equals("-1")) {
            // the user clicked on title 1
        } else if (label.getName().equals("-2")) {
            // the user clicked on title 2
        } else if (label.getName().equals("-3")) {
            // the user clicked on title 3
            MainFrame.changePanel(MainFrame.CATEGORYLIST);
        }
    }
    
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawLine(20, 45, 540, 45);		// under H Title
    }
}
