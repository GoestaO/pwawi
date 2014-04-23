/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ParentArticle;
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
public class ParentArticleEditController implements Serializable {

    @EJB
    private ArticleService service;

    private ParentArticle pa;

    public ParentArticle getPa() {
        return pa;
    }

    public void setPa(ParentArticle pa) {
        this.pa = pa;
    }

    public String doSave(ParentArticle pa) {
        service.update(pa);
        return "parentArticleOverview?faces-redirect=true";
    }

}
