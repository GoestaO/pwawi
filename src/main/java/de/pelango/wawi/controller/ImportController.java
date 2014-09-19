/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.services.ArticleService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;

/**
 * Diese Datei ist der Controller für die Upload-Seite und sorgt dafür, dass
 * eine Backlog-Datei in die DB eingespielt wird.
 *
 * @author Gösta Ostendorf (goesta.o@gmail.com)
 */
@Named
@ViewScoped
public class ImportController implements Serializable {

    List<String[]> list;
    long ean;

    @EJB
    private ArticleService service;

    private String[] columns;

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    /**
     * Diese Methode sorgt dafür, dass eine gewählte Datei auf den Server
     * geladen wird. Hierzu wird zunächst die hochgeladene CSV geparst und aus
     * den entsprechenden Spalten ein BacklogArticle-Objekt erzeugt wird. Diese
     * Objekte werden dann in einer Liste gespeichert. Anschließend wird die
     * Objekte in der Liste mit der Datenbank abgeglichen, ob sie schon
     * vorhanden sind oder nicht. Noch nicht vorhandene Objekte werden dann in
     * der Datenbank abgespeichert.
     *
     * @param event Das FileUpload-Event
     */
    public void handleFileUpload(FileUploadEvent event) {

        Map<String, String> columnMap = new HashMap();

        list = new ArrayList();

        Field[] fieldsParentArticle;
        Field[] fieldsChildArticle;

        // Die hochzuladende Datei auf den Server laden und in backlog.csv abspeichern
        try {
            File targetFile = new File("import.csv");
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
            FileReader reader = new FileReader(targetFile);
            BufferedReader br = new BufferedReader(reader);
            if (this.columns == null) {
                columns = br.readLine().split("\t", -1);
                System.out.println(Arrays.toString(columns));
            } else {

                br.readLine();

                String line = br.readLine();
                while (line != null) {
                    String[] data = line.split("\t", -1);
                    list.add(data);
                    line = br.readLine();
                }
                br.close();
            }
            // Die Liste an den DB-Handler übergeben, der jedes Objekt auf Vorhandensein überprüft und eventuell abspeichert
        } catch (IOException e) {
//            Logger.getLogger(ImportController.class.getClass()).log(Logger.Level.FATAL, e);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Upload fehlgeschlagen", e.getMessage());
            FacesContext.getCurrentInstance()
                    .addMessage(null, message);
        }

        FacesMessage message = new FacesMessage("Upload", list.size() + " Artikel erfolgreich hochgeladen.");
        FacesContext.getCurrentInstance()
                .addMessage(null, message);
        for (String[] s : list) {
//            System.out.print("0: "+ s[0]);
//            System.out.print("1: "+ s[1]);
//            System.out.print("2: "+ s[2]);
//            System.out.print("3: "+ s[3]);
//            System.out.println("4: "+ s[4]);
            int count = s[4].length() - s[4].replace("-", "").length();
            System.out.println("count = " + count);
//            System.out.println(Arrays.toString(s));
        }
    }

}