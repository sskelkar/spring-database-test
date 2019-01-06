package example;

import example.service.DepartmentService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
    DepartmentService service = (DepartmentService) context.getBean("departmentService");

    service.getEmployeesInDepartment("hr")
      .forEach(emp -> System.out.println(emp.getId() + " - " + emp.getName()));

    context.close();
  }
}
