/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author goesta
 */
@Entity
@Table(name = "ARTICLE")
@DiscriminatorValue("C")
@XmlRootElement
public class ChildArticle extends ParentArticle implements Serializable {
    
    private static final long serialVersionUID = 1L;

//    @Id
//    private Long id;    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "SIZE_ID")
    private Sizes size;
    
    private BigDecimal purchasePrice;
    
    private BigDecimal amazonPrice;
    
    private BigDecimal ebayPrice;
    
    private BigDecimal shopPrice;
    
    private BigDecimal specialPrice;
    
    private BigDecimal suggestedRetailPrice;
    
    private int quantity;
    
    private Long ean;
    
    private Long asin;
    
    private String manufacturerSKU;
    
    private BigDecimal weight;
    
    private String dimensions;
    
    private String sku;
    
    public String getSKU() {
        return sku;
    }
    
    public void setSKU(String sku) {
        String model = super.getModel();
        String color = "";
        try {
            color = super.getColor().getName();
        } catch (NullPointerException ex) {
            color = "";
        }
        
        if (color.equals("")) {
            this.sku = super.getSku() + "-" + model + "-" + size.getName();
        } else {
            this.sku = super.getSku() + "-" + color + "-" + size.getName();
        }
        
    }
    
    public Sizes getSize() {
        return size;
    }
    
    public void setSize(Sizes size) {
        this.size = size;
    }
    
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setPurchasePrice(BigDecimal price) {
        this.purchasePrice = price;
    }
    
    public BigDecimal getAmazonPrice() {
        return amazonPrice;
    }
    
    public void setAmazonPrice(BigDecimal amazonPrice) {
        this.amazonPrice = amazonPrice;
    }
    
    public BigDecimal getEbayPrice() {
        return ebayPrice;
    }
    
    public void setEbayPrice(BigDecimal ebayPrice) {
        this.ebayPrice = ebayPrice;
    }
    
    public BigDecimal getShopPrice() {
        return shopPrice;
    }
    
    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Long getEan() {
        return ean;
    }
    
    public void setEan(Long ean) {
        this.ean = ean;
    }
    
    public Long getAsin() {
        return asin;
    }
    
    public void setAsin(Long asin) {
        this.asin = asin;
    }
    
    public String getManufacturerSKU() {
        return manufacturerSKU;
    }
    
    public void setManufacturerSKU(String manufacturerSKU) {
        this.manufacturerSKU = manufacturerSKU;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public String getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    
    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }
    
    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }
    
    public BigDecimal getSuggestedRetailPrice() {
        return suggestedRetailPrice;
    }
    
    public void setSuggestedRetailPrice(BigDecimal suggestedRetailPrice) {
        this.suggestedRetailPrice = suggestedRetailPrice;
    }
    
    @Override
    public String toString() {
        return "de.pelango.wawi.model.ChildArticle[ sku=" + this.getSKU() + "]";
    }
    
    public ChildArticle() {
        
    }
    
    public ChildArticle(Sizes size, String sku, String brand, String model, String misc, Color color, Attribute attribute, List<Gender> gender, boolean topProduct, boolean topProductMobile, boolean specialProduct, List<Material> material, List<ProductType> productTypes, Category category, int numberOfPictures, String shortDescription, String longDescription) {
        super(sku, brand, model, misc, color, attribute, gender, topProduct, topProductMobile, specialProduct, material, productTypes, category, numberOfPictures, shortDescription, longDescription);        
        this.size = size;
    }
    
}
