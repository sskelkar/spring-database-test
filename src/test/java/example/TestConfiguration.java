package example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan({"example"})
public class TestConfiguration {
  @Bean
  @Conditional(ForUsingH2.class)
  public DatabaseProperties h2DatabaseProperties() {
    return new DatabaseProperties("org.h2.Driver", "jdbc:h2:mem:ia", "org.hibernate.dialect.H2Dialect", true);
  }

  @Bean
  @Conditional(ForUsingMySQL.class)
  public DatabaseProperties mysqlDatabaseProperties() {
    MySQLContainer mysql = new MySQLContainer("mysql:5.6.42");
    mysql.withInitScript("database.sql").start();
    return new DatabaseProperties(mysql.getDriverClassName(), mysql.getJdbcUrl(), "org.hibernate.dialect.MySQL5InnoDBDialect", false);
  }

  @Bean
  public DataSource dataSource(DatabaseProperties databaseProperties) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(databaseProperties.getDriverClass());
    hikariConfig.setJdbcUrl(databaseProperties.getJdbcUrl());
    hikariConfig.setUsername(databaseProperties.getUsername());
    hikariConfig.setPassword(databaseProperties.getPassword());

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public SessionFactory sessionFactory(DataSource dataSource, DatabaseProperties databaseProperties) throws IOException {
    LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
    localSessionFactoryBean.setDataSource(dataSource);
    localSessionFactoryBean.setPackagesToScan("example.model");
    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.dialect", databaseProperties.getDialect());
    hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");
    hibernateProperties.setProperty("hibernate.cache.use_minimal_puts", "true");
    hibernateProperties.setProperty("hibernate.jdbc.batch_size", "20");
    hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
    if (databaseProperties.createSchemaAutomatically()) {
      hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
    }
    localSessionFactoryBean.setHibernateProperties(hibernateProperties);
    localSessionFactoryBean.afterPropertiesSet();
    return localSessionFactoryBean.getObject();
  }

  @Bean
  public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }

  private static class ForUsingMySQL implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      Environment env = context.getEnvironment();
      return "mysql".equals(env.getProperty("testDB"));
    }
  }

  private static class ForUsingH2 implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      Environment env = context.getEnvironment();
      return !"mysql".equals(env.getProperty("testDB"));
    }
  }
}
