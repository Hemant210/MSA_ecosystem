/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package EJB;

import entity.Moviemaster;
import jakarta.ejb.Local;
import java.util.Collection;

/**
 *
 * @author 123
 */
@Local
public interface MovieBeanLocal {

    Collection<Moviemaster> getMovies(String moviename, String city);
}
