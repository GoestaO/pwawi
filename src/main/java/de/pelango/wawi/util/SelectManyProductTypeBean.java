/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.ProductType;
import de.pelango.wawi.services.ProductTypeService;
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
public class SelectManyProductTypeBean {

    @EJB
    private ProductTypeService service;

    private List<ProductType> productTypeList;

    public List<ProductType> getProductTypeList() {
        productTypeList = service.getProductTypes();
        return productTypeList;
    }

    public void setProductTypeList(List<ProductType> productTypeList) {
        this.productTypeList = productTypeList;
    }
}
