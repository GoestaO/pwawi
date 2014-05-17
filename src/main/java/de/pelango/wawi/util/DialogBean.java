/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author goesta
 */

@Named
@RequestScoped
public class DialogBean {

    public void viewChildArticleEdit() {
        RequestContext.getCurrentInstance().openDialog("childArticleEdit");
    }
}
