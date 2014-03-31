/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.Category;
import de.pelango.wawi.services.CategoryService;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@RequestScoped

public class SelectCategoryBean {

    @EJB
    private CategoryService service;

    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        categoryList = service.getCategories();
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

}
