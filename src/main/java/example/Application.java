package example;

import example.dao.DepartmentDAO;
import example.service.DepartmentService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
    DepartmentService service = (DepartmentService) context.getBean("departmentService");
    DepartmentDAO dao = (DepartmentDAO) context.getBean("departmentDAO");
    System.out.println(dao.getById(20).getName());

    service.getEmployeesInDepartment("hr")
      .forEach(emp -> System.out.println(emp.getId() + " - " + emp.getName()));

    context.close();
  }
}
