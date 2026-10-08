/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import EJB.MovieBeanLocal;
import entity.Moviemaster;
import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.Collection;
import jakarta.annotation.security.RolesAllowed;

/**
 *
 * @author 123
 */

@Path("/movies")
public class MovieService {
    
    @EJB
    MovieBeanLocal bean;
    
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("MOVIEUSER")
    public Collection<Moviemaster> getMovies(
            @QueryParam("movie") String moviename,
            @QueryParam("city") String city){
        return bean.getMovies(moviename, city);
    }
    
}
