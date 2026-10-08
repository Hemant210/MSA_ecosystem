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
@Table(name = "theater")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Theater.findAll", query = "SELECT t FROM Theater t"),
    @NamedQuery(name = "Theater.findByTheaterid", query = "SELECT t FROM Theater t WHERE t.theaterid = :theaterid"),
    @NamedQuery(name = "Theater.findByTheatername", query = "SELECT t FROM Theater t WHERE t.theatername = :theatername"),
    @NamedQuery(name = "Theater.findByCity", query = "SELECT t FROM Theater t WHERE t.city = :city"),
    @NamedQuery(name = "Theater.findByAddress", query = "SELECT t FROM Theater t WHERE t.address = :address"),
    @NamedQuery(name = "Theater.findByCapacity", query = "SELECT t FROM Theater t WHERE t.capacity = :capacity"),
    @NamedQuery(name = "Theater.findByTheaterType", query = "SELECT t FROM Theater t WHERE t.theaterType = :theaterType")})
public class Theater implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "theaterid")
    private Integer theaterid;
    @Size(max = 100)
    @Column(name = "theatername")
    private String theatername;
    @Size(max = 100)
    @Column(name = "city")
    private String city;
    @Size(max = 200)
    @Column(name = "address")
    private String address;
    @Column(name = "capacity")
    private Integer capacity;
    @Size(max = 50)
    @Column(name = "theater_type")
    private String theaterType;
    @ManyToMany(mappedBy = "theaterCollection")
    private Collection<Moviemaster> moviemasterCollection;

    public Theater() {
    }

    public Theater(Integer theaterid) {
        this.theaterid = theaterid;
    }

    public Integer getTheaterid() {
        return theaterid;
    }

    public void setTheaterid(Integer theaterid) {
        this.theaterid = theaterid;
    }

    public String getTheatername() {
        return theatername;
    }

    public void setTheatername(String theatername) {
        this.theatername = theatername;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getTheaterType() {
        return theaterType;
    }

    public void setTheaterType(String theaterType) {
        this.theaterType = theaterType;
    }

    @XmlTransient
    @JsonbTransient
    public Collection<Moviemaster> getMoviemasterCollection() {
        return moviemasterCollection;
    }

    public void setMoviemasterCollection(Collection<Moviemaster> moviemasterCollection) {
        this.moviemasterCollection = moviemasterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (theaterid != null ? theaterid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theater)) {
            return false;
        }
        Theater other = (Theater) object;
        if ((this.theaterid == null && other.theaterid != null) || (this.theaterid != null && !this.theaterid.equals(other.theaterid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Theater[ theaterid=" + theaterid + " ]";
    }
    
}
