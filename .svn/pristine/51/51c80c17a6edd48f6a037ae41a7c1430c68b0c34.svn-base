/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.Color;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author goesta
 */
@Stateless
public class ColorService {

    @PersistenceContext
    private EntityManager em;

    public void createInitialColors() {
        Color c1 = new Color();
        c1.setName("Schwarz");

        Color c2 = new Color();
        c2.setName("Blau");

        Color c3 = new Color();
        c3.setName("Grün");
        
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
    }

    public List<Color> getColors() {
        TypedQuery<Color> query = em.createQuery("Select c from Color c order by c.name", Color.class);
        List<Color> colorList = query.getResultList();
        return colorList;
    }

    public Long getColorId(String name) {
        TypedQuery<Long> query = em.createQuery("Select c.id from Color c where c.name = :name", Long.class);
        query.setParameter("name", name);
        Long id = query.getSingleResult();
        return id;
    }
}
