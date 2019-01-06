package example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "department_id")
  private Integer departmentId;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getDepartmentId() {
    return departmentId;
  }
}
