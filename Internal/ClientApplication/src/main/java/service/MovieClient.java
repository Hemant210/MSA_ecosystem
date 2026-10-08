/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.Moviemaster;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;

/**
 *
 * @author 123
 */
@Named("movieClient")
@RequestScoped
public class MovieClient {

    private String movie;
    private String city;

    private Collection<Moviemaster> movies;

    public void search() {

        String token = "eyJraWQiOiJqd3Qua2V5IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJkdWtlIiwidXBuIjoiZHVrZSIsImF1dGhfdGltZSI6MTc5MTQ3MzU5OSwiaXNzIjoiYWlyaGFja3MiLCJncm91cHMiOlsiTU9WSUVVU0VSIl0sImV4cCI6MTc5MTQ3NDU5OSwiaWF0IjoxNzkxNDczNTk5LCJqdGkiOiI0MiJ9.mUAN_4vZJHDmj1kJuDro6TSgAWafl_ThYL9RL6EhzVmlQ8esNtK3D2eJGrXEsfzryiVOM1VG_W_4Z2z5jnkozgdJYZcHhhAlVsxOJJMOU_5392v153BJAlqJJwl2Ss82ZQ6RSCHERJBzofTLIYhalCbAx5SOu_V2TJPaXHaMKgD_OMGuPEdzJk7-Q-IXUOqyWkHnlfV6a0x99XFROYTxrXlQ6LPyPGsbJtRLXcOnEmEdnNqJCS6fMRLJxnbtNBdaoOzm1ldWYnX1p11rSo4197u8uUKIioJQhhu-60b6yl_oCJOcco-PoUTYuemqAbDMzADYgSfyvV7KRSKgwhHMKw";
        
        Client client = ClientBuilder.newClient();

        try {
            WebTarget target = client
                    .target("http://localhost:8085/MovieApplication/rest/movies/search")
                    .queryParam("movie", movie)
                    .queryParam("city", city);

            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .get();

            System.out.println("URL = " + target.getUri());
            System.out.println("STATUS = " + response.getStatus());
            System.out.println("TYPE = " + response.getMediaType());

            if (response.getStatus() == 200) {

                movies = response.readEntity(
                        new GenericType<Collection<Moviemaster>>() {
                }
                );

                System.out.println("MOVIES = " + movies);

            } else {

                String error = response.readEntity(String.class);
                System.out.println("ERROR = " + error);

                movies = null;
            }

            response.close();

        } catch (Exception e) {

            e.printStackTrace();
            movies = null;

        } finally {
            client.close();
        }
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<Moviemaster> getMovies() {
        return movies;
    }
}
