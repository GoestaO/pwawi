/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.ProductType;
import de.pelango.wawi.services.ProductTypeService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */
@FacesConverter(value = "productTypeConverter", forClass = ProductType.class)
public class ProductTypeConverter implements Converter {

    @EJB
    private ProductTypeService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            Long id = service.getProductTypeId(value);
            ProductType pt = new ProductType();
            pt.setId(id);
            pt.setName(value);
            return pt;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
