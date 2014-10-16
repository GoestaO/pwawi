/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.ParentArticle;

/**
 *
 * @author Gösta Ostendorf <goesta.o@gmail.com>
 */
public class CSVAnalyser {
    
    public List<ParentArticle> check(){
        
        ChildArticle ca = new ChildArticle();
        return ca;
    }

    /**
     * Enum mit den Attributen, dem zugeordneten Setter, welchen
     * Inputparametertyp der Setter erwartet und die Klasse, in der der Setter
     * aufgerufen wird
     */
    private static enum ColToSave {

        Size("size", "setSize", "Sizes", "ChildArticle"),
        PurchasePrice("purchasePrice", "setPurchasePrice", "BigDecimal", "ChildArticle"),
        AmazonPrice("amazonPrice", "setAmazonPrice", "BigDecimal", "ChildArticle"),
        EbayPrice("ebayPrice", "setEbayPrice", "BigDecimal", "ChildArticle"),
        ShopPrice("shopPrice", "setShopPrice", "BigDecimal", "ChildArticle"),
        Quantity("quantity", "setQuantity", "Integer", "ChildArticle"),
        EAN("ean", "setEan", "Long", "ChildArticle"),
        ASIN("asin", "setAsin", "Long", "ChildArticle"),
        ManufacturerSKU("manufacturerSKU", "setManufacturerSKU", "String", "ChildArticle"),
        Weight("weight", "setWeight", "BigDecimal", "ChildArticle"),
        Dimensions("dimensions", "setDimensions", "BigDecimal", "ChildArticle"),
        SKU("sku", "setSku", "Sring", "ParentArticle"),
        Brand("brand", "setBrand", "Sring", "ParentArticle"),
        Model("model", "setModel", "Sring", "ParentArticle"),
        Misc("misc", "setMisc", "Sring", "ParentArticle"),
        TaxClass("taxClass", "setTaxClass", "Float", "ParentArticle"),
        Color("color", "setColor", "Color", "ParentArticle"),
        ParentArticleName("parentArticleName", "setParentArticleName", "String", "ParentArticle"),
        Attribute("attribute", "setAttribute", "Attribute", "ParentArticle"),
        Gender("gender", "setGender", "Gender", "ParentArticle"),
        TopProduct("topProduct", "setTopProduct", "Boolean", "ParentArticle"),
        TopProductMobile("topProductMobile", "setTopProductMobile", "Boolean", "ParentArticle"),
        SpecialProduct("specialProduct", "setSpecialProduct", "Boolean", "ParentArticle"),
        Material("material", "setMaterial", "Material", "ParentArticle"),
        ProductTypes("productTypes", "setProductTypes", "List<ProductType>", "ParentArticle"),
        Category("category", "setCategory", "Category", "ParentArticle"),
        NumberOfPictures("numberOfPictures", "setNumberOfPictures", "Integer", "ParentArticle"),
        ShortDescription("shortDescription", "setShortDescription", "String", "ParentArticle"),
        LongDescription("longDescription", "setLongDescription", "String", "ParentArticle");

        private final String attribute;
        private final String method;
        private final String inputParameter;
        private final String className;

        private ColToSave(String attribute, String method, String inputParameter, String className) {
            this.attribute = attribute;
            this.method = method;
            this.inputParameter = inputParameter;
            this.className = className;
        }

        public String getAttribute() {
            return this.attribute;
        }

        public String getMethod() {
            return this.method;
        }

        public String getInputParameter() {
            return inputParameter;
        }

        public String getClassName() {
            return className;
        }

    }

}
