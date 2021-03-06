package de.pelango.wawi.controller;

import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.services.ArticleService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class ParentArticleOverviewController implements Serializable {

    @EJB
    private ArticleService articleService;

    private List<ParentArticle> articles;

    @Inject
    private ParentArticleEditController parentArticleEditController;

    public ParentArticleEditController getParentArticleEditController() {
        return parentArticleEditController;
    }

    public void setParentArticleEditController(ParentArticleEditController parentArticleEditController) {
        this.parentArticleEditController = parentArticleEditController;
    }

    @PostConstruct
    public void init() {
        articles = articleService.findAllParentArticles();

    }

    public List<ParentArticle> getArticles() {
        return articles;
    }

    public String edit(ParentArticle pa) {
//        parentArticleEditController.start();
        parentArticleEditController.setPa(pa);
        return "parentArticleEdit?faces-redirect=true";
    }

}
