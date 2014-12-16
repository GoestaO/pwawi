package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
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

    @PostConstruct
    public void init() {
        articles = articleService.findAllChildArticles();
    }

    public List<ChildArticle> getArticles() {
        return articles;
    }

    public List<ChildArticle> getSelectedChildArticles() {
        return selectedChildArticles;
    }

    public void setSelectedChildArticles(List<ChildArticle> selectedChildArticles) {
        this.selectedChildArticles = selectedChildArticles;
    }

    public void update(List<ChildArticle> articleList) {
        for (ChildArticle ca : articleList) {
//            Long id = ca.getId();
            Long ean = ca.getEan();
            String manufacturerSKU = ca.getManufacturerSKU();
            int quantity = ca.getQuantity();
            BigDecimal purchasePrice = ca.getPurchasePrice();
            BigDecimal shopPrice = ca.getShopPrice();
            BigDecimal ebayPrice = ca.getEbayPrice();
            BigDecimal amazonPrice = ca.getAmazonPrice();
            ChildArticle childArticle = articleService.findChildArticle(ca.getSku());
            if (childArticle != null) {
                childArticle.setEan(ean);
                childArticle.setManufacturerSKU(manufacturerSKU);
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
