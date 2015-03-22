/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.webservice;

import de.pelango.wawi.model.ParentArticle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author goesta
 */
@Stateless
@Path("parentarticle")
public class ParentArticleFacadeREST extends AbstractFacade<ParentArticle> {
    @PersistenceContext(unitName = "de.pelango_Wawi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ParentArticleFacadeREST() {
        super(ParentArticle.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ParentArticle entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, ParentArticle entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ParentArticle find(@PathParam("id") String sku) {
        return super.find(sku);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ParentArticle> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ParentArticle> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
