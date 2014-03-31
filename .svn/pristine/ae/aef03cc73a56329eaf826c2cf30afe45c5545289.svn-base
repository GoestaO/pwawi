/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author goesta
 */
@Named
@RequestScoped
public class SelectNumberOfPicturesBean {

    private List<Integer> pictureList;

    public List<Integer> getPictureList() {
        pictureList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            pictureList.add(i);
        }
        return pictureList;
    }

    public void setPictureList(List<Integer> pictureList) {
        this.pictureList = pictureList;
    }

}
