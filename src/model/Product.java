package model;

import java.util.Date;
import javax.swing.JOptionPane;
import utils.Functions;

public class Product {

    private int productId;
    private int categorieId;
    private String name;
    private String description;
    private double price;
    private int maxDeelnemers;
    private Date startDatum;
    private Date eindDatum;
    private String status;

    public Product(int productId, int categorieId, String name, String description, double price, int maxDeelnemers, Date startDatum, Date eindDatum, String status) {
        this.productId = productId;
        this.categorieId = categorieId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxDeelnemers = maxDeelnemers;
        this.startDatum = startDatum;
        this.eindDatum = eindDatum;
        this.status = status;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public int getMaxDeelnemers() {
        return maxDeelnemers;
    }

    public void setMaxDeelnemers(int maxDeelnemers) {
        this.maxDeelnemers = maxDeelnemers;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.trim();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the categorieId
     */
    public int getCategorieId() {
        return categorieId;
    }

    /**
     * @param categorieId the categorieId to set
     */
    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean validate(){
        String errorMessage = "";
        
        if(this.name == null || this.name.length() == 0)  errorMessage += "\nProductnaam\n";
        if(this.price == -1.00) errorMessage += "Prijs\n";
        
        if (this.categorieId == 4) {
            if(this.maxDeelnemers < 1) errorMessage += "Max. aantal deelnemers\n";
            if(this.startDatum == null)    errorMessage += "Startdatum\n";
            if(this.eindDatum == null) errorMessage += "Einddatum\n";
        }
        
        if(errorMessage.length() > 0){
            JOptionPane.showMessageDialog(null, "De volgende velden zijn niet of incorrect ingevuld: \r" + errorMessage);
            return false;
        } else
            return true;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        boolean value;
        if (obj instanceof Product) {
            value = this.productId == ((Product) obj).productId;
        } else {
            value = super.equals(obj);
        }
        return value;
    }

    @Override
    public int hashCode() {
        return 13 * 3 + this.productId;
    }
}
