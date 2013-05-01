/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Thom
 */
public class InvoiceRule {
    
    private int productNummer;
    private int aantal;
    private double prijs;
    private String omschrijving;
    
    public InvoiceRule(int productNummer, int aantal, double prijs, String omschrijving){
        this.productNummer = productNummer;
        this.aantal = aantal;
        this.prijs = prijs;
        this.omschrijving = omschrijving;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }
    
}
