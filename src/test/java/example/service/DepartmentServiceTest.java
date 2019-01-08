package example.service;

import example.TestConfiguration;
import example.dao.DepartmentDAO;
import example.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class DepartmentServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
  @Resource
  private DepartmentService departmentService;
  @Resource
  private DepartmentDAO departmentDAO;

  @Test
  public void shouldReturnEmployeesInAGivenDepartment() {
    //given
    jdbcTemplate.execute("insert into department values(30, 'admin')");
    jdbcTemplate.execute("insert into department values(40, 'support')");
    jdbcTemplate.execute("insert into employee values(1, 'john', 30)");
    jdbcTemplate.execute("insert into employee values(2, 'paul', 30)");
    jdbcTemplate.execute("insert into employee values(3, 'george', 40)");

    //when
    departmentDAO.getById(30);
    List<Employee> employees = departmentService.getEmployeesInDepartment("admin");

    //then
    assertEquals(2, employees.size());
    assertEquals(asList(1, 2), employees.stream().map(Employee::getId).collect(toList()));
  }
}