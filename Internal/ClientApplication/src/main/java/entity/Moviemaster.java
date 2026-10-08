/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author 123
 */
@Entity
@Table(name = "moviemaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Moviemaster.findAll", query = "SELECT m FROM Moviemaster m"),
    @NamedQuery(name = "Moviemaster.findByMovieid", query = "SELECT m FROM Moviemaster m WHERE m.movieid = :movieid"),
    @NamedQuery(name = "Moviemaster.findByMoviename", query = "SELECT m FROM Moviemaster m WHERE m.moviename = :moviename"),
    @NamedQuery(name = "Moviemaster.findByMovietype", query = "SELECT m FROM Moviemaster m WHERE m.movietype = :movietype"),
    @NamedQuery(name = "Moviemaster.findByLanguage", query = "SELECT m FROM Moviemaster m WHERE m.language = :language"),
    @NamedQuery(name = "Moviemaster.findByDuration", query = "SELECT m FROM Moviemaster m WHERE m.duration = :duration"),
    @NamedQuery(name = "Moviemaster.findByRating", query = "SELECT m FROM Moviemaster m WHERE m.rating = :rating")})
public class Moviemaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "movieid")
    private Integer movieid;
    @Size(max = 100)
    @Column(name = "moviename")
    private String moviename;
    @Size(max = 50)
    @Column(name = "movietype")
    private String movietype;
    @Size(max = 50)
    @Column(name = "language")
    private String language;
    @Column(name = "duration")
    private Integer duration;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Double rating;
    @JoinTable(name = "master_movies", joinColumns = {
        @JoinColumn(name = "movieid", referencedColumnName = "movieid")}, inverseJoinColumns = {
        @JoinColumn(name = "theaterid", referencedColumnName = "theaterid")})
    @ManyToMany
    private Collection<Theater> theaterCollection;

    public Moviemaster() {
    }

    public Moviemaster(Integer movieid) {
        this.movieid = movieid;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMovietype() {
        return movietype;
    }

    public void setMovietype(String movietype) {
        this.movietype = movietype;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @XmlTransient
    @JsonbTransient
    public Collection<Theater> getTheaterCollection() {
        return theaterCollection;
    }

    public void setTheaterCollection(Collection<Theater> theaterCollection) {
        this.theaterCollection = theaterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movieid != null ? movieid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Moviemaster)) {
            return false;
        }
        Moviemaster other = (Moviemaster) object;
        if ((this.movieid == null && other.movieid != null) || (this.movieid != null && !this.movieid.equals(other.movieid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Moviemaster[ movieid=" + movieid + " ]";
    }
    
}
