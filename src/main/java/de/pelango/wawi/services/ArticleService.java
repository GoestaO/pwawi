package de.pelango.wawi.services;

import de.pelango.wawi.model.Attribute;
import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Color;
import de.pelango.wawi.model.Material;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.model.Sizes;
import java.math.BigInteger;
import javax.persistence.PersistenceContext;

/**
 * The Databasehandler for all queries related to articles
 *
 * @author goesta
 */
@Stateless
public class ArticleService {

    @PersistenceContext
    private EntityManager em;

    public void create(ParentArticle entity) {
        em.persist(entity);
    }

    public void update(ParentArticle entity) {
        em.merge(entity);
    }

    public void remove(ParentArticle entity) {
        em.remove(em.merge(entity));
    }

    public void print() {
        System.out.println("Hallo");
    }

    /**
     * Returns all Parentarticles
     *
     * @return
     */
    public List<ParentArticle> findAllParentArticles() {
        TypedQuery<ParentArticle> query = em.createQuery("select p from ParentArticle p where p not in (select c from ChildArticle c)", ParentArticle.class);
        List<ParentArticle> resultList = query.getResultList();
        return resultList;
    }

    /**
     * Returns all Childarticles
     *
     * @return
     */
    public List<ChildArticle> findAllChildArticles() {
        TypedQuery<ChildArticle> query = em.createQuery("select c from ChildArticle c", ChildArticle.class);
        List<ChildArticle> resultList = query.getResultList();
        return resultList;
    }

    /**
     * Returns the ChildArticle object having a specific ean
     *
     * @param ean
     * @return
     */
    public ChildArticle findChildArticleByEan(BigInteger ean) {
        Long eanLong = ean.longValue();
        TypedQuery<ChildArticle> query = em.createQuery("select c from ChildArticle c where c.ean like :eanLong ", ChildArticle.class);
        query.setParameter("eanLong", eanLong);
        try {
            ChildArticle result = query.getSingleResult();
            return result;
        } catch (javax.persistence.NoResultException nrex) {
            return null;
        }
    }
    
    public List<ChildArticle> findChildArticle(String brand, Attribute attribute, Color color, Sizes size) {
        List<ChildArticle> result;
        TypedQuery<ChildArticle> query = em.createQuery("select c from ChildArticle c where c.brand =:brand or :brand is null and c.attribute = :attribute or :attribute is null and c.color = :color or :color is null and c.size = :size or :size is null", ChildArticle.class);
        query.setParameter("brand", brand);
        query.setParameter("attribute", attribute);
        query.setParameter("color", color);
        query.setParameter("size", size);
        try {
            result = query.getResultList();
        } catch (javax.persistence.NoResultException nrex) {
            result = null;
        }
        return result;
    }

    /**
     * Returns all ParentArticles of one brand
     *
     * @return
     */
    public List<ParentArticle> findByBrand() {
        TypedQuery<ParentArticle> query = em.createQuery("select p from ParentArticle p where p.brand = :brand", ParentArticle.class);
        List<ParentArticle> resultList = query.getResultList();
        return resultList;
    }

}
