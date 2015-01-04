/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.services;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Sizes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    private ArticleService articleService;

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

    /**
     * Gets the sizes filtered by the given attribute: 'Default' -> DressSize,
     * 'Jeans' -> 'JeansSize', 'InternationalSize
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
            case "InternationalSize":
                parameter = "InternationalSize";
                break;
            default:
                parameter = "Sizes";
                break;

        }

        TypedQuery<Sizes> query = em.createQuery("Select s from " + parameter + " s", Sizes.class);
        List<Sizes> result = query.getResultList();
        return result;
    }

    public List<Sizes> getAvailableSizes(String sku) {
        // Zunächst alle ChildArticles suchen, die zu einer SKU gehören

        List<ChildArticle> caList = articleService.findArticlesBySKU(sku);
        String sizeType = "";
        // Den SizeType des ersten ChildArticles ermitteln
        if (caList != null) {
            sizeType = caList.get(0).getAttribute().getName();
        }        

        // Die bereits vergebenen Größen ermitteln
        List<Sizes> notAvailableSizes = new ArrayList();
        for (ChildArticle ca : caList) {
            Sizes size = ca.getSize();
            if (size != null) {
                notAvailableSizes.add(size);
            }
        }
        List<Sizes> sizeList = this.getSizesByType(sizeType);
        sizeList.removeAll(notAvailableSizes);
        return sizeList;
    }
}
