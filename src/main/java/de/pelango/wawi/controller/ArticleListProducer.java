/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.controller;

import de.pelango.wawi.model.Color;
import de.pelango.wawi.model.Gender;
import de.pelango.wawi.model.Material;
import de.pelango.wawi.model.ProductType;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author goesta
 */
@Named
@SessionScoped
public class ArticleListProducer implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public void createMockEntities() {

        // Farben
        Color c1 = new Color();
        Color c2 = new Color();
        c1.setName("Rot");
        c2.setName("Schwarz");
        em.persist(c1);
        em.persist(c2);

        // Geschlecht
        Gender g1 = new Gender();
        Gender g2 = new Gender();
        g1.setName("Men's");
        g2.setName("Women's");

        // Material
        Material m1 = new Material();
        Material m2 = new Material();
        
        
        //Produkt-Typ
        ProductType p1 = new ProductType();
        ProductType p2 = new ProductType();
        
        
    }

}
