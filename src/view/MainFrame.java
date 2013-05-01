package view;

import view.Members.*;
import view.Barapplication.*;
import view.Products.*;
import view.Invoices.*;
import view.Accounts.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.PASApplication;

/**
 *
 * @author Thom Puiman
 * This class initializes the JFrame that is used as container for all the panels of this application
 */
public class MainFrame {

    /*
     * This JPanel uses a CardLayout that contains all panels of the applcation
     */
    public static JPanel cards;
    /*
     * This is the JFrame used as container for the menu and panels
     */
    public static JFrame frame;
    /*
     * Strings used as identifier for each panel
     */
    public final static String MEMBERS = "Lid aanmaken";
    public final static String SEARCHMEMBERS = "Lid zoeken";
    public final static String INVOICES = "Facturen";
    public final static String STARTSCREEN = "Beginscherm";
    public final static String CATEGORYLIST = "Barapplicatie";
    public final static String INUITCHECKEN = "In/uitchecken";
    public final static String PRODUCTZOEKEN = "Product zoeken";
    public final static String PRODUCTTOEVOEGEN = "Product aanmaken";
    public final static String MODIFYINVOICE = "Wijzig Factuur";
    public final static String ACCOUNT = "Account aanmaken";
    public final static String ACCOUNTZOEKEN = "Account zoeken";
    public final static String CURSUSINSCHRIJVING = "Cursus inschrijving";
    public final static int    ROL_MEDEWERKER = 3;
    public final static int    ROL_BEHEER = 2;
    public final static int    ROL_MANAGER = 1;

    /*
     * Initialize all panels of the application and put into container panel
     * @param pane This represents this contentpane of the JFrame
     */
    public void initPanels(Container pane) throws SQLException {
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.setPreferredSize(new Dimension(PASApplication.FRAME_WIDTH,PASApplication.FRAME_HEIGHT));
        //Initialize all panels and this to the container panel
        cards.add(new Start(), STARTSCREEN);
        cards.add(new MemberRegistration(), MEMBERS);
        cards.add(new MemberSearch(), SEARCHMEMBERS);
        cards.add(new InvoiceSearch(), INVOICES);
        cards.add(new CategoryList(), CATEGORYLIST);
        cards.add(new CheckInOut(), INUITCHECKEN);
        cards.add(new InvoiceDetail(), MODIFYINVOICE);
        cards.add(new CourseRegistration(), CURSUSINSCHRIJVING);
        
        if(main.PASApplication.getQueryManager().getRol() >= this.ROL_BEHEER){
            cards.add(new ProductZoeken(), PRODUCTZOEKEN);
            cards.add(new ProductAdd(), PRODUCTTOEVOEGEN);
            cards.add(new AccountAdd(), ACCOUNT);
            cards.add(new AccountSearch(), ACCOUNTZOEKEN);
        }

        //Add logo image to frame
        JLabel lblLogo = new JLabel();
        lblLogo.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/main/FitShape_Logo.png")));
        lblLogo.setSize(400, 80);

        pane.add(lblLogo, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        pane.setBackground(Color.white);
        lblLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //Make logo clickable
        lblLogo.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.changePanel(MainFrame.STARTSCREEN);
            }
        });
    }
    
    /*
     * Change the cardlayout to another panel
     * @param panel Identifier of the panel
     */
    public static void changePanel(String panel) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) panel);
    }

    /*
     * Add a panel to the cards-container
     * @param panel The object of the panel
     * @param panelName A identifier for the new panel
     */
    public static void addPanel(JPanel panel, String panelName) {
        cards.add(panel, panelName);
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) panelName);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void initFrame() throws SQLException {
        //Create and set up the window.
        frame = new JFrame(PASApplication.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        MainFrame mFrame = new MainFrame();

        mFrame.initPanels(frame);
        frame.setJMenuBar(mFrame.getMenuBar());
        frame.getContentPane().setBackground(Color.white);
        //Display the window.
        frame.setSize(PASApplication.FRAME_WIDTH, PASApplication.FRAME_HEIGHT);
        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Get the previous showed panel in the collection of cards
     */
    public static void previousCard() {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.previous(cards);
    }
    
    /*
     * Initialize the topmenubar and its items
     * @return The menubar object
     */
    private JMenuBar getMenuBar() {
        int rolId = main.PASApplication.getQueryManager().getRol();
        // Creates a menubar for the JFrame
        JMenuBar menuBar = new JMenuBar();

        // Create the different menu's and attach to menubar
        JMenu memberMenu = new JMenu("Ledenadministratie");
        menuBar.add(memberMenu);
        JMenu barapplicatieMenu = new JMenu("Barapplicatie");
        menuBar.add(barapplicatieMenu);
        JMenu beheerMenu = new JMenu("Beheer");
        menuBar.add(beheerMenu);
        JMenu factuurMenu = new JMenu("Facturatie");
        menuBar.add(factuurMenu);
        JMenu rapportageMenu = null;
        if(rolId == this.ROL_MANAGER){
            rapportageMenu = new JMenu("Rapportages");
            menuBar.add(rapportageMenu);
        }
        
        /*
         * Create its items and events
         */
        JMenuItem startAction = new JMenuItem(CATEGORYLIST);
        barapplicatieMenu.add(startAction);
        startAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, CATEGORYLIST);
            }
        });

        JMenu memberSubmenu = new JMenu("Ledenadministratie");

        // Create and add simple menu item to one of the drop down menu
        JMenuItem newAction = new JMenuItem(MEMBERS);
        newAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                clearModifyMember();
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, MEMBERS);
            }
        });
        memberSubmenu.add(newAction);
        memberSubmenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem searchAction = new JMenuItem(SEARCHMEMBERS);
        searchAction.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        searchAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                addPanel(new MemberSearch(), MainFrame.SEARCHMEMBERS);
                cl.show(cards, SEARCHMEMBERS);
            }
        });
        memberSubmenu.add(searchAction);

        JMenuItem cursusAction = new JMenuItem(CURSUSINSCHRIJVING);
        cursusAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                clearModifyMember();
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, CURSUSINSCHRIJVING);
            }
        });
        memberSubmenu.add(cursusAction);

        //fileMenu.add(memberSubmenu);
        memberMenu.add(searchAction);
        memberMenu.add(newAction);
        memberMenu.add(cursusAction);


        JMenu productSubmenu = new JMenu("Productbeheer");



        // Create and add simple menu item to one of the drop down menu
        JMenuItem newProductAction = new JMenuItem(PRODUCTTOEVOEGEN);
        newProductAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                clearModifyProduct();
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, PRODUCTTOEVOEGEN);
            }
        });
        beheerMenu.add(newProductAction);

        beheerMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem searchProductAction = new JMenuItem(PRODUCTZOEKEN);
        searchProductAction.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        searchProductAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, PRODUCTZOEKEN);
            }
        });
        beheerMenu.add(searchProductAction);




        JMenuItem invAction = new JMenuItem(INVOICES);
        factuurMenu.add(invAction);
        invAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, INVOICES);
            }
        });



        JMenuItem inuitAction = new JMenuItem(INUITCHECKEN);
        memberMenu.add(inuitAction);
        inuitAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, INUITCHECKEN);
            }
        });


        JMenuItem newAccount = new JMenuItem(ACCOUNT);
        beheerMenu.add(newAccount);
        newAccount.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                clearAccountModify();
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, ACCOUNT);
            }
        });

        JMenuItem searchAccount = new JMenuItem(ACCOUNTZOEKEN);
        beheerMenu.add(searchAccount);
        searchAccount.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, ACCOUNTZOEKEN);
            }
        });

        if(rolId == this.ROL_MANAGER){
            JMenuItem pathAction = new JMenuItem("Bestelde producten per uur");
            pathAction.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    System.out.println("he");
                    view.Reports.ReportSelection rs = new view.Reports.ReportSelection("OrdersByHour");
                    rs.pack();
                    rs.setVisible(true);
                    //main.PASApplication.writePathToRegistry(f.getAbsolutePath());
                }
            });

            rapportageMenu.add(pathAction);
        }

        return menuBar;
    }

    /*
     * Call the edit() method of the object of MemberRegistration stored in the cards-collection
     * @param iMemberId Id of the member to edit
     */
    public static void setModifyMember(int iMemberId) {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("MemberRegistration")) {
                MemberRegistration wpnl = (MemberRegistration) newpnl;
                wpnl.clear();
                wpnl.edit(iMemberId);
            }
        }
    }

    /*
     * Call the clear() method of the object of MemberRegistration stored in the cards-collection
     */
    public static void clearModifyMember() {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("MemberRegistration")) {
                MemberRegistration wpnl = (MemberRegistration) newpnl;
                wpnl.clear();
            }
        }
    }

    /*
     * Call the edit() method of the object of ProductAdd stored in the cards-collection
     * @param iProductID Id of the product to edit
     */
    public static void setModifyProduct(int iProductID) {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("ProductAdd")) {
                ProductAdd ppnl = (ProductAdd) newpnl;
                ppnl.edit(iProductID);
            }
        }
    }

    /*
     * Call the clear() method of the object of ProductAdd stored in the cards-collection
     */
    public static void clearModifyProduct() {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("ProductAdd")) {
                ProductAdd ppnl = (ProductAdd) newpnl;
                ppnl.clear();
            }
        }
    }

    /*
     * Call the edit() method of the object of AccountAdd stored in the cards-collection
     * @param iAccountID The account id of the account you want to edit
     */
    public static void setAccountModify(int iAccountID) {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("AccountAdd")) {
                AccountAdd apnl = (AccountAdd) newpnl;
                apnl.edit(iAccountID);
            }
        }
    }

    /*
     * Call the clear() method of the object of AccountAdd stored in the cards-collection
     */
    public static void clearAccountModify() {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("AccountAdd")) {
                AccountAdd apnl = (AccountAdd) newpnl;
                apnl.clear();
            }
        }
    }

    /*
     * Call the loadInvoice() method of the object of InvoiceDetail stored in the cards-collection
     * @param iInvoiceId The invoice id of the invoice to be loaded
     */
    public static void setModifyInvoice(int iInvoiceId) {
        for (Component comp : cards.getComponents()) {
            JPanel newpnl = (JPanel) comp;
            if (newpnl.getClass().getName().contains("InvoiceDetail")) {
                InvoiceDetail wpnl = (InvoiceDetail) newpnl;
                wpnl.loadInvoice(iInvoiceId);
            }
        }
    }

    public void initApplication() {

        /* Use another Look and Feel then the default one */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    initFrame();
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenu1.setText("Bestand");

        jMenu3.setText("Leden zoeken");
        jMenu1.add(jMenu3);

        jMenuItem1.setText("Barapplicatie");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Acties");
        jMenuBar1.add(jMenu2);

    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
