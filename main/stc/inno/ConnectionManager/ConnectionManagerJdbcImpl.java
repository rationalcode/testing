package stc.inno.ConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerJdbcImpl implements ConnectionManager {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManagerJdbcImpl.class);
  public static final ConnectionManager INSTANCE = new ConnectionManagerJdbcImpl();

  private ConnectionManagerJdbcImpl() {
  }

  public static ConnectionManager getInstance() {
    return INSTANCE;
  }

  @Override
  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(
              "jdbc:postgresql://localhost:5434/jdbcDB",
              "postgres",
              "qwerty");
    } catch (SQLException e) {
      LOGGER.error("Some thing wrong in getConnection method", e);
    }
    return connection;
  }

  @Override public int get15() {
    return 15;
  }
}
