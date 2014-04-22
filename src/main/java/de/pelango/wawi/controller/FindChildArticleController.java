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
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@RequestScoped
public class FindChildArticleController implements Serializable {

    @EJB
    private ArticleService service;

    private String brand;

    private Color color;

    private Sizes size;

    private List<ChildArticle> searchResult;

    public ArticleService getService() {
        return service;
    }

    public void setService(ArticleService service) {
        this.service = service;
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
        System.out.println("hersteller = " + this.brand);
        searchResult = service.findChildArticle(this.getBrand(), this.getColor(), this.getSize());
    }

}
