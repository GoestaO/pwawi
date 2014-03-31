/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.Sizes;
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
public class SizeService {

    @PersistenceContext
    private EntityManager em;

    private String parameter;

    public List<Sizes> getSizes() {
        TypedQuery<Sizes> query = em.createQuery("Select s from Sizes s", Sizes.class);
        List<Sizes> result = query.getResultList();
        return result;
    }

    public Long getSizeId(String name) {
        TypedQuery<Long> query = em.createQuery("Select s.id from Sizes s where s.name = :name", Long.class);
        query.setParameter("name", name);
        Long id = query.getSingleResult();
        return id;
    }

    /** Gets the sizes filtered by the given attribute: 'Default' -> DressSize, 'Jeans' -> 'JeansSize', '
     * 
     * @param sizeType
     * @return 
     */    
    public List<Sizes> getSizesByType(String sizeType) {
        switch (sizeType) {
            case "Jeans":
                parameter = "JeansSize";
                break;
            case "Default":
                parameter = "DressSize";
                break;
            default:
                parameter = "InternationalSize";

        }
        TypedQuery<Sizes> query = em.createQuery("Select s from " + parameter + " s", Sizes.class);
        List<Sizes> result = query.getResultList();
        return result;
    }
}
