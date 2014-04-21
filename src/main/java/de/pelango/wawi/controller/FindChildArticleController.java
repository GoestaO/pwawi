/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@ViewScoped
public class FindChildArticleController implements Serializable {

    @EJB
    private ArticleService service;

    private ChildArticle childArticle;

    private List<ChildArticle> result;

    public ChildArticle getChildArticle() {
        return childArticle;
    }

    public void setChildArticle(ChildArticle childArticle) {
        this.childArticle = childArticle;
    }

    public List<ChildArticle> getResult() {
        return result;
    }

    public void setResult(List<ChildArticle> result) {
        this.result = result;
    }

    public List<ChildArticle> findChildArticle(ActionEvent event) {
        result = service.findChildArticle(this.childArticle.getBrand(), this.childArticle.getAttributes(), this.childArticle.getColor(), this.childArticle.getSize());
        return result;
    }

}
