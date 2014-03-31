/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.converter;

import de.pelango.wawi.model.Gender;
import de.pelango.wawi.services.GenderService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author goesta
 */
@FacesConverter(value = "genderConverter", forClass = Gender.class)
public class GenderConverter implements Converter {

    @EJB
    private GenderService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Long id = service.getGenderId(value);
        Gender gender = new Gender();
        gender.setId(id);
        gender.setName(value);
        return gender;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
