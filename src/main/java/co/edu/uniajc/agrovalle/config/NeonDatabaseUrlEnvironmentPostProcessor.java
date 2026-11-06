package co.edu.uniajc.agrovalle.config;

import java.net.URI;
import java.net.URLDecoder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

/** Maps Neon connection URLs to the JDBC settings used by Spring Boot. */
public class NeonDatabaseUrlEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
  private static final String DATABASE_URL = "DATABASE_URL";
  private static final String DATABASE_URL_UNPOOLED = "DATABASE_URL_UNPOOLED";

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    Map<String, Object> localVariables = loadLocalEnvironment(environment);
    if (!localVariables.isEmpty()) {
      environment.getPropertySources().addFirst(
          new MapPropertySource("neonLocalEnvironment", localVariables));
    }

    String databaseUrl = environment.getProperty(DATABASE_URL);
    if (databaseUrl == null || databaseUrl.isBlank()) {
      return;
    }

    Map<String, Object> properties = new LinkedHashMap<>();
    ConnectionSettings pooled = parseConnectionUrl(databaseUrl);
    // DATABASE_URL is authoritative when present. This also replaces legacy
    // DB_URL/DB_USERNAME/DB_PASSWORD values left behind by the previous host.
    properties.put("DB_URL", pooled.jdbcUrl());
    properties.put("DB_USERNAME", pooled.username());
    properties.put("DB_PASSWORD", pooled.password());

    String directUrl = environment.getProperty(DATABASE_URL_UNPOOLED);
    if (directUrl != null && !directUrl.isBlank()
        && environment.getProperty("spring.flyway.url") == null) {
      ConnectionSettings direct = parseConnectionUrl(directUrl);
      properties.put("spring.flyway.url", direct.jdbcUrl());
      if (environment.getProperty("spring.flyway.user") == null) {
        properties.put("spring.flyway.user", direct.username());
      }
      if (environment.getProperty("spring.flyway.password") == null) {
        properties.put("spring.flyway.password", direct.password());
      }
    }

    if (!properties.isEmpty()) {
      environment.getPropertySources().addFirst(
          new MapPropertySource("neonDatabaseUrl", properties));
    }
  }

  private static ConnectionSettings parseConnectionUrl(String value) {
    try {
      URI uri = URI.create(unquote(value));
      String scheme = uri.getScheme();
      if (!"postgresql".equalsIgnoreCase(scheme) && !"postgres".equalsIgnoreCase(scheme)) {
        throw new IllegalArgumentException("Expected a PostgreSQL connection URL.");
      }
      if (uri.getHost() == null || uri.getRawPath() == null || uri.getRawPath().length() < 2) {
        throw new IllegalArgumentException("The PostgreSQL connection URL is incomplete.");
      }

      String rawUserInfo = uri.getRawUserInfo();
      int separator = rawUserInfo == null ? -1 : rawUserInfo.indexOf(':');
      if (separator < 1) {
        throw new IllegalArgumentException("The PostgreSQL connection URL has no credentials.");
      }
      String username = decode(rawUserInfo.substring(0, separator));
      String password = decode(rawUserInfo.substring(separator + 1));
      String authority = uri.getHost() + (uri.getPort() < 0 ? "" : ":" + uri.getPort());
      String query = uri.getRawQuery();
      if (query == null || !query.toLowerCase().contains("sslmode=")) {
        query = query == null || query.isBlank() ? "sslmode=require" : query + "&sslmode=require";
      }
      String jdbcUrl = "jdbc:postgresql://" + authority + uri.getRawPath() + "?" + query;
      return new ConnectionSettings(jdbcUrl, username, password);
    } catch (IllegalArgumentException exception) {
      throw new IllegalStateException(
          "Could not configure the Neon PostgreSQL connection from DATABASE_URL. "
              + "Check that it is a valid PostgreSQL URL with credentials.");
    }
  }

  private static String decode(String value) {
    return URLDecoder.decode(value.replace("+", "%2B"), StandardCharsets.UTF_8);
  }

  private static Map<String, Object> loadLocalEnvironment(ConfigurableEnvironment environment) {
    Path envFile = Path.of(".env.local");
    if (!Files.isRegularFile(envFile)) {
      return Map.of();
    }

    Map<String, Object> variables = new LinkedHashMap<>();
    try {
      for (String line : Files.readAllLines(envFile, StandardCharsets.UTF_8)) {
        String trimmed = line.trim();
        int separator = trimmed.indexOf('=');
        if (trimmed.isEmpty() || trimmed.startsWith("#") || separator < 1) {
          continue;
        }
        String key = trimmed.substring(0, separator).trim();
        if (environment.getProperty(key) == null) {
          variables.put(key, unquote(trimmed.substring(separator + 1)));
        }
      }
    } catch (IOException exception) {
      throw new IllegalStateException("Could not read the local Neon environment file.");
    }
    return variables;
  }

  private static String unquote(String value) {
    String trimmed = value.trim();
    if (trimmed.length() >= 2) {
      char first = trimmed.charAt(0);
      char last = trimmed.charAt(trimmed.length() - 1);
      if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
        return trimmed.substring(1, trimmed.length() - 1);
      }
    }
    return trimmed;
  }

  private record ConnectionSettings(String jdbcUrl, String username, String password) { }
}
