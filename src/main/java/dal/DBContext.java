package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBContext {
  private Connection connection;
  private final Logger logger = Logger.getLogger(DBContext.class.getName());

  private static DBContext instance = null;

  private DBContext() {
    try {
      // TODO: replace below with your own SQL Server database URL, username, and
      // password
      String user = "your-user";
      String password = "your-password";
      String dbName = "AuthDemo";
      String serverName = "your-server-name";
      int portNumber = 12345;

      String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true",
          serverName, portNumber, dbName);

      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      connection = DriverManager.getConnection(url, user, password);

      logger.log(Level.INFO, "Connect to SQL Server successfully!");

    } catch (Exception ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

  public static DBContext getInstance() {
    if (instance == null) {
      instance = new DBContext();
    }
    return instance;
  }

  public Connection getConnection() {
    return connection;
  }
}