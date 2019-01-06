package example.service;

import example.dao.DepartmentDAO;
import example.dao.EmployeeDAO;
import example.model.Department;
import example.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
  @Resource
  private EmployeeDAO employeeDAO;
  @Resource
  private DepartmentDAO departmentDAO;

  public List<Employee> getEmployeesInDepartment(String departmentName) {
    Department department = departmentDAO.getByName(departmentName);
    return employeeDAO.getEmployeesOfDepartment(department.getId());
  }
}
