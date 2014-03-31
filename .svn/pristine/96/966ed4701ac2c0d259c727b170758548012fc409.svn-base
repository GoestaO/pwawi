/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.Attribute;
import de.pelango.wawi.services.AttributeService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */
@FacesConverter(value = "attributeConverter", forClass = Attribute.class)
public class AttributeConverter implements Converter {

    @EJB
    private AttributeService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = service.getAttributeId(value);
        Attribute a = new Attribute();
        a.setName(value);
        a.setId(id);
        return a;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
