package example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan({"example"})
public class TestConfiguration {

  @Bean
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName("org.h2.Driver");
    hikariConfig.setJdbcUrl("jdbc:h2:mem:example");
    hikariConfig.setUsername("test");
    hikariConfig.setPassword("test");

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
    LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
    localSessionFactoryBean.setDataSource(dataSource);
    localSessionFactoryBean.setPackagesToScan("example.model");
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    properties.setProperty("hibernate.cache.use_query_cache", "false");
    properties.setProperty("hibernate.cache.use_minimal_puts", "true");
    properties.setProperty("hibernate.jdbc.batch_size", "20");
    properties.setProperty("hibernate.cache.use_second_level_cache", "false");
    properties.setProperty("hibernate.hbm2ddl.auto", "create");
    localSessionFactoryBean.setHibernateProperties(properties);
    localSessionFactoryBean.afterPropertiesSet();
    return localSessionFactoryBean.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }
}