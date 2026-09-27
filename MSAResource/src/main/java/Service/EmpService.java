/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import ejb.EmpBeanLocal;
import entity.Emp;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author 123
 */
@RolesAllowed("chief")
@Path("/emp")
public class EmpService {
    
    @EJB 
    EmpBeanLocal local;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) // Added to handle response content negotiation properly
    public Response addEmp(Emp emp){
        local.addEmp(emp);
        return Response.status(Response.Status.CREATED).entity(emp).build(); // Optionally return the created object or .ok()
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getallEmp(){
        List<Emp> emps = local.getAllEmp();
        return Response.ok(emps).build();
    }
}