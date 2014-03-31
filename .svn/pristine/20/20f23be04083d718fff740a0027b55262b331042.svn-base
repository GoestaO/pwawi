package de.pelango.wawi.controller;

import de.pelango.wawi.model.Attribute;
import de.pelango.wawi.model.Category;
import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Color;
import de.pelango.wawi.model.Gender;
import de.pelango.wawi.model.Material;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.model.ProductType;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.ArticleService;
import de.pelango.wawi.services.SizeService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@RequestScoped
public class ArticleCreateController implements Serializable {

    private String brand;
    private String model;
    private String sku;
    private String misc;
    private Color color;
    private Attribute attribute;
    private List<Gender> gender;
    private boolean topProduct;
    private boolean topProductMobile;
    private boolean specialProduct;
    private List<Material> material;
    private List<ProductType> productType;
    private Category category;
    private int numberOfPictures;
    private String shortDescription;
    private String longDescription;
    private List<Sizes> sizes;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ArticleService service;
    @EJB
    private SizeService sizeService;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public List<Gender> getGender() {
        return gender;
    }

    public void setGender(List<Gender> gender) {
        this.gender = gender;
    }

    public boolean isTopProduct() {
        return topProduct;
    }

    public void setTopProduct(boolean topProduct) {
        this.topProduct = topProduct;
    }

    public boolean isTopProductMobile() {
        return topProductMobile;
    }

    public void setTopProductMobile(boolean topProductMobile) {
        this.topProductMobile = topProductMobile;
    }

    public boolean isSpecialProduct() {
        return specialProduct;
    }

    public void setSpecialProduct(boolean specialProduct) {
        this.specialProduct = specialProduct;
    }

    public List<Material> getMaterial() {
        return material;
    }

    public void setMaterial(List<Material> material) {
        this.material = material;
    }

    public List<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getNumberOfPictures() {
        return numberOfPictures;
    }

    public void setNumberOfPictures(int numberOfPictures) {
        this.numberOfPictures = numberOfPictures;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<Sizes> sizes) {
        this.sizes = sizes;
    }

    public String createArticle(String sku, String brand, String model, String misc, Color color, Attribute attribute, List<Gender> gender, boolean topProduct, boolean topProductMobile, boolean specialProduct, List<Material> material, List<ProductType> productType, Category category, int numberOfPictures, String shortDescription, String longDescription, List<Sizes> size) {
        ParentArticle pa = new ParentArticle(sku, brand, model, misc, color, attribute, gender, topProduct, topProductMobile, specialProduct, material, productType, category, numberOfPictures, shortDescription, longDescription);

        for (Sizes s : sizes) {
            ChildArticle ca = new ChildArticle(s, sku, brand, model, misc, color, attribute, gender, topProduct, topProductMobile, specialProduct, material, productType, category, numberOfPictures, shortDescription, longDescription);
            service.create(ca);
        }
        
        service.create(pa);

        return "parentArticleOverview?faces-redirect=true";
    }

}
