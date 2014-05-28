/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@SessionScoped
public class ChildArticleEditController implements Serializable {

    @EJB
    private ArticleService service;

    private ChildArticle childArticle;

    public ChildArticle getChildArticle() {
        return childArticle;
    }

    public void setChildArticle(ChildArticle childArticle) {
        this.childArticle = childArticle;
    }

    public void doSave(ChildArticle childArticle) {
//        System.out.println("childArticle = " + childArticle.getEan());
        service.update(childArticle);
//        return "childArticleOverview?faces-redirect=true";
    }

}
