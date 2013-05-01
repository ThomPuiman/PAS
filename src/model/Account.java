/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Vincent S
 */
public class Account {

    private int medewerkers_nr;
    private String medew_voornaam;
    private String medew_tussenvoegsel;
    private String medew_achternaam;
    private String medew_wachtwoord;
    private String medew_geslacht;
    private String vestigingscode;
    private int rolcode;
    private String medew_status;

    public Account(int medewerkers_nr, String medew_voornaam, String medew_tussenvoegsel, String medew_achternaam, String medew_wachtwoord, String medew_geslacht, String vestigingscode, int rolcode, String medew_status) {
        this.medewerkers_nr = medewerkers_nr;
        this.medew_voornaam = medew_voornaam;
        this.medew_tussenvoegsel = medew_tussenvoegsel;
        this.medew_achternaam = medew_achternaam;
        this.medew_wachtwoord = medew_wachtwoord;
        this.medew_geslacht = medew_geslacht;
        this.rolcode = rolcode;
        this.vestigingscode = vestigingscode;
        this.medew_status = medew_status;
    }

    public Account(int aInt, String trim, String trim0, String string, String string0, String string1, Date date, Date date0, int aInt0, String string2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int getmedewerkers_nr() {
        return medewerkers_nr;
    }

    public void setmedewerkers_nr(int medewerkers_nr) {
        this.medewerkers_nr = medewerkers_nr;
    }

    public String getmedew_voornaam() {
        return medew_voornaam;
    }

    public void medew_voornaam(String medew_voornaam) {
        this.medew_voornaam = medew_voornaam;
    }

    public String getmedew_tussenvoegsel() {
        return medew_tussenvoegsel;
    }

    public void medew_tussenvoegsel(String medew_tussenvoegsel) {
        this.medew_tussenvoegsel = medew_tussenvoegsel;
    }

    public String getmedew_achternaam() {
        return medew_achternaam;
    }

    public void medew_achternaam(String medew_achternaam) {
        this.medew_achternaam = medew_achternaam;
    }

    public String getMedew_wachtwoord() {
        return medew_wachtwoord;
    }

    public void setMedew_wachtwoord(String medew_wachtwoord) {
        this.medew_wachtwoord = medew_wachtwoord;
    }

    public String getMedew_geslacht() {
        return medew_geslacht;
    }

    public void setMedew_geslacht(String medew_geslacht) {
        this.medew_geslacht = medew_geslacht;
    }

    public String getMedew_status() {
        return medew_status;
    }

    public void setMedew_status(String medew_status) {
        this.medew_status = medew_status;
    }

    public int getRolcode() {
        return rolcode;
    }

    public void setRolcode(int rolcode) {
        this.rolcode = rolcode;
    }

    public String getVestigingscode() {
        return vestigingscode;
    }

    public void setVestigingscode(String vestigingscode) {
        this.vestigingscode = vestigingscode;
    }
    
    public boolean validate(){
        String errorMessage = "";
        
        if(this.medew_voornaam == null || this.medew_voornaam.length() == 0)  errorMessage += "\nVoornaam\n";
        if(this.medew_achternaam == null || this.medew_achternaam.length() == 0)  errorMessage += "Achternaam\n";
        if(this.medew_wachtwoord == null || this.medew_wachtwoord.length() == 0)  errorMessage += "Wachtwoord\n";
        
        if(errorMessage.length() > 0){
            JOptionPane.showMessageDialog(null, "De volgende velden zijn niet of incorrect ingevuld: \r" + errorMessage);
            return false;
        } else
            return true;
    }
}

