/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.ParentArticle;
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
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Gösta Ostendorf <goesta.o@gmail.com>
 */
public class CSVAnalyser {

    @EJB
    private SizeService sizeService;

    @EJB
    private AttributeService attributeService;

    public List<ParentArticle> getData(File file, Map<String, String> columnMap) {
        List<ParentArticle> list = new ArrayList();
        //Bedingung: SKU ist Pflichtangabe, muss also einmal ausgewählt sein
        if (columnMap.containsValue("sku")) {

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
                        ca = eachCell(data, columnMap, ca);

                        if (isValidSKU(ca.getSku())) {

                            // Wenn die SKU darauf hinweist, dass es sich um einen ParentArticle handelt, 
                            // die Child-Attribute auf ein neu erzeugtes Parent-Objekt übertragen
                            if (isParentArticle(ca)) {
                                ParentArticle pa = new ParentArticle();
                                try {
                                    BeanUtils.copyProperties(pa, ca);
                                } catch (IllegalAccessException | InvocationTargetException ex) {
                                    Logger.getLogger(CSVAnalyser.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                list.add(pa);
                            } else {
                                list.add(ca);
                            }
                        }
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

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Import nicht möglich", "Eine Spalte muss die SKU enthalten.");
            FacesContext.getCurrentInstance()
                    .addMessage(null, message);
        }
        return list;
    }

    /**
     * Durchläuft jede Zelle einer Reihe, sucht den Spaltennamen aus dem
     * Spaltenarray anhand des Spaltenindexes heraus, zieht das Attribut aus der
     * ColumnMap und übergibt alles die Methode check.
     *
     * @param data Das Array mit den Daten
     * @param columnMap Die Hashmap mit den Attributen, die zu jeder Spalte
     * hinterlegt sind
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
     * Zieht die Setter-Methode anhand des Attributs aus dem Enum Zieht den
     * Parametertyp, der an den Setter übergeben wird, aus dem Enum Zieht den
     * Klassennamen, in der das Attribut angelegt ist, aus dem Enum Übergibt
     * alles an die Methode save
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
     * @param methodName Der Name der Settermethode
     * @param parameterType Der Parametertyp, der an den Setter übergeben wird
     * @param entry Der Wert, der in der Zelle steht
     * @param className Der Klassenname, in der das Attribut/Settermethode
     * implementiert ist
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
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fehler beim Parsen", "Kann nicht den String in Dezimalformat umwandeln."));
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
            // Methoden-Objekt erzeugen
            Method method;
            try {
                // Methode aus ChildArticle 
                method = pt.getClass().getDeclaredMethod(methodName, paramTypes);
            } catch (NoSuchMethodException nsm) {
                // Methode aus ParentArticle
                method = pt.getClass().getSuperclass().getDeclaredMethod(methodName, paramTypes);
            }
            method.invoke(pt, new Object[]{o});
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Parsefehler", ex.getMessage()));
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

    // Validierungsmethoden
    /**
     * Check, ob ein Artikel ein ParentArticle ist (hat nämlich nur ein "-")
     *
     * @param ca
     * @return
     */
    private boolean isParentArticle(ParentArticle ca) {
        String sku = ca.getSku();

        // Zählen, wie oft "-" in der SKU vorkommt: wenn einmal, dann ist es ein ParentArticle, 
        // ansonsten ein ChildArticle
        int count = sku.length() - sku.replace("-", "").length();
        return count == 1;
    }

    /**
     * Check, ob eine SKU ein gewisse Länge aufweist sowie, ob ein "-" vorkommt
     *
     * @param sku
     * @return
     */
    private boolean isValidSKU(String sku) {
        return sku.length() > 6 && sku.contains("-");
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
        SKU("sku", "setSku", "String", "ParentArticle"),
        Brand("brand", "setBrand", "String", "ParentArticle"),
        Model("model", "setModel", "String", "ParentArticle"),
        Misc("misc", "setMisc", "String", "ParentArticle"),
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
        LongDescription("longDescription", "setLongDescription", "String", "ParentArticle"),
        SpecialPrice("specialPrice", "setSpecialPrice", "BigDecimal", "ChildArticle"),
        SuggestedRetailPrice("suggestedRetailPrice", "setSuggestedRetailPrice", "BigDecimal", "ChildArticle");

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
