/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@SessionScoped
public class InboundBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<BigInteger> eanList = new ArrayList();
    private BigInteger ean;

    @EJB
    private ArticleService service;

    @Inject
    private FindChildArticleController findChildArticleController;
//    public void init() {
//        eanList = new ArrayList();
//    }

    public List<BigInteger> getEanList() {
        return eanList;
    }

    public void setEanList(List<BigInteger> eanList) {
        this.eanList = eanList;
    }

    public BigInteger getEan() {
        return ean;
    }

    public void setEan(BigInteger ean) {
        this.ean = ean;
    }

    public FindChildArticleController getFindChildArticleController() {
        return findChildArticleController;
    }

    public void setFindChildArticleController(FindChildArticleController findChildArticleController) {
        this.findChildArticleController = findChildArticleController;
    }

    public void addEan(ActionEvent event) {
        if (ean != null) {
            System.out.println("EAN bekannt: " + checkInput(ean));
            eanList.add(ean);
        }
    }

    /**
     * Processes the inboundList: If a childArticle was found having a specific
     * ean, it's quantity will be increased
     */
    public void processInbound() {
        for (BigInteger ean : eanList) {
            try {
                ChildArticle ca = service.findChildArticleByEan(ean);
                ca.setQuantity(ca.getQuantity() + 1);

                System.out.println("Bestand von " + ca.getEan() + " um 1 erhöht");
                service.update(ca);

            } catch (NullPointerException npex) {
                System.out.println(ean + " nicht bekannt");
            }
        }
        eanList.clear();
    }

    public void clearList() {
        eanList.clear();
    }

    public boolean checkInput(BigInteger ean) {
        try {
            ChildArticle ca = service.findChildArticleByEan(ean);
            if (ca != null) {
                return true;
            }
        } catch (NullPointerException npex) {
            return false;
        }
        return false;
    }

    public String doSearch(BigInteger ean) {
        findChildArticleController.setEan(ean);
        return "findChildArticle?faces-redirect=true";
    }

}
