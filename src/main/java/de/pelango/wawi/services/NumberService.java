/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Gösta Ostendorf <goesta.o@gmail.com>
 */
@Stateless
public class NumberService {

    @PersistenceContext
    private EntityManager em;

    public Long getMaxNumber() {
        TypedQuery<Long> query = em.createQuery("Select max(p.number) from PrefixNumber p", Long.class);
        Long result = query.getSingleResult();
        return result;
    }
}
