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

    public ParentArticle findParentArticle(Long id){
        return (ParentArticle) em.find(ParentArticle.class, id);
    }
    
    public ChildArticle findChildArticle(Long id){
        return (ChildArticle) em.find(ChildArticle.class, id);
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

    public List<ChildArticle> findChildArticle(String brand, Color color, Sizes size) {
        List<ChildArticle> result;
        String queryString = "";

        if (!brand.isEmpty() && color != null && size != null) {
            queryString = "select c from ChildArticle c where c.brand = :brand and c.color =:color and c.size = :size";
        }

        if (!brand.isEmpty() && color != null && size == null) {
            queryString = "select c from ChildArticle c where c.brand = :brand and c.color =:color";
        }

        if (!brand.isEmpty() && color == null && size != null) {
            queryString = "select c from ChildArticle c where c.brand = :brand and c.size = :size";
        }

        if (brand.isEmpty() && color != null && size != null) {
            queryString = "select c from ChildArticle c where c.color =:color and c.size = :size";
        }

        if (!brand.isEmpty() && color == null && size == null) {
            queryString = "select c from ChildArticle c where c.brand = :brand";
        }

        if (brand.isEmpty() && color != null && size == null) {
            queryString = "select c from ChildArticle c where c.color =:color";
        }

        if (brand.isEmpty() && color == null && size != null) {
            queryString = "select c from ChildArticle c where c.size = :size";
        }
        if (brand.isEmpty() && color == null && size == null) {
            queryString = "select c from ChildArticle c";
        }

        TypedQuery<ChildArticle> query = em.createQuery(queryString, ChildArticle.class);

        if (!brand.isEmpty()) {
            query.setParameter("brand", brand);
        }
        if (color != null) {
            query.setParameter("color", color);
        }
        if (size != null) {
            query.setParameter("size", size);
        }
        try {
            result = query.getResultList();
        } catch (javax.persistence.NoResultException nrex) {
            result = null;
        }

        return result;
    }

    public List<ParentArticle> findParentArticle(String brand, Color color, Material material) {
        List<ParentArticle> result;
        String queryString = "";
        try {
            if (!brand.isEmpty() && color != null && material != null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.brand = :brand and c.color =:color and c.material = :material";
            }

            if (!brand.isEmpty() && color != null && material == null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.brand = :brand and c.color =:color";
            }

            if (!brand.isEmpty() && color == null && material != null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.brand = :brand and c.material = :material";
            }

            if (brand.isEmpty() && color != null && material != null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.color =:color and c.material = :material";
            }

            if (!brand.isEmpty() && color == null && material == null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.brand = :brand";
            }

            if (brand.isEmpty() && color != null && material == null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.color =:color";
            }

            if (brand.isEmpty() && color == null && material != null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle and c.material = :material";
            }
            if (brand.isEmpty() && color == null && material == null) {
                queryString = "select c from ParentArticle c where TYPE(c) = ParentArticle";
            }

            TypedQuery<ParentArticle> query = em.createQuery(queryString, ParentArticle.class);

            if (!brand.isEmpty()) {
                query.setParameter("brand", brand);
            }
            if (color != null) {
                query.setParameter("color", color);
            }
            if (material != null) {
                query.setParameter("material", material);
            }
            try {
                result = query.getResultList();
            } catch (javax.persistence.NoResultException nrex) {
                result = null;
            }
        } catch (NullPointerException npex) {
            result = null;
        }
        return result;
    }

}
