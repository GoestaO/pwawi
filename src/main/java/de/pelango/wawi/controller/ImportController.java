/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        System.out.println(columnMap.keySet());
    }
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
