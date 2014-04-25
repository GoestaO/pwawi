/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.Material;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.services.ArticleService;
import de.pelango.wawi.util.SelectManyMaterialBean;
import java.io.Serializable;
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
public class ParentArticleEditController implements Serializable {

    @EJB
    private ArticleService articleService;

    @Inject
    private SelectManyMaterialBean selectManyMaterialBean;

    private ParentArticle pa;

    private List<Material> materialListRight;

    private List<Material> selectedRight;

    private List<Material> selectedLeft;

    public ParentArticle getPa() {
        return pa;
    }

    public void setPa(ParentArticle pa) {
        this.pa = pa;
    }

    public List<Material> getMaterialListRight() {
        return materialListRight;
    }

    public void setMaterialListRight(List<Material> materialListRight) {
        this.materialListRight = selectManyMaterialBean.getMaterialList();
    }

    public List<Material> getSelectedRight() {
        return selectedRight;
    }

    public void setSelectedRight(List<Material> selectedRight) {
        this.selectedRight = selectedRight;
    }

    public List<Material> getSelectedLeft() {
        return selectedLeft;
    }

    public void setSelectedLeft(List<Material> selectedLeft) {
        this.selectedLeft = selectedLeft;
    }

    public void addMaterial(ActionEvent event) {

      
        System.out.println("hinzugefügt: " + selectedRight.toString());

    }

    public void removeMaterial(ActionEvent event) {

        System.out.println("material = " + selectedLeft.toString());

    }

    public String doSave(ParentArticle pa) {
        articleService.update(pa);
        return "parentArticleOverview?faces-redirect=true";
    }

}
