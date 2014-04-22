/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.Color;
import de.pelango.wawi.services.ColorService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */
@FacesConverter(value = "colorConverter", forClass = Color.class)
public class ColorConverter implements Converter {

    @EJB
    private ColorService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            Color color = new Color();
            color.setName(value);
            color.setId(service.getColorId(value));
            return color;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

}
