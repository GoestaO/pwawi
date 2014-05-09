/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Color;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@SessionScoped
public class FindChildArticleController implements Serializable {

    @EJB
    private ArticleService service;

    @Inject
    private FindParentArticleController findParentArticleController;

    private ChildArticle ca;

    private BigInteger ean;

    private String brand;

    private Color color;

    private Sizes size;

    private List<ChildArticle> searchResult;

    public ChildArticle getCa() {
        return ca;
    }

    public void setCa(ChildArticle ca) {
        this.ca = ca;
    }

    public BigInteger getEan() {
        return ean;
    }

    public void setEan(BigInteger ean) {
        this.ean = ean;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Sizes getSize() {
        return size;
    }

    public void setSize(Sizes size) {
        this.size = size;
    }

    public List<ChildArticle> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<ChildArticle> searchResult) {
        this.searchResult = searchResult;
    }

    public void findChildArticle(ActionEvent event) {
        this.searchResult = service.findChildArticle(this.getBrand(), this.getColor(), this.getSize());
    }

    public String doUpdateArticle(ChildArticle ca, BigInteger ean) {
        if (ean != null) {
            ca.setEan(ean.longValue());
            this.setEan(null);
            service.update(ca);
        }
        return "inbound?faces-redirect=true";
    }

    public void listener(AjaxBehaviorEvent event) {

        System.out.println("Größe: " + this.getSize());
        System.out.println("Marke: " + this.getBrand());
        System.out.println("Farbe: " + this.getColor());
        this.searchResult = service.findChildArticle(this.getBrand(), this.getColor(), this.getSize());
//        System.out.println(this.getSearchResult());
    }

    public void clearBrand() {
        if (!this.getBrand().isEmpty()) {
            this.setBrand("");
            this.searchResult = service.findChildArticle(this.getBrand(), this.getColor(), this.getSize());
        }

    }

    public void clearColor() {
        if (this.getColor() != null) {
            this.setColor(null);
            this.searchResult = service.findChildArticle(this.getBrand(), this.getColor(), this.getSize());
        }

    }

    public void clearSize() {
        if (this.getSize() != null) {
            this.setSize(null);
            this.searchResult = service.findChildArticle(this.getBrand(), this.getColor(), this.getSize());
        }
    }

    public void reset() {
        this.setBrand("");
        this.setColor(null);
        this.setSize(null);
        this.getSearchResult().clear();
    }

    public String findParent() {
        findParentArticleController.setEan(this.getEan());
        return "findParentArticle?faces-redirect=true";
    }

}
