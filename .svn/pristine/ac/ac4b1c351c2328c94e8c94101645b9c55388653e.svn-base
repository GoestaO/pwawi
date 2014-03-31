/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.ProductType;
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
public class ProductTypeService {

    @PersistenceContext
    private EntityManager em;

    public Long getProductTypeId(String name) {
        TypedQuery<Long> query = em.createQuery("select p.id from ProductType p where p.name = :name", Long.class);
        query.setParameter("name", name);
        query.setMaxResults(1);
        Long id = query.getSingleResult();
        return id;
    }

    public List<ProductType> getProductTypes() {
        TypedQuery<ProductType> query = em.createQuery("select p from ProductType p", ProductType.class);
        List<ProductType> result = query.getResultList();
        return result;
    }

}
