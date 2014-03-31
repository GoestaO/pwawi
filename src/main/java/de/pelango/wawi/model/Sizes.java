/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pelango.wawi.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author goesta
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "SIZE_TYPE")
@Table(name = "SIZES")
public class Sizes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "size")
    private List<ChildArticle> childArticles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildArticle> getChildArticles() {
        return childArticles;
    }

    public void setChildArticles(List<ChildArticle> childArticles) {
        this.childArticles = childArticles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sizes)) {
            return false;
        }
        Sizes other = (Sizes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.pelango.wawi.model.Sizes[ id=" + id + " ]";
    }
}
