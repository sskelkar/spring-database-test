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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan({"example"})
@EnableTransactionManagement
public class TestConfiguration {

  @Bean
  public DataSource dataSource() {
    MySQLContainer mysql = new MySQLContainer("mysql:5.6.42");
    mysql.withInitScript("database.sql").start();
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(mysql.getDriverClassName());
    hikariConfig.setJdbcUrl(mysql.getJdbcUrl());
    hikariConfig.setUsername(mysql.getUsername());
    hikariConfig.setPassword(mysql.getPassword());

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
    LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
    localSessionFactoryBean.setDataSource(dataSource);
    localSessionFactoryBean.setPackagesToScan("example.model");
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
    properties.setProperty("hibernate.cache.use_query_cache", "false");
    properties.setProperty("hibernate.cache.use_minimal_puts", "true");
    properties.setProperty("hibernate.jdbc.batch_size", "20");
    properties.setProperty("hibernate.cache.use_second_level_cache", "false");
    localSessionFactoryBean.setHibernateProperties(properties);
    localSessionFactoryBean.afterPropertiesSet();
    return localSessionFactoryBean.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }
}
