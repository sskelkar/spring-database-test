package example.dao;

import example.model.Department;
import example.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAO extends BaseDAO {
  public Employee getById(Integer id) {
    return get(Employee.class, id);
  }

  public void save(Employee Employee) {
    save(Employee);
  }

  public List<Employee> getEmployeesOfDepartment(Integer departmentId) {
    return getSession().createQuery("from Employee where department_id = :departmentId", Employee.class)
      .setParameter("departmentId", departmentId)
      .list();
  }
}
