/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generators;

import de.pelango.wawi.services.NumberService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Gösta Ostendorf <goesta.o@gmail.com>
 */

@Named
@RequestScoped
public class SKUGenerator {

    @EJB
    private NumberService service;

   
    public Long generateNumber() {
        Long maxNumber = service.getMaxNumber();
        Long proposedNumber = maxNumber + 1;
        return proposedNumber;

    }

}
