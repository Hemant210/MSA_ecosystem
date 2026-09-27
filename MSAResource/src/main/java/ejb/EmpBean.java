/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package ejb;

import entity.Emp;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author 123
 */
@Stateless
public class EmpBean implements EmpBeanLocal {

    @PersistenceContext(unitName = "mypu")
    EntityManager em;

    @Override
    public void addEmp(Emp emp) {
        em.persist(emp);
    }

    @Override
    public void UpdateEmp(Emp emp) {
        em.merge(emp);
    }

    @Override
    public List<Emp> getAllEmp() {
        List<Emp> emps = em.createNamedQuery("Emp.findAll").getResultList();
        return emps;
    }

    @Override
    public void deleteEmp(Integer userId) {
         Emp emp = em.find(Emp.class, userId);
        if (emp != null) {
            em.remove(emp);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
