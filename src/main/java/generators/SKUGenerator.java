/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generators;

import de.pelango.wawi.services.NumberService;
import generators.qualifier.SevenDigits;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gösta Ostendorf <goesta.o@gmail.com>
 */
@Named
@ViewScoped
@SevenDigits
public class SKUGenerator implements NumberGenerator, Serializable {

    @EJB
    private NumberService service;

    @Override
    public Long generateNumber() {
        Long maxNumber = service.getMaxNumber();
        Long proposedNumber = maxNumber + 1;
        return proposedNumber;

    }

    public void numberChanged(ValueChangeEvent e) {
        Long newValue = (Long) e.getNewValue();
        boolean alreadyExists = service.exists(newValue);
        if (alreadyExists) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!", "Präfix existiert bereits."));
        }
        String pattern = "^[0-9]{7}$";
        if(!newValue.toString().matches(pattern)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!", "Präfix hat entweder weniger als 7 Ziffern oder enthält Buchstaben."));
        }
    }

}
