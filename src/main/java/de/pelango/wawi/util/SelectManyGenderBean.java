/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import de.pelango.wawi.model.Gender;
import de.pelango.wawi.services.GenderService;
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
public class SelectManyGenderBean {

    @EJB
    private GenderService service;

    private List<Gender> genderList;

    public List<Gender> getGenderList() {
        genderList = service.getGenders();
        return genderList;
    }

    public void setGenderList(List<Gender> genderList) {
        this.genderList = genderList;
    }

}
