package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.LoginRequest;
import co.edu.uniajc.agrovalle.api.dto.TokenResponse;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService {

  private static final long DURACION_HORAS = 2;
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtEncoder jwtEncoder;

  public AutenticacionService(UsuarioRepository usuarioRepository,
      PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtEncoder = jwtEncoder;
  }

  public TokenResponse iniciarSesion(LoginRequest request) {
    Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(request.correo().trim())
        .filter(Usuario::isActivo)
        .filter(cuenta -> passwordEncoder.matches(request.contrasena(), cuenta.getPasswordHash()))
        .orElseThrow(() -> new BadCredentialsException("Correo o contraseña incorrectos"));
    Instant ahora = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("agrovalle-connect")
        .issuedAt(ahora)
        .expiresAt(ahora.plus(DURACION_HORAS, ChronoUnit.HOURS))
        .subject(usuario.getId().toString())
        .claim("scope", usuario.getRol().name())
        .claim("correo", usuario.getCorreo())
        .build();
    String token = jwtEncoder.encode(JwtEncoderParameters.from(
        JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    return new TokenResponse(token, "Bearer", DURACION_HORAS * 3600);
  }
}
