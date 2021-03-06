/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.Material;
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
public class MaterialService {

    @PersistenceContext
    private EntityManager em;

    public List<Material> getMaterial() {
        TypedQuery<Material> query = em.createQuery("select m from Material m", Material.class);
        List<Material> result = query.getResultList();
        return result;
    }

    public Long getMaterialId(String name) {
        TypedQuery<Long> query = em.createQuery("select m.id from Material m where m.name = :name", Long.class);
        query.setParameter("name", name);
        Long id = query.getSingleResult();
        return id;
    }
        
}
