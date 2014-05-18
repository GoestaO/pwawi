package de.pelango.wawi.controller;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The controller for the childArticleOverview-Site
 * 
* @author goesta
 */
@RequestScoped
@Named
public class ChildArticleOverviewController implements Serializable {

    @EJB
    private ArticleService articleService;

    @Inject
    private ChildArticleEditController childArticleEditController;

    private List<ChildArticle> articles;

    private ChildArticle toBeEditedChildArticle;

    public ChildArticle getToBeEditedChildArticle() {
        return toBeEditedChildArticle;
    }

    public void setToBeEditedChildArticle(ChildArticle toBeEditedChildArticle) {
        this.toBeEditedChildArticle = toBeEditedChildArticle;
    }

    @PostConstruct
    public void init() {
        articles = articleService.findAllChildArticles();
    }

    public List<ChildArticle> getArticles() {
        return articles;
    }

    public String doEdit() {
        return "childArticleEdit?faces-redirect=true";
    }

}
