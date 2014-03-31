/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.Gender;
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
public class GenderService {

    @PersistenceContext
    private EntityManager em;

    public void createInitialGenders() {
        Gender g1 = new Gender();
        g1.setName("Women");
        Gender g2 = new Gender();
        g2.setName("Men");
        Gender g3 = new Gender();
        g3.setName("Unisex");
        em.persist(g3);
        em.persist(g2);
        em.persist(g1);
    }

    public List<Gender> getGenders() {
        TypedQuery<Gender> query = em.createQuery("select g from Gender g", Gender.class);
        List<Gender> result = query.getResultList();
        return result;
    }

    public Long getGenderId(String name) {
        TypedQuery<Long> query = em.createQuery("select g.id from Gender g where g.name = :name", Long.class);
        query.setParameter("name", name);
        Long result = query.getSingleResult();
        return result;
    }

}
