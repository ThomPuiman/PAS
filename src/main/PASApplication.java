package main;

import utils.*;
import model.*;
import view.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import java.util.prefs.Preferences;


/*
 * @author Thom Puiman
 * This class initialize the connection to the datase, creates an instance of 
 * MainFrame and asks the user for his credentials to access the application
 */
public class PASApplication {

    /*
     * Used for an instance of the QueryManager
     */
    private static QueryManager qryManager;
    /*
     * Used for an instance of the DbManager
     */
    private static DbManager dbManager;
    /*
     * Used for an instance of the Basket
     */
    public static Basket basket;
    /*
     * Sets the width of the MainFrame
     */
    public static final int FRAME_WIDTH = 700;
    /*
     * Sets the height of the MainFrame
     */
    public static final int FRAME_HEIGHT = 600;
    /*
     * Sets the title of the MainFrame
     */
    public static final String NAME = "Fitshape Sportscholen";
    /*
     * Sets the currency for the barapplication and invoicing
     */
    public static final String CURRENCY = "€ ";
    /** static fonts which are used within the application */
    public static final Font FONT_10_PLAIN = new Font("Verdana", Font.PLAIN, 10);
    public static final Font FONT_10_BOLD = new Font("Verdana", Font.BOLD, 10);
    public static final Font FONT_12_BOLD = new Font("Verdana", Font.BOLD, 12);
    public static final Font FONT_16_BOLD = new Font("Verdana", Font.BOLD, 16);
    public static String DATE_FORMAT = "yyyyMMdd";
    public static String PHOTO_PATH = "C:\\fotos\\";

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException {
        dbManager = new DbManager();
        dbManager.openConnection();
        qryManager = new QueryManager(dbManager);
        basket = new Basket();

        Login loginFrame = new Login();
        loginFrame.pack();
        loginFrame.setVisible(true);
        
        /*
         * Wait until user has inserted valid credentials
         */
        while (loginFrame.gethasAccess() == false) {
            /*
             * If true, user requested to stop execution of the application
             */
            if (loginFrame.getcloseApp()) {
                loginFrame.setVisible(false);
                loginFrame.dispose();
                System.exit(0);
                break;
            }

            loginFrame.repaint();
        }
        loginFrame.setVisible(false);
        loginFrame.dispose();
        MainFrame mFrame = new MainFrame();
        mFrame.initApplication();

    }

    /*
     * Get an instance of the QueryManager class
     */
    public static QueryManager getQueryManager() {
        return qryManager;
    }

    /*
     * Use this function write the path of the Access report macro to the Windows registry
     * @param reportPath This is the full path of the Access macro path
     */
    public static void writePathToRegistry(String reportPath) {
        Preferences userPref = Preferences.userRoot();
        userPref.put("PAS.ReportPath", reportPath);
    }

    /*
     * Retrieve the full path of the value set in writePathToRegistry()
     */
    public static String readPathFromRegistry() {
        Preferences userPref = Preferences.userRoot();
        return userPref.get("PAS.ReportPath", "");
    }

    /*
     * Get the instance of the Basket class
     */
    public static Basket getBasket() {
        return basket;
    }
}
