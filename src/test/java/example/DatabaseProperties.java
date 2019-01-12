package example;

public class DatabaseProperties {
  private String driverClass;
  private String jdbcUrl;
  private String dialect;
  private String username;
  private String password;
  private boolean createSchemaAutomatically;

  public DatabaseProperties(String driverClass, String jdbcUrl, String dialect, boolean createSchemaAutomatically) {
    this.driverClass = driverClass;
    this.jdbcUrl = jdbcUrl;
    this.dialect = dialect;
    this.createSchemaAutomatically = createSchemaAutomatically;
    this.username = "test";
    this.password = "test";
  }

  public String getDriverClass() {
    return driverClass;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getDialect() {
    return dialect;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public boolean createSchemaAutomatically() {
    return createSchemaAutomatically;
  }
}