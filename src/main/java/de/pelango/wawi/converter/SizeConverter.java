/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.SizeService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */
@FacesConverter(value = "sizeConverter", forClass = Sizes.class)
public class SizeConverter implements Converter {

    @EJB
    private SizeService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = service.getSizeId(value);
        Sizes s = new Sizes();
        s.setId(id);
        s.setName(value);
        return s;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
