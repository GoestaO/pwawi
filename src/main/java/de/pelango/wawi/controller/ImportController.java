package de.pelango.wawi.controller;

import de.pelango.wawi.services.ArticleService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

import org.primefaces.event.FileUploadEvent;

/**
 * Diese Datei ist der Controller für die Upload-Seite und sorgt dafür, dass
 * eine Backlog-Datei in die DB eingespielt wird.
 *
 * @author Gösta Ostendorf (goesta.o@gmail.com)
 */
@Named
@SessionScoped
public class ImportController implements Serializable {

    List<String[]> list;
    long ean;

    private String[] columnHeaders;

    Map<String, String> columnMap;

    private List<String> fieldList;

    private File targetFile;

    @EJB
    private ArticleService service;

    @PostConstruct
    public void init() {
        this.loadFields();
    }

    public String[] getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(String[] columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFields(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public Map<String, String> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, String> columnMap) {
        this.columnMap = columnMap;
    }

    public void loadFields() {
        fieldList = service.getFields();
    }

//    public void upload(FileUploadEvent event) {
//        try {
//            targetFile = new File("import.csv");
//            
//            InputStream inputStream = event.getFile().getInputstream();
//            OutputStream out = new FileOutputStream(new File(targetFile.getName()));
//            int read = 0;
//            byte[] bytes = new byte[1024];
//            while ((read = inputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            inputStream.close();
//            out.flush();
//            out.close();
//        } catch (FileNotFoundException f) {
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Datei nicht gefunden", f.getMessage());
//            FacesContext.getCurrentInstance()
//                    .addMessage(null, message);
//        } catch (IOException io) {
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ein- und Ausgabefehler", io.getMessage());
//            FacesContext.getCurrentInstance()
//                    .addMessage(null, message);
//        }
//
//        FacesMessage message = new FacesMessage("Import erfolgreich", "Spaltenköpfe importiert");
//        FacesContext.getCurrentInstance()
//                .addMessage(null, message);
//    }
    public void handleColumnImport(FileUploadEvent event) {

        columnMap = new LinkedHashMap<>();
//        list = new ArrayList<>();
        // Die hochzuladende Datei auf den Server laden und in import.csv abspeichern
        try {
            targetFile = new File("import.csv");
            InputStream inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFile.getName()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();

            // Anschließend die gerade hochgeladene Datei einlesen und parsen
//            File targetFile = new File(event.getFile().getFileName());
            FileReader reader = new FileReader(targetFile);
            BufferedReader br = new BufferedReader(reader);
            columnHeaders = br.readLine().split("\t", -1);
            for (String entry : columnHeaders) {
                columnMap.put(entry, null);
            }
            RequestContext.getCurrentInstance().update("headers");
            br.close();

        } catch (FileNotFoundException f) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Datei nicht gefunden", f.getMessage());
            FacesContext.getCurrentInstance()
                    .addMessage(null, message);
        } catch (IOException io) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ein- und Ausgabefehler", io.getMessage());
            FacesContext.getCurrentInstance()
                    .addMessage(null, message);
        }

        FacesMessage message = new FacesMessage("Import erfolgreich", "Spaltenköpfe importiert");
        FacesContext.getCurrentInstance()
                .addMessage(null, message);
    }

    public void importArticles() {
        if (columnMap != null) {
            try {
//                File targetFile = new File("/home/goesta/glassfish-4.0/glassfish/domains/domain1/config/import.csv");
                FileReader reader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(reader);
                br.readLine();
                String line = br.readLine();
                while (line != null) {
                    String[] data = line.split("\t", -1);
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
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Keine Spaltenzuweisung.", "Bitte zunächst eine Spaltenzuweisung durchführen.");
            FacesContext.getCurrentInstance()
                    .addMessage(null, message);
        }
    }

    public void test() {
        columnMap.keySet();
        Iterator<String> iterator = columnMap.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

//    static enum ColToSave {
//
//        Size("size", "setSize", "Sizes", "ChildArticle"), PurchasePrice("purchasePrice", "setPurchasePrice", "BigDecimal", "ChildArticle"), AmazonPrice("amazonPrice", "setAmazonPrice", "BigDecimal", "ChildArticle"), EbayPrice("ebayPrice", "setEbayPrice", "BigDecimal", "ChildArticle"), ShopPrice("shopPrice", "setShopPrice", "BigDecimal", "ChildArticle"), Quantity("quantity", "setQuantity", "Integer", "ChildArticle"), EAN("ean", "setEan", "Long", "ChildArticle"), ASIN("asin", "setAsin", "Long", "ChildArticle"), ManufacturerSKU("manufacturerSKU", "setManufacturerSKU", "String", "ChildArticle"), Weight("weight", "setWeight"), Dimensions("dimensions", "setDimensions", "BigDecimal", "ChildArticle"), SKU("sku", "setSku", "Sring", "ParentArticle"), Brand("brand", "setBrand","Sring", "ParentArticle"), Model("model", "setModel", "Sring", "ParentArticle"), Misc("misc", "setMisc", "Sring", "ParentArticle"), TaxClass("taxClass", "setTaxClass", "Float", "ParentArticle"), Color("color", "setColor", "Color", "ParentArticle"), ParentArticleName("parentArticleName", "setParentArticleName", "String", "ParentArticle"), Attribute("attribute", "setAttribute", "Attribute", "ParentArticle"), Gender("gender", "setGender", "Gender", "ParentArticle"), TopProduct("topProduct", "setTopProduct", "Boolean", "ParentArticle"), TopProductMobile("topProductMobile", "setTopProductMobile", "Boolean", "ParentArticle"), SpecialProduct("specialProduct", "setSpecialProduct","Boolean", "ParentArticle"), Material("material", "setMaterial", "Material", "ParentArticle"), ProductTypes("productTypes", "setProductTypes", "List<ProductType>"), Category("category", "setCategory", "Category", "ParentArticle"), NumberOfPictures("numberOfPictures", "setNumberOfPictures", "Integer", "ParentArticle"), ShortDescription("shortDescription", "setShortDescription", "String", "ParentArticle"), LongDescription("longDescription", "setLongDescription", "String", "ParentArticle");
//        
//        private final String attribute;
//        private final String method;
//        private final String inputParameter;
//        private final String class;
//
//        private ColToSave(String attribute, String method, String inputParameter, String class) {
//            this.attribute = attribute;
//            this.method = method;
//            this.inputParameter = inputParameter;
//            this.class = class;
//        }
//        
//        public String getAttribute(){
//            return this.attribute;
//        }
//        
//        public String getMethod(){
//            return this.method;
//        }
//    }
}
//        for (String[] s : list) {
////            System.out.print("0: "+ s[0]);
////            System.out.print("1: "+ s[1]);
////            System.out.print("2: "+ s[2]);
////            System.out.print("3: "+ s[3]);
////            System.out.println("4: "+ s[4]);
//            int count = s[4].length() - s[4].replace("-", "").length();
////            System.out.println(Arrays.toString(ChildArticle.class.getFields()));
////            System.out.println(Arrays.toString(s));
////        }
//}
