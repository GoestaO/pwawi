/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.Material;
import de.pelango.wawi.services.MaterialService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */

@FacesConverter(value = "materialConverter", forClass = Material.class)
public class MaterialConverter implements Converter {

    @EJB
    private MaterialService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = service.getMaterialId(value);
        Material m = new Material();
        m.setId(id);
        m.setName(value);
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
