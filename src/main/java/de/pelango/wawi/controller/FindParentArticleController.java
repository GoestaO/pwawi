/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.Color;
import de.pelango.wawi.model.Material;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
public class FindParentArticleController implements Serializable {

    @EJB
    private ArticleService service;

    @Inject
    private ParentArticleEditController parentArticleEditController;

    private BigInteger ean;

    private String brand;

    private Color color;

    private Material material;

    private List<ParentArticle> searchResult;

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<ParentArticle> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<ParentArticle> searchResult) {
        this.searchResult = searchResult;
    }

    public void findParentArticle(ActionEvent event) {
        this.searchResult = service.findParentArticle(this.getBrand(), this.getColor(), this.getMaterial());
    }

    public void listener(AjaxBehaviorEvent event) {
        this.searchResult = service.findParentArticle(this.getBrand(), this.getColor(), this.getMaterial());
    }

    public void clearBrand() {
        this.setBrand(null);
        this.searchResult = service.findParentArticle(this.getBrand(), this.getColor(), this.getMaterial());

    }

    public void clearMaterial() {
        this.setMaterial(null);
        this.searchResult = service.findParentArticle(this.getBrand(), this.getColor(), this.getMaterial());
    }

    public void clearColor() {
        this.setColor(null);
        this.searchResult = service.findParentArticle(this.getBrand(), this.getColor(), this.getMaterial());
    }

    public void reset() {
        this.setBrand(null);
        this.setMaterial(null);
        this.setColor(null);
        this.getSearchResult().clear();
    }

    public String addChildArticle(ParentArticle pa) {        
//        parentArticleEditController.start();
        parentArticleEditController.setPa(pa);
        parentArticleEditController.setEan(this.getEan());
        return "parentArticleEdit?faces-redirect=true";
    }

}
