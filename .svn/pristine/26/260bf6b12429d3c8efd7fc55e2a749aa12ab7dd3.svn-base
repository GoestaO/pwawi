/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.Attribute;
import de.pelango.wawi.model.Sizes;
import de.pelango.wawi.services.SizeService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@ViewScoped
public class SelectManySizeBean implements Serializable {

    @EJB
    private SizeService service;
    private Attribute attribute;
    private String sizeType = "Default";

    private List<Sizes> sizeList;

    public List<Sizes> getSizeList() {
//        List<Sizes> sizes = service.getSizes();
        List<Sizes> sizes = service.getSizesByType(sizeType);
//            List<Sizes> sizes = service.getSizesByType(sizeType);
        return sizes;
    }

    public List<Sizes> getSizeListByType(String sizeType) {
        sizeList = service.getSizesByType(sizeType);
        return sizeList;
    }

    public void setSizeList(List<Sizes> sizeList) {
        this.sizeList = sizeList;
    }

    public void attributeChanged(ValueChangeEvent e) {
        attribute = (Attribute) e.getNewValue();
        sizeType = attribute.getName();
    }

    public Attribute getSizeType() {
        return attribute;
    }

    public void setSizeType(Attribute attribute) {
        this.attribute = attribute;
    }

}
