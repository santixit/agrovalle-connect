package co.edu.uniajc.agrovalle.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SecurityConfiguration {

  private final SecretKey jwtKey;

  public SecurityConfiguration(@Value("${app.jwt.secret}") String secret) {
    byte[] bytes = java.util.Base64.getDecoder().decode(secret);
    if (bytes.length < 32) {
      throw new IllegalArgumentException("JWT_SECRET debe contener al menos 32 bytes en Base64");
    }
    this.jwtKey = new SecretKeySpec(bytes, "HmacSHA256");
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/index.html", "/favicon.ico", "/assets/**", "/styles.css")
                .permitAll()
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/productos")
                .hasAuthority("SCOPE_AGRICULTOR")
            .requestMatchers(org.springframework.http.HttpMethod.PATCH,
                "/api/v1/productos/*/estado").hasAuthority("SCOPE_AGRICULTOR")
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/contactos")
                .hasAuthority("SCOPE_COMPRADOR")
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/reservas")
                .hasAuthority("SCOPE_COMPRADOR")
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/reservas/carrito")
                .hasAuthority("SCOPE_COMPRADOR")
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/reservas/mias")
                .hasAuthority("SCOPE_COMPRADOR")
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/reservas/pendientes")
                .hasAuthority("SCOPE_AGRICULTOR")
            .requestMatchers(org.springframework.http.HttpMethod.POST,
                "/api/v1/reservas/*/confirmar", "/api/v1/reservas/*/preparar",
                "/api/v1/despachos")
                .hasAuthority("SCOPE_AGRICULTOR")
            .requestMatchers(org.springframework.http.HttpMethod.PATCH,
                "/api/v1/despachos/*/en-ruta", "/api/v1/despachos/*/entregado")
                .hasAuthority("SCOPE_AGRICULTOR")
            .requestMatchers(org.springframework.http.HttpMethod.GET,
                "/api/v1/reservas/*/trazabilidad").hasAuthority("SCOPE_COMPRADOR")
            .requestMatchers("/api/v1/favoritos/**").hasAuthority("SCOPE_COMPRADOR")
            .requestMatchers("/api/v1/admin/**").hasAuthority("SCOPE_ADMIN")
            .requestMatchers("/api/v1/fincas/**").hasAuthority("SCOPE_AGRICULTOR")
            .requestMatchers(org.springframework.http.HttpMethod.POST,
                "/api/v1/auth/register/comprador").permitAll()
            .requestMatchers("/api/v1/auth/**", "/api/v1/health", "/api/v1/productores/**",
                "/api/v1/productos/**", "/api/v1/precios/**").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()))
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withSecretKey(jwtKey).macAlgorithm(MacAlgorithm.HS256).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey));
  }
}
