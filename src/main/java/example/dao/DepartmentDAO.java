package example.dao;

import example.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDAO extends BaseDAO{
  public Department getById(Integer id) {
    return get(Department.class, id);
  }

  public Department getByName(String name) {
    return getSession().createQuery("from Department where name = :name", Department.class)
      .setParameter("name", name)
      .getSingleResult();
  }

  public void save(Department department) {
    save(department);
  }
}
