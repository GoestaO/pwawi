/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.ArticleService;
import de.pelango.wawi.services.SizeService;
import de.pelango.wawi.util.SelectManyGenderBean;
import de.pelango.wawi.util.SelectManyMaterialBean;
import de.pelango.wawi.util.SelectManyProductTypeBean;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
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
    
    @EJB
    private SizeService sizeService;
    
//    @Inject
//    private Conversation conversation;
    
    @Inject
    private SelectManyMaterialBean selectManyMaterialBean;
    
    @Inject
    private SelectManyGenderBean selectManyGenderBean;
    
    @Inject
    private SelectManyProductTypeBean selectManyProductTypeBean;
    
    private ParentArticle pa;
    
    private List<ParentArticle> paList;
    
    private ChildArticle ca;
    
    private BigInteger ean;
    
    private Sizes size;
    
    private BigDecimal purchasePrice;
    
    private BigDecimal amazonPrice;
    
    private BigDecimal ebayPrice;
    
    private BigDecimal shopPrice;
    
    private BigDecimal specialPrice;
    
    private List<Sizes> availableSizes;
    
    public ParentArticle getPa() {
        return pa;
    }
    
    public void setPa(ParentArticle pa) {
        this.pa = pa;
    }
    
    public ChildArticle getCa() {
        return ca;
    }
    
    public void setCa(ChildArticle ca) {
        this.ca = ca;
    }
    
    public List<ParentArticle> getPaList() {
        return paList;
    }
    
    public void setPaList(List<ParentArticle> paList) {
        this.paList = paList;
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
    
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    public BigDecimal getAmazonPrice() {
        return amazonPrice;
    }
    
    public void setAmazonPrice(BigDecimal amazonPrice) {
        this.amazonPrice = amazonPrice;
    }
    
    public BigDecimal getEbayPrice() {
        return ebayPrice;
    }
    
    public void setEbayPrice(BigDecimal ebayPrice) {
        this.ebayPrice = ebayPrice;
    }
    
    public BigDecimal getShopPrice() {
        return shopPrice;
    }
    
    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }
    
    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }
    
    public List<Sizes> getAvailableSizes() {
//        if (availableSizes == null) {
//            availableSizes = loadAvailableSizes();
//        }
        return availableSizes;
    }
    
    public void setAvailableSizes(List<Sizes> availableSizes) {
        this.availableSizes = availableSizes;
    }
    
    public List<Sizes> loadAvailableSizes() {
        if (pa != null) {
            return sizeService.getAvailableSizes(this.getPa().getSku());
        } else {
            return null;
        }
    }

    // Control start and end of conversation
//    public void start() {
//        conversation.begin();
//    }
    
//    public void end() {
//        conversation.end();
//    }
    
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
    
    public void addChildArticle() {
        try {
            ChildArticle ca = new ChildArticle();
            BeanUtils.copyProperties(ca, pa);
            ca.setAmazonPrice(this.amazonPrice);
            ca.setShopPrice(this.shopPrice);
            ca.setPurchasePrice(this.purchasePrice);
            ca.setEbayPrice(this.ebayPrice);
            ca.setSize(this.size);
            ca.setSKU(this.pa.getSku() + "-" + this.size);
            if (this.ean != null) {
                ca.setEan(this.ean.longValue());
            }
            
            articleService.create(ca);
//            this.end();
//            return "inbound?faces-redirect=true";
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ParentArticleEditController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ParentArticleEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void test() {
//        System.out.println("sku = " + this.pa.getSku());
//        List<Sizes> sizes = this.loadAvailableSizes();
//        System.out.println("availableSizes = " + availableSizes.size());
        ChildArticle ca = new ChildArticle();
        try {
            BeanUtils.copyProperties(ca, pa);
            ca.setSKU(this.pa.getSku() + "-" + this.size);
            System.out.println("sku = " + ca.getSKU());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ParentArticleEditController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ParentArticleEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
