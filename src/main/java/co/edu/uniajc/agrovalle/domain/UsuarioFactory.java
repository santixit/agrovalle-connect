package co.edu.uniajc.agrovalle.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Creates credential-bearing accounts consistently for the supported user roles. */
@Component
public class UsuarioFactory {
  private final PasswordEncoder passwordEncoder;

  public UsuarioFactory(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public Usuario crearAgricultor(String correo, String contrasena) {
    return crear(correo, contrasena, RolUsuario.AGRICULTOR);
  }

  public Usuario crearComprador(String correo, String contrasena) {
    return crear(correo, contrasena, RolUsuario.COMPRADOR);
  }

  public Usuario crearAdministrador(String correo, String contrasena) {
    return crear(correo, contrasena, RolUsuario.ADMIN);
  }

  private Usuario crear(String correo, String contrasena, RolUsuario rol) {
    return new Usuario(correo, passwordEncoder.encode(contrasena), rol);
  }
}
