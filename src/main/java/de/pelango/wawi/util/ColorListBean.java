/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.Color;
import de.pelango.wawi.services.ColorService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@RequestScoped
public class ColorListBean {

    @EJB
    private ColorService service;

    private List<Color> colorList;

    @PostConstruct
    public void init() {
        colorList = getColorList();
    }

    public List<Color> getColorList() {
        colorList = service.getColors();
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

}
