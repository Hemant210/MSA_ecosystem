/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package ejb;

import entity.Emp;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author 123
 */
@Local
public interface EmpBeanLocal {
    void addEmp(Emp emp);
    void UpdateEmp(Emp emp);
    List<Emp> getAllEmp();
    void deleteEmp(Integer userId);
}
