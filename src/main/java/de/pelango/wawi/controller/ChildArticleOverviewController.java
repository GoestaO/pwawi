package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * The controller for the childArticleOverview-Site
 * 
* @author goesta
 */
@ViewScoped
@Named
public class ChildArticleOverviewController implements Serializable {

    @EJB
    private ArticleService articleService;

    @Inject
    private ChildArticleEditController childArticleEditController;

    private List<ChildArticle> articles;

    private List<ChildArticle> selectedChildArticles;

    private ChildArticle selectedChildArticle;

    @PostConstruct
    public void init() {
        articles = articleService.findAllChildArticles();
    }

    public List<ChildArticle> getArticles() {
        if (articles == null) {
            articles = this.loadData();
        }
        return articles;
    }

    public List<ChildArticle> getSelectedChildArticles() {
        return selectedChildArticles;
    }

    public void setSelectedChildArticles(List<ChildArticle> selectedChildArticles) {
        this.selectedChildArticles = selectedChildArticles;
    }

    public ChildArticle getSelectedChildArticle() {
        return selectedChildArticle;
    }

    public void setSelectedChildArticle(ChildArticle selectedChildArticle) {
        this.selectedChildArticle = selectedChildArticle;
    }

    private List<ChildArticle> loadData() {
        return articleService.findAllChildArticles();

    }
    
    public void test(){
        System.out.println("this = " + this.selectedChildArticle);
    }

    public void update(List<ChildArticle> articleList) {
        for (ChildArticle ca : articleList) {
//            Long id = ca.getId();
            Long ean = ca.getEan();
            String manufacturerSKU = ca.getManufacturerSKU();
            int quantity = ca.getQuantity();
            BigDecimal purchasePrice = ca.getPurchasePrice();
            Sizes size = ca.getSize();
            BigDecimal shopPrice = ca.getShopPrice();
            BigDecimal ebayPrice = ca.getEbayPrice();
            BigDecimal amazonPrice = ca.getAmazonPrice();
            ChildArticle childArticle = articleService.findChildArticle(ca.getSku());
            if (childArticle != null) {
                childArticle.setEan(ean);
                childArticle.setManufacturerSKU(manufacturerSKU);
                childArticle.setSize(size);
                childArticle.setQuantity(quantity);
                childArticle.setPurchasePrice(purchasePrice);
                childArticle.setShopPrice(shopPrice);
                childArticle.setEbayPrice(ebayPrice);
                childArticle.setAmazonPrice(amazonPrice);
                articleService.update(childArticle);
            }
        }

    }

}
