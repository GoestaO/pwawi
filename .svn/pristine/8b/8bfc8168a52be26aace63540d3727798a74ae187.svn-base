/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.Attribute;
import de.pelango.wawi.services.AttributeService;
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
public class AttributeBean {

    @EJB
    private AttributeService service;

    private List<Attribute> attributeList;

    public List<Attribute> getAttribute() {
        return service.getAttributes();
    }

    public void setAttribute(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

}
