/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.Category;
import de.pelango.wawi.services.CategoryService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */
@FacesConverter(value = "categoryConverter", forClass = Category.class)
public class CategoryConverter implements Converter {

    @EJB
    private CategoryService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            Long id = service.getCategoryId(value);
            Category c = new Category();
            c.setId(id);
            c.setName(value);
            return c;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return value.toString();
        } else {
            return "";
        }
    }

}
