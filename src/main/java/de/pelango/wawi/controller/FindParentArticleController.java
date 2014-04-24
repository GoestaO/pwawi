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
        this.searchResult = service.findParentArticle(brand, color, material);
    }
    
    public void clear(){
        this.setBrand(null);
        this.setColor(null);
        this.setMaterial(null);
        this.getSearchResult().clear();
    }
    
    private String addChildArticle(ParentArticle pa){
        
        return "";
    }

}
