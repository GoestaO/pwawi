/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Gender;
import de.pelango.wawi.model.Material;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.model.ProductType;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.ArticleService;
import de.pelango.wawi.util.SelectManyGenderBean;
import de.pelango.wawi.util.SelectManyMaterialBean;
import de.pelango.wawi.util.SelectManyProductTypeBean;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.beanutils.BeanUtils;

/**
 * The controller implementation for the parentarticle edit page.
 *
 * @author goesta
 */
@Named
@SessionScoped
public class ParentArticleEditController implements Serializable {

    @EJB
    private ArticleService articleService;

    @Inject
    private Conversation conversation;

    @Inject
    private SelectManyMaterialBean selectManyMaterialBean;

    @Inject
    private SelectManyGenderBean selectManyGenderBean;

    @Inject
    private SelectManyProductTypeBean selectManyProductTypeBean;

    private ParentArticle pa;

    private List<Material> materialListRight;

    private List<Gender> genderListRight;

    private List<ProductType> productTypeListRight;

    private List<Material> selectedMaterialRight;

    private List<Material> selectedMaterialLeft;

    private List<Gender> selectedGenderLeft;

    private List<Gender> selectedGenderRight;

    private List<ProductType> selectedProductTypesLeft;

    private List<ProductType> selectedProductTypesRight;

    private BigInteger ean;

    private Sizes size;

    public ParentArticle getPa() {
        return pa;
    }

    public void setPa(ParentArticle pa) {
        this.pa = pa;
    }

    public List<Material> getMaterialListRight() {
        List<Material> materialAvailable = selectManyMaterialBean.getMaterialList();
        if (pa != null) {
            materialAvailable.removeAll(pa.getMaterial());
        }
        materialListRight = materialAvailable;
        return materialListRight;
    }

    public void setMaterialListRight(List<Material> materialListRight) {
        this.materialListRight = materialListRight;
    }

    public List<Material> getSelectedMaterialRight() {
        return selectedMaterialRight;
    }

    public void setSelectedMaterialRight(List<Material> selectedMaterialRight) {
        this.selectedMaterialRight = selectedMaterialRight;
    }

    public List<Material> getSelectedMaterialLeft() {
        return selectedMaterialLeft;
    }

    public void setSelectedMaterialLeft(List<Material> selectedMaterialLeft) {
        this.selectedMaterialLeft = selectedMaterialLeft;
    }

    public List<Gender> getGenderListRight() {
        List<Gender> genderList = selectManyGenderBean.getGenderList();
        if (pa != null) {
            genderList.removeAll(pa.getGender());
        }
        genderListRight = genderList;
        return genderListRight;
    }

    public void setGenderListRight(List<Gender> genderListRight) {
        this.genderListRight = genderListRight;
    }

    public List<Gender> getSelectedGenderLeft() {
        return selectedGenderLeft;
    }

    public void setSelectedGenderLeft(List<Gender> selectedGenderLeft) {
        this.selectedGenderLeft = selectedGenderLeft;
    }

    public List<Gender> getSelectedGenderRight() {
        return selectedGenderRight;
    }

    public void setSelectedGenderRight(List<Gender> selectedGenderRight) {
        this.selectedGenderRight = selectedGenderRight;
    }

    public List<ProductType> getProductTypeListRight() {
        List<ProductType> productTypeList = selectManyProductTypeBean.getProductTypeList();
        if (pa != null) {
            productTypeList.removeAll(pa.getProductTypes());
        }
        productTypeListRight = productTypeList;
        return productTypeListRight;
    }

    public void setProductTypeListRight(List<ProductType> productTypeListRight) {
        this.productTypeListRight = productTypeListRight;
    }

    public List<ProductType> getSelectedProductTypesLeft() {
        return selectedProductTypesLeft;
    }

    public void setSelectedProductTypesLeft(List<ProductType> selectedProductTypesLeft) {
        this.selectedProductTypesLeft = selectedProductTypesLeft;
    }

    public List<ProductType> getSelectedProductTypesRight() {
        return selectedProductTypesRight;
    }

    public void setSelectedProductTypesRight(List<ProductType> selectedProductTypesRight) {
        this.selectedProductTypesRight = selectedProductTypesRight;
    }

    public BigInteger getEan() {
        return ean;
    }

    public void setEan(BigInteger ean) {
        this.ean = ean;
    }

    public Sizes getSize() {
        return size;
    }

    public void setSize(Sizes size) {
        this.size = size;
    }

    public void addMaterial(ActionEvent event) {
        List<Material> materialToBeMoved = new ArrayList<>();
        materialToBeMoved.addAll(selectedMaterialRight);
        this.pa.getMaterial().addAll(materialToBeMoved);
    }

    public void removeMaterial(ActionEvent event) {
        this.pa.getMaterial().removeAll(selectedMaterialLeft);
    }

    public void addGender(ActionEvent event) {
        List<Gender> genderToBeMoved = new ArrayList<>();
        genderToBeMoved.addAll(selectedGenderRight);
        this.pa.getGender().addAll(genderToBeMoved);
    }

    public void removeGender(ActionEvent event) {
        this.pa.getGender().removeAll(selectedGenderLeft);
    }

    public void addProductType(ActionEvent event) {
        List<ProductType> productTypesToBeMoved = new ArrayList<>();
        productTypesToBeMoved.addAll(selectedProductTypesRight);
        this.pa.getProductTypes().addAll(productTypesToBeMoved);
    }

    public void removeProductType(ActionEvent event) {
        this.pa.getProductTypes().removeAll(selectedProductTypesLeft);
    }

    // Control start and end of conversation
    public void start() {
        conversation.begin();
    }

    public void end() {
        conversation.end();
    }

    public void doSave(ParentArticle pa) {
        String sku = pa.getSku();

        // Alle zugehörigen ChildArticles abrufen
        List<ChildArticle> childs = articleService.findArticlesBySKU(sku);
        for (ChildArticle c : childs) {
            try {
                // SKU sichern
                String tempSKU = c.getSku();

                // Alle Properties vom Parent aufs Child übertragen
                BeanUtils.copyProperties(c, pa);

                // Die alte SKU wiederherstellen
                c.setSku(tempSKU);
                articleService.update(c);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(ParentArticleEditController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        articleService.update(pa);
    }

    public String addChildArticle() {
        ChildArticle ca = new ChildArticle(this.getSize(), this.getPa().getSku(), this.getPa().getBrand(), this.getPa().getModel(), this.getPa().getMisc(), this.getPa().getColor(), this.getPa().getAttribute(), this.getPa().getGender(), this.getPa().isTopProduct(), this.getPa().isTopProductMobile(), this.getPa().isSpecialProduct(), this.getPa().getMaterial(), this.getPa().getProductTypes(), this.getPa().getCategory(), this.getPa().getNumberOfPictures(), this.getPa().getShortDescription(), this.getPa().getLongDescription());
        ca.setEan(this.getEan().longValue());
        articleService.create(ca);
        this.end();
        return "inbound?faces-redirect=true";
    }

}
