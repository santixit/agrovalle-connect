package co.edu.uniajc.agrovalle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/** Checks the staging database after the application has applied its Flyway migrations. */
class StagingDatabaseSmokeTest {
  private static final String EXPECTED_DATABASE = "agrovalle_staging";
  private static final String EXPECTED_FLYWAY_VERSION = "7";

  @Test
  void stagingDatabaseHasExpectedNameFlywayVersionAndCoreTables() {
    String jdbcUrl = System.getenv("STAGING_DB_URL");
    String username = System.getenv("STAGING_DB_USERNAME");
    String password = System.getenv("STAGING_DB_PASSWORD");

    boolean noConfiguration = isBlank(jdbcUrl) && isBlank(username) && isBlank(password);
    assumeTrue(!noConfiguration, "Staging credentials are only supplied by the staging workflow.");
    assertFalse(isBlank(jdbcUrl) || isBlank(username) || isBlank(password),
        "Configure STAGING_DB_URL, STAGING_DB_USERNAME, and STAGING_DB_PASSWORD together.");

    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
      try (Statement statement = connection.createStatement();
          ResultSet result = statement.executeQuery("SELECT current_database()")) {
        assertTrue(result.next(), "PostgreSQL should return the current database name.");
        assertEquals(EXPECTED_DATABASE, result.getString(1));
      }

      try (Statement statement = connection.createStatement();
          ResultSet result = statement.executeQuery("""
              SELECT version
              FROM flyway_schema_history
              WHERE success = TRUE
              ORDER BY installed_rank DESC
              LIMIT 1
              """)) {
        assertTrue(result.next(), "Flyway should have applied at least one migration.");
        assertEquals(EXPECTED_FLYWAY_VERSION, result.getString(1));
      }

      try (Statement statement = connection.createStatement();
          ResultSet result = statement.executeQuery("""
              SELECT to_regclass('public.agricultores'),
                     to_regclass('public.productos'),
                     to_regclass('public.fincas'),
                     to_regclass('public.parcelas')
              """)) {
        assertTrue(result.next(), "PostgreSQL should return the expected core tables.");
        assertNotNull(result.getString(1), "The agricultores table should exist.");
        assertNotNull(result.getString(2), "The productos table should exist.");
        assertNotNull(result.getString(3), "The fincas table should exist.");
        assertNotNull(result.getString(4), "The parcelas table should exist.");
      }
    } catch (SQLException exception) {
      fail("Staging database verification failed (SQLState " + exception.getSQLState()
          + "). Check the JDBC URL, credentials, SSL, network access, and Flyway state.");
    }
  }

  private static boolean isBlank(String value) {
    return value == null || value.isBlank();
  }
}
