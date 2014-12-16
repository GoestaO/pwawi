package de.pelango.wawi.services;

import de.pelango.wawi.model.ChildArticle;
import de.pelango.wawi.model.Color;
import de.pelango.wawi.model.Material;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import de.pelango.wawi.model.ParentArticle;
import de.pelango.wawi.model.Sizes;
import java.lang.reflect.Field;
import java.math.BigInteger;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

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

    public ParentArticle findParentArticle(String sku) {
        return (ParentArticle) em.find(ParentArticle.class, sku);
    }

    public ChildArticle findChildArticle(String sku) {
        return (ChildArticle) em.find(ChildArticle.class, sku);
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

    /**
     * Finds all child articles having the same base sku
     *
     * @param sku
     * @return
     */
    public List<ChildArticle> findArticlesBySKU(String sku) {
        String queryString = "select c from ChildArticle c where c.sku like :sku";
        TypedQuery query = em.createQuery(queryString, ChildArticle.class);
        sku = sku + "%";
        query.setParameter("sku", sku);
        List<ChildArticle> result = query.getResultList();
        return result;
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

    public List<String> getFields() {
        Class c = ChildArticle.class;
        Class p = ParentArticle.class;
        Field[] fields = c.getDeclaredFields();
        Field[] fields2 = p.getDeclaredFields();
        List<String> fieldList = new ArrayList<>();
        for (Field f : fields) {
            if (!f.getName().contains("serialVersionUID") && !f.getName().contains("id")) {
                fieldList.add(f.getName());
            }
        }
        for (Field f : fields2) {
            if (!f.getName().contains("_") && !f.getName().contains("id")) {
                fieldList.add(f.getName());
            }
        }

        // Da SKU im Parent und im Child vorkommt, einen davon löschen 
        fieldList.remove("sku");
        return fieldList;
    }

}
