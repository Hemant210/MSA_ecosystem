/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import entity.Moviemaster;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collection;

/**
 *
 * @author 123
 */
@Stateless
public class MovieBean implements MovieBeanLocal {

    
    @PersistenceContext(unitName = "MoviePU")
    EntityManager em;
   
    
    @Override
    public Collection<Moviemaster> getMovies(String moviename, String city) {
    
        return em.createQuery(
            "SELECT m FROM Moviemaster m " +
            "JOIN m.theaterCollection t " +
            "WHERE m.moviename = :movie " +
            "AND t.city = :city",
            Moviemaster.class)
            .setParameter("movie", moviename)
            .setParameter("city", city)
            .getResultList();

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
