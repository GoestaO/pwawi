/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.Attribute;
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
public class AttributeService {

    @PersistenceContext
    private EntityManager em;

    public Long getAttributeId(String name) {
        TypedQuery<Long> query = em.createQuery("select a.id from Attribute a where a.name = :name", Long.class);
        query.setParameter("name", name);
        Long id = query.getSingleResult();
        return id;
    }

    public List<Attribute> getAttributes() {
        TypedQuery<Attribute> query = em.createQuery("select a from Attribute a", Attribute.class);
        List<Attribute> result = query.getResultList();
        return result;
    }
}
