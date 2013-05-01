/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.awt.MediaTracker;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;

/**
 *
 * @author Gebruiker
 */
public class Functions {
    
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDate(CharSequence date) {

        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";

        // define here all date format
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        patterns.add(Pattern.compile(day + "[-]" + month + "[-]" + year));
        // here you can add more date formats if you want

        // check dates
        for (Pattern p : patterns) {
            if (p.matcher(date).matches()) {
                return true;
            }
        }
        return false;
    }
}
