/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.AttributeService;
import de.pelango.wawi.services.SizeService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Gösta Ostendorf <goesta.o@gmail.com>
 */
public class CSVAnalyser {
    
    @EJB
    private SizeService sizeService;
    
    @EJB
    private AttributeService attributeService;

//    private 
    //Amazon Price <price_amazon> (admin)	 BigDecimal
    //Ebay Price <price_ebay> (admin)	BigDecimal
    //Manufacturer's Suggested Retail Price <msrp> (admin)	 BigDecimal
    //Price <price> (admin) BigDecimal	
    //SKU <sku>	String
    //Special Price <special_price> (admin) BigDecimal
    public List<ChildArticle> getData(File file, Map<String, String> columnMap) {
        List<ChildArticle> list = new ArrayList();
        // Bedingung: Muss csv sein
        if (file.getName().endsWith(".csv")) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] data = line.split("\t", -1);
                    ChildArticle ca = new ChildArticle();
                    ca.setShortDescription("test123");
                    System.out.println(ca.getShortDescription());
                    ca = eachCell(data, columnMap, ca);
                    list.add(ca);
                    line = br.readLine();
                }
                
            } catch (FileNotFoundException fne) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Keine Datei nicht gefunden. Bitte erst eine Datei hochladen.", fne.getMessage());
                FacesContext.getCurrentInstance()
                        .addMessage(null, message);
            } catch (IOException ie) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ein- und Ausgabefehler.", ie.getMessage());
                FacesContext.getCurrentInstance()
                        .addMessage(null, message);
            }
        }
        return list;
    }

    /**
     * Durchläuft jede Zelle einer Reihe
     *
     * @param data
     * @param columnMap
     * @return
     */
    private ChildArticle eachCell(String[] data, Map<String, String> columnMap, ChildArticle ca) {
        
        ChildArticle pt = ca;
        int columnIndex = 0;        
        Object[] columns = columnMap.keySet().toArray();
        for (String entry : data) {
            String columnName = columns[columnIndex].toString();
            String attribute = columnMap.get(columnName);
            pt = check(attribute, entry, pt);
            columnIndex++;
        }
        return pt;
        
    }

    /**
     * Überprüft jeden Eintrag in der Zelle -> liest das
     *
     * @param attribute
     * @param entry
     * @param pa
     * @return
     */
    private ChildArticle check(String attribute, String entry, ChildArticle pa) {
        ChildArticle pt = pa;

        // Die Setter-Methode zu dem Attribut aus dem Enum ziehen 
        String method = ColToSave.getEnum(attribute).getMethod();
        // Den Parametertyp aus dem Enum ziehen
        String parameterType = ColToSave.getEnum(attribute).getInputParameter();
        // Den Klassentyp, auf dem dieses Attribut implementiert ist, aus dem Enum ziehen
        String className = ColToSave.getEnum(attribute).getClassName();
        pt = save(method, parameterType, entry, className, pt);
        return pt;
        
    }

    /**
     * Ruft die Settermethoden der Klasse auf.
     *
     * @param methodName
     * @param parameterType
     * @param entry
     * @param className
     * @param pa
     * @return
     */
    private ChildArticle save(String methodName, String parameterType, String entry, String className, ChildArticle pa) {
        ChildArticle pt = pa;

        //Check, ob es sich beim Artikel um einen ParentArtilce handelt: Die SKU hat n Stellen
//        if (className.equals("ChildArticle")) {
//            pt = (ChildArticle) pt;
////            ca = pt;
////            pt = (ChildArticle)pt;
//            System.out.println("pt = " + pt.getShortDescription());
////            pt = (ChildArticle) pt;
//        }
        //ParentArticle zu ChildArticle casten 
        // Ein Inputobjekt erzeugen
        Object o = new Object();
        Class[] paramTypes = new Class[1];

        // Jetzt die Paramtertyp-Varianten durchgehen und paramTypes[0] entsprechend setzen
        switch (parameterType) {
            // Wenn es eine Größe ist
            case "Sizes":
                Sizes size = new Sizes();
                try {
                    Long id = sizeService.getSizeId(entry);
                    size.setId(id);
                    size.setName(entry);
                    // Das Inputobjekt in ein "Sizes"-Objekt konvertieren
                    o = size;
//                    System.out.println("o = " + o);
                } catch (NullPointerException ex) {
                    o = null;
                }
                paramTypes[0] = Sizes.class;
                break;

            // Wenn es BigDecimal ist, dorthin konvertieren
            case "BigDecimal":
                try {
                    o = convertString2BigDecimal(entry);
                } catch (ParseException ex) {
                    o = new BigDecimal(0);
                    Logger.getLogger(CSVAnalyser.class.getName()).log(Level.SEVERE, null, ex);
                }
                paramTypes[0] = BigDecimal.class;
                break;

            // TODO: DecimalFormat setzen
            // TODO: Wenn setSKU die Methode ist, muss eine weitere Untersuchung der SKU erfolgen
            case "String":
                paramTypes[0] = String.class;
                o = entry;
                break;
        }
        
        try {
//            System.out.println("entry = " + entry + "; parameter = " + o.getClass() + "; parameterType = " + parameterType);

            // Methoden-Objekt erzeugen
            Method method = pt.getClass().getDeclaredMethod(methodName, paramTypes);
//            System.out.println("method = " + method.getName());

            // Setter-Methode per Reflektion ausführen
            method.invoke(pt, new Object[]{o});
        } catch (NoSuchMethodException nsm) {
            Logger.getLogger(CSVAnalyser.class.getName()).log(Level.SEVERE, null, nsm);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CSVAnalyser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CSVAnalyser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CSVAnalyser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pt;
    }

    /**
     * Konvertiert einen String in eine Zahl mit BigDecimal-Format um
     *
     * @param entry
     * @return
     * @throws ParseException
     */
    private BigDecimal convertString2BigDecimal(String entry) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        String pattern = "#,00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        if (!entry.isEmpty()) {
            BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(entry);
            return bigDecimal;
        }
        BigDecimal bigDecimal0 = new BigDecimal(0);
        return bigDecimal0;
    }

    /**
     * Enum mit den Attributen, dem zugeordneten Setter, welchen
     * Inputcarametertyp der Setter erwartet und die Klasse, in der der Setter
     * aufgerufen wird
     */
    static enum ColToSave {
        
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
        private final String parameterType;
        private final String className;
        
        private ColToSave(String attribute, String method, String parameterType, String className) {
            this.attribute = attribute;
            this.method = method;
            this.parameterType = parameterType;
            this.className = className;
        }
        
        public String getAttribute() {
            return this.attribute;
        }
        
        public String getMethod() {
            return this.method;
        }
        
        public String getInputParameter() {
            return parameterType;
        }
        
        public String getClassName() {
            return className;
        }
        
        public static ColToSave getEnum(String value) {
            for (ColToSave c : ColToSave.values()) {
                if (c.getAttribute().equalsIgnoreCase(value)) {
                    return c;
                }
            }
            throw new IllegalArgumentException();
        }
        
    }
    
}
