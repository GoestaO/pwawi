/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.Material;
import de.pelango.wawi.services.MaterialService;
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
public class SelectManyMaterialBean {

    @EJB
    private MaterialService service;

    private List<Material> materialList;

    public List<Material> getMaterialList() {
        materialList = service.getMaterial();
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

}
