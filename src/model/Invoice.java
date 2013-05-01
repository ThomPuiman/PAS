/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Date;

/**
 *
 * @author Thom
 */
public class Invoice {
    
    private int invoiceNumber;
    private Date invoiceDate;
    private Date invoiceExpire;
    private int lidNr;
    private double invoiceTotal;
    private String lidNaam;
    private String status;
    
    public Invoice(int invoiceNumber, Date invoiceDate, Date invoiceExpire, int lidNr, double invoiceTotal){
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceExpire = invoiceExpire;
        this.lidNr = lidNr;
        this.invoiceTotal = invoiceTotal;
    }
    
    public Invoice(int invoiceNumber, Date invoiceDate, Date invoiceExpire, int lidNr, double invoiceTotal, String lidNaam, String status){
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceExpire = invoiceExpire;
        this.lidNr = lidNr;
        this.invoiceTotal = invoiceTotal;
        this.lidNaam = lidNaam;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLidNaam() {
        return lidNaam;
    }

    public void setLidNaam(String lidNaam) {
        this.lidNaam = lidNaam;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceExpire() {
        return invoiceExpire;
    }

    public void setInvoiceExpire(Date invoiceExpire) {
        this.invoiceExpire = invoiceExpire;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public int getLidNr() {
        return lidNr;
    }

    public void setLidNr(int lidNr) {
        this.lidNr = lidNr;
    }
}
