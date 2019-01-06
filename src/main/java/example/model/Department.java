package example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
