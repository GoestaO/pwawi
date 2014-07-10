package de.pelango.wawi.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ParentArticle
 *
 */
@Entity
@Table(name = "ARTICLE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING, length = 20)
@DiscriminatorValue("P")

public class ParentArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private BigInteger skuPrefix;

    private String sku;

    private String brand;

    private String model;

    private String misc;

    private float taxClass;

    @OneToOne
    private Color color;

    private String parentArticleName;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ATTRIBUTE_ID")
    private Attribute attribute;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "ARTICLE_GENDER",
            joinColumns = @JoinColumn(name = "ParentArticle_ID"),
            inverseJoinColumns = @JoinColumn(name = "gender_ID"))
    private List<Gender> gender;

    private boolean topProduct;

    private boolean topProductMobile;

    private boolean specialProduct;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "ARTICLE_MATERIAL",
            joinColumns = @JoinColumn(name = "ParentArticle_ID"),
            inverseJoinColumns = @JoinColumn(name = "materials_ID"))
    private List<Material> material;

    @OneToMany
    private List<ProductType> productTypes;

    @OneToOne
    private Category category;

    private int numberOfPictures;

    private String shortDescription;

    private String longDescription;

    public ParentArticle(String sku, String brand, String model, String misc, Color color, Attribute attribute, List<Gender> gender, boolean topProduct, boolean topProductMobile, boolean specialProduct, List<Material> material, List<ProductType> productTypes, Category category, int numberOfPictures, String shortDescription, String longDescription) {
        this.sku = sku;
        this.brand = brand;
        this.model = model;
        this.misc = misc;
        this.color = color;
        this.parentArticleName = brand + model + "-" + misc + "-" + color.getName();
        this.attribute = attribute;
        this.gender = gender;
        this.topProduct = topProduct;
        this.topProductMobile = topProductMobile;
        this.specialProduct = specialProduct;
        this.material = material;
        this.productTypes = productTypes;
        this.category = category;
        this.numberOfPictures = numberOfPictures;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public ParentArticle() {
        super();
    }

    public BigInteger getSkuPrefix() {
        return skuPrefix;
    }

    public void setSkuPrefix(BigInteger skuPrefix) {
        this.skuPrefix = skuPrefix;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

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

    public String getParentArticleName() {
        return parentArticleName;
    }

    public void setParentArticleName(String brand, String model, String misc, Color color) {
        this.parentArticleName = brand + model + "-" + misc + "-" + color.getName();
    }

    public void setParentArticleName(String parentArticleName) {
        this.parentArticleName = parentArticleName;
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

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
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

    public float getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(float taxClass) {
        this.taxClass = taxClass;
    }
}
