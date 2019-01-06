package example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public abstract class BaseDAO {
  @Resource
  private SessionFactory sessionFactory;

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  protected <T> T get(Class<T> clazz, Integer id) {
    return getSession().get(clazz, id);
  }

  protected void save(Object entity) {
    getSession().persist(entity);
  }

  protected void delete(Object entity) {
    getSession().delete(entity);
  }
}
