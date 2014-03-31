/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.Category;
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
public class CategoryService {

    @PersistenceContext
    private EntityManager em;

    public List<Category> getCategories() {
        TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        List<Category> result = query.getResultList();
        return result;
    }

    public Long getCategoryId(String name) {
        TypedQuery<Long> query = em.createQuery("select c.id from Category c where c.name = :name", Long.class);
        query.setParameter("name", name);
//        query.setMaxResults(1);
        Long id = query.getSingleResult();
        return id;
    }
}
