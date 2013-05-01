/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Egbert Hulsman
 */
public class Member {

    private int memberId;


    private String firstname;
    private String lastname;
    private String gender;
    private Date birthday;
    private String tussenvoeg;
    private String straat;
    private int huisnr;
    private String huisnrtoevoeg;
    private String postcode;
    private String woonplaats;
    private String telefoondag;
    private String telefoonnacht;
    private String reknr;
    private String status;
    private String foto;
    private Date uitschrijf;
    private Date inschrijf;
    private int medewerkersnr;
    private String vestigingscode;
    private String emailAddress;
    private int abonnement;
       
    public Member ( int memberId , String firstname, String lastname, String gender, Date birthday, String tussenvoeg, String straat, int huisnr
            , String huisnrtoevoeg, String postcode, String woonplaats, String telefoondag, String telefoonnacht, String reknr, String status
            , String foto, Date uitschrijf, Date inschrijf, int medewerkersnr, String vestigingscode, String emailAddress, int abonnement){
        this.memberId = memberId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthday = birthday;
        this.tussenvoeg = tussenvoeg;
        this.straat = straat;
        this.huisnr = huisnr;
        this.huisnrtoevoeg = huisnrtoevoeg;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.telefoondag = telefoondag;
        this.telefoonnacht = telefoonnacht;
        this.reknr = reknr;
        this.status = status;
        this.foto = foto;
        this.uitschrijf = uitschrijf;
        this.inschrijf = inschrijf;
        this.medewerkersnr = medewerkersnr;
        this.vestigingscode = vestigingscode;
        this.emailAddress = emailAddress;
        this.abonnement = abonnement;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(int abonnement) {
        this.abonnement = abonnement;
    }

    public String getTussenvoeg() {
        return tussenvoeg;
    }

    public void setTussenvoeg(String tussenvoeg) {
        this.tussenvoeg = tussenvoeg;
    }

    public int getHuisnr() {
        return huisnr;
    }

    public void setHuisnr(int huisnr) {
        this.huisnr = huisnr;
    }

    public String getHuisnrtoevoeg() {
        return huisnrtoevoeg;
    }

    public void setHuisnrtoevoeg(String huisnrtoevoeg) {
        this.huisnrtoevoeg = huisnrtoevoeg;
    }

    public Date getInschrijf() {
        return inschrijf;
    }

    public void setInschrijf(Date inschrijf) {
        this.inschrijf = inschrijf;
    }

    public int getMedewerkersnr() {
        return medewerkersnr;
    }

    public void setMedewerkersnr(int medewerkersnr) {
        this.medewerkersnr = medewerkersnr;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getReknr() {
        return reknr;
    }

    public void setReknr(String reknr) {
        this.reknr = reknr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getTelefoondag() {
        return telefoondag;
    }

    public void setTelefoondag(String telefoondag) {
        this.telefoondag = telefoondag;
    }

    public String getTelefoonnacht() {
        return telefoonnacht;
    }

    public void setTelefoonnacht(String telefoonnacht) {
        this.telefoonnacht = telefoonnacht;
    }

    public Date getUitschrijf() {
        return uitschrijf;
    }

    public void setUitschrijf(Date uitschrijf) {
        this.uitschrijf = uitschrijf;
    }

    public String getVestigingscode() {
        return vestigingscode;
    }

    public void setVestigingscode(String vestigingscode) {
        this.vestigingscode = vestigingscode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getMemberId() {
        return memberId;

    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public String getTussenVoeg() {
        return tussenvoeg;

    }

    public void setTussenVoeg(String tussenvoeg) {
        this.tussenvoeg = tussenvoeg;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public boolean validate(){
        String errorMessage = "";
        
        if(this.firstname == null || this.firstname.length() == 0)  errorMessage += "\nVoornaam\n";
        if(this.lastname == null || this.lastname.length() == 0)    errorMessage += "Achternaam\n";
        if(this.birthday == null) errorMessage += "Geboortedatum\n";
        if(this.straat == null || this.straat.length() == 0)    errorMessage += "Adres\n";
        if(this.huisnr < 1)    errorMessage += "Huisnummer\n";
        if(this.postcode == null || this.postcode.length() == 0)    errorMessage += "Postcode\n";
        if(this.woonplaats == null || this.woonplaats.length() == 0)    errorMessage += "Woonplaats\n";
        if((this.telefoondag == null || this.telefoondag.length() == 0) || (this.telefoonnacht == null || this.telefoonnacht.length() == 0))    errorMessage += "Telefoonnummer\n";
        if(this.reknr == null || this.reknr.length() == 0)    errorMessage += "Rekeningnummer\n";
        if(this.abonnement == -1) errorMessage += "Abonnement\n";
        
        if(errorMessage.length() > 0){
            JOptionPane.showMessageDialog(null, "De volgende velden zijn niet of incorrect ingevuld: \r" + errorMessage);
            return false;
        }else
            return true;
    }
}
