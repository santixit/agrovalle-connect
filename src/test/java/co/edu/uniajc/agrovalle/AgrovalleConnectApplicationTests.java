package co.edu.uniajc.agrovalle;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.TransaccionPrecio;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.CompradorRepository;
import co.edu.uniajc.agrovalle.repository.ContactoRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import co.edu.uniajc.agrovalle.repository.TransaccionPrecioRepository;
import co.edu.uniajc.agrovalle.repository.PedidoRepository;
import co.edu.uniajc.agrovalle.repository.EventoTrazabilidadRepository;
import co.edu.uniajc.agrovalle.repository.NotificacionRepository;
import co.edu.uniajc.agrovalle.repository.DespachoRepository;
import co.edu.uniajc.agrovalle.repository.FavoritoRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

/** Verifies the Sprint 1 REST scenarios against the test database. */
@AutoConfigureMockMvc
@SpringBootTest
class AgrovalleConnectApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AgricultorRepository agricultorRepository;

  @Autowired
  private ProductoRepository productoRepository;

  @Autowired
  private CompradorRepository compradorRepository;

  @Autowired
  private ContactoRepository contactoRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TransaccionPrecioRepository transaccionPrecioRepository;

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private EventoTrazabilidadRepository eventoTrazabilidadRepository;

  @Autowired
  private NotificacionRepository notificacionRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private DespachoRepository despachoRepository;

  @Autowired
  private FavoritoRepository favoritoRepository;

  @Autowired
  private FincaRepository fincaRepository;

  @BeforeEach
  void limpiarDatos() {
    despachoRepository.deleteAll();
    eventoTrazabilidadRepository.deleteAll();
    favoritoRepository.deleteAll();
    pedidoRepository.deleteAll();
    transaccionPrecioRepository.deleteAll();
    notificacionRepository.deleteAll();
    contactoRepository.deleteAll();
    productoRepository.deleteAll();
    fincaRepository.deleteAll();
    agricultorRepository.deleteAll();
    compradorRepository.deleteAll();
    usuarioRepository.deleteAll();
  }

  @Test
  void registraAgricultorYDevuelveCreated() throws Exception {
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Ana Ruiz","cedula":"123456","ubicacion_valle":"Dagua"}
                """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.nombre").value("Ana Ruiz"));
  }

  @Test
  void rechazaCedulaDuplicada() throws Exception {
    String request = """
        {"nombre":"Ana Ruiz","cedula":"123456","ubicacion_valle":"Dagua"}
        """;
    String duplicateWithWhitespace = request.replace("123456", " 123456 ");
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(duplicateWithWhitespace))
        .andExpect(status().isCreated());
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isConflict());
  }

  @Test
  void rechazaDatosObligatoriosVacios() throws Exception {
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"","cedula":"","ubicacion_valle":""}
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void consultaPerfilDeAgricultorRegistrado() throws Exception {
    String response = mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Ana Ruiz","cedula":"123456","ubicacion_valle":"Dagua"}
                """))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    Number id = com.jayway.jsonpath.JsonPath.read(response, "$.id");

    mockMvc.perform(get("/api/v1/productores/{id}", id.longValue()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nombre").value("Ana Ruiz"))
        .andExpect(jsonPath("$.ubicacion_valle").value("Dagua"));
  }

  @Test
  void devuelveNotFoundParaPerfilInexistente() throws Exception {
    mockMvc.perform(get("/api/v1/productores/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void filtraOfertasPorMunicipioYCategoria() throws Exception {
    productoRepository.save(new Producto("Mango", "Frutas", "Dagua", true));
    productoRepository.save(new Producto("Platano", "Frutas", "Cali", true));
    productoRepository.save(new Producto("Cafe", "Granos", "Dagua", true));
    productoRepository.save(new Producto("Mango agotado", "Frutas", "Dagua", false));

    mockMvc.perform(get("/api/v1/productos")
            .param("municipio", "Dagua").param("categoria", "Frutas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].nombre").value("Mango"));
  }

  @Test
  void catalogoSinCoincidenciasDevuelveColeccionVacia() throws Exception {
    mockMvc.perform(get("/api/v1/productos")
            .param("municipio", "Dagua").param("categoria", "Frutas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void catalogoSinFiltrosDevuelveTodasLasOfertasActivas() throws Exception {
    productoRepository.save(new Producto("Mango", "Frutas", "Dagua", true));
    productoRepository.save(new Producto("Cafe", "Granos", "Cali", true));
    productoRepository.save(new Producto("Platano agotado", "Frutas", "Dagua", false));

    mockMvc.perform(get("/api/v1/productos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void readinessEndpointIndicaServicioDisponible() throws Exception {
    mockMvc.perform(get("/api/v1/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("UP"))
        .andExpect(jsonPath("$.service").value("agrovalle-connect"));
  }

  @Test
  void respuestaDePerfilNoExponeLaCedula() throws Exception {
    String response = mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Ana Ruiz","cedula":"123456","ubicacion_valle":"Dagua"}
                """))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    Number id = com.jayway.jsonpath.JsonPath.read(response, "$.id");

    mockMvc.perform(get("/api/v1/productores/{id}", id.longValue()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cedula").doesNotExist());
  }

  @Test
  void interfazWebSeSirveSinAutenticar() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
    mockMvc.perform(get("/index.html"))
        .andExpect(status().isOk())
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
            .content().contentTypeCompatibleWith("text/html"));
  }

  @Test
  void cuentaAgricultorPuedeAutenticarseYPublicarOferta() throws Exception {
    String alta = """
        {"nombre":"Rosa Díaz","cedula":"998877","ubicacion_valle":"Dagua",
         "correo":"rosa@example.com","contrasena":"ClaveSegura2026"}
        """;
    mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
            .content(alta))
        .andExpect(status().isCreated());
    String tokenResponse = mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"correo":"rosa@example.com","contrasena":"ClaveSegura2026"}
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.tokenType").value("Bearer"))
        .andReturn().getResponse().getContentAsString();
    String token = com.jayway.jsonpath.JsonPath.read(tokenResponse, "$.accessToken");

    mockMvc.perform(post("/api/v1/productos").header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Mango","categoria":"Frutas","municipio":"Dagua",
                 "cantidadKg":35,"precioPorKg":4200,"fecha_cosecha":"2026-09-29"}
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.cantidad_kg").value(35))
        .andExpect(jsonPath("$.precio_por_kg").value(4200));
  }

  @Test
  void compradorAutenticadoPuedeRegistrarContactoDeOferta() throws Exception {
    String altaAgricultor = """
        {"nombre":"Rosa Díaz","cedula":"998877","ubicacion_valle":"Dagua",
         "correo":"rosa@example.com","contrasena":"ClaveSegura2026"}
        """;
    mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
            .content(altaAgricultor)).andExpect(status().isCreated());
    String tokenAgricultor = token("rosa@example.com", "ClaveSegura2026");
    String producto = mockMvc.perform(post("/api/v1/productos")
            .header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Mango","categoria":"Frutas","municipio":"Dagua",
                 "cantidadKg":35,"precioPorKg":4200,"fecha_cosecha":"2026-09-29"}
                """))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    Number productoId = com.jayway.jsonpath.JsonPath.read(producto, "$.id");
    mockMvc.perform(post("/api/v1/auth/register/comprador")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Restaurante Valle","correo":"compras@example.com",
                 "contrasena":"OtraClave2026","tipoComercio":"Restaurante"}
                """))
        .andExpect(status().isCreated());
    String tokenComprador = token("compras@example.com", "OtraClave2026");

    mockMvc.perform(post("/api/v1/contactos").header("Authorization", "Bearer " + tokenComprador)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productoId\":" + productoId + ",\"mensaje\":\"Deseo comprar\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.estado").value("NUEVO"));
    mockMvc.perform(get("/api/v1/notificaciones/mias")
            .header("Authorization", "Bearer " + tokenAgricultor))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].tipo").value("CONTACTO"));
  }

  @Test
  void consultaPromedioDePreciosUsaTransaccionesGuardadas() throws Exception {
    Producto cafe = productoRepository.save(new Producto("Cafe", "Granos", "Dagua", true));
    TransaccionPrecio antigua = new TransaccionPrecio(cafe,
        new BigDecimal("50"), new BigDecimal("5000"));
    ReflectionTestUtils.setField(antigua, "ocurridaEn", java.time.LocalDateTime.now()
        .minusHours(25));
    transaccionPrecioRepository.save(antigua);
    transaccionPrecioRepository.save(new TransaccionPrecio(cafe,
        new BigDecimal("10"), new BigDecimal("8000")));
    transaccionPrecioRepository.save(new TransaccionPrecio(cafe,
        new BigDecimal("5"), new BigDecimal("10000")));

    mockMvc.perform(get("/api/v1/precios/regionales").param("categoria", "Granos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.promedioPorKg").value(9000))
        .andExpect(jsonPath("$.transaccionesAnalizadas").value(2))
        .andExpect(jsonPath("$.moneda").value("COP"));
  }

  @Test
  void publicacionDeOfertaExigeAutenticacion() throws Exception {
    mockMvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void reservaDescuentaInventarioYRechazaExceso() throws Exception {
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Rosa Díaz","cedula":"998877","ubicacion_valle":"Dagua",
                 "correo":"rosa@example.com","contrasena":"ClaveSegura2026"}
                """))
        .andExpect(status().isCreated());
    String tokenAgricultor = token("rosa@example.com", "ClaveSegura2026");
    String response = mockMvc.perform(post("/api/v1/productos")
            .header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Mango","categoria":"Frutas","municipio":"Dagua",
                 "cantidadKg":35,"precioPorKg":4200,"fecha_cosecha":"2026-09-29"}
                """))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    Number productoId = com.jayway.jsonpath.JsonPath.read(response, "$.id");
    mockMvc.perform(post("/api/v1/auth/register/comprador")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Restaurante Valle","correo":"compras@example.com",
                 "contrasena":"OtraClave2026"}
                """))
        .andExpect(status().isCreated());
    String tokenComprador = token("compras@example.com", "OtraClave2026");

    mockMvc.perform(post("/api/v1/reservas").header("Authorization", "Bearer " + tokenComprador)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productoId\":" + productoId + ",\"cantidad_kg\":20}"))
        .andExpect(status().isCreated());
    mockMvc.perform(get("/api/v1/productos/{id}", productoId.longValue()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cantidad_disponible_kg").value(15));
    mockMvc.perform(post("/api/v1/reservas").header("Authorization", "Bearer " + tokenComprador)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productoId\":" + productoId + ",\"cantidad_kg\":20}"))
        .andExpect(status().isConflict());
  }

  @Test
  void fincaOfertaReservaDespachoTrazabilidadYFavoritos() throws Exception {
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Rosa Díaz","cedula":"998877","ubicacion_valle":"Dagua",
                 "correo":"rosa@example.com","contrasena":"ClaveSegura2026"}
                """))
        .andExpect(status().isCreated());
    String tokenAgricultor = token("rosa@example.com", "ClaveSegura2026");
    String finca = mockMvc.perform(post("/api/v1/fincas")
            .header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"La Esperanza","municipio":"Dagua","direccion":"Vereda El Salado"}
                """))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    Number fincaId = com.jayway.jsonpath.JsonPath.read(finca, "$.id");
    String oferta = mockMvc.perform(post("/api/v1/productos")
            .header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Mango","categoria":"Frutas","municipio":"Dagua",
                 "cantidadKg":35,"precioPorKg":4200,"fecha_cosecha":"2026-09-29",
                 "fincaId":%d}
                """.formatted(fincaId.longValue())))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    Number productoId = com.jayway.jsonpath.JsonPath.read(oferta, "$.id");
    mockMvc.perform(get("/api/v1/productos/{id}", productoId.longValue()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.agricultor").value("Rosa Díaz"))
        .andExpect(jsonPath("$.finca").value("La Esperanza"))
        .andExpect(jsonPath("$.cedula").doesNotExist());
    mockMvc.perform(patch("/api/v1/productos/{id}/estado", productoId.longValue())
            .header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON).content("{\"estado\":\"PAUSADO\"}"))
        .andExpect(status().isOk());
    mockMvc.perform(patch("/api/v1/productos/{id}/estado", productoId.longValue())
            .header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON).content("{\"estado\":\"DISPONIBLE\"}"))
        .andExpect(status().isOk());
    mockMvc.perform(post("/api/v1/auth/register/comprador")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"nombre":"Restaurante Valle","correo":"compras@example.com",
                 "contrasena":"OtraClave2026"}
                """))
        .andExpect(status().isCreated());
    String tokenComprador = token("compras@example.com", "OtraClave2026");
    mockMvc.perform(post("/api/v1/favoritos/{id}", productoId.longValue())
            .header("Authorization", "Bearer " + tokenComprador))
        .andExpect(status().isNoContent());
    mockMvc.perform(get("/api/v1/favoritos")
            .header("Authorization", "Bearer " + tokenComprador))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    String reserva = mockMvc.perform(post("/api/v1/reservas")
            .header("Authorization", "Bearer " + tokenComprador)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productoId\":" + productoId + ",\"cantidad_kg\":20}"))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    Number pedidoId = com.jayway.jsonpath.JsonPath.read(reserva, "$.id");
    mockMvc.perform(get("/api/v1/reservas/pendientes")
            .header("Authorization", "Bearer " + tokenAgricultor))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    mockMvc.perform(get("/api/v1/reservas/mias")
            .header("Authorization", "Bearer " + tokenComprador))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    mockMvc.perform(post("/api/v1/reservas/{id}/confirmar", pedidoId.longValue())
            .header("Authorization", "Bearer " + tokenAgricultor))
        .andExpect(status().isNoContent());
    mockMvc.perform(post("/api/v1/despachos").header("Authorization", "Bearer " + tokenAgricultor)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"pedidoId":%d,"fecha_programada":"2026-09-29",
                 "franjaHoraria":"8:00-12:00","ruta":"Dagua-Cali"}
                """.formatted(pedidoId.longValue())))
        .andExpect(status().isCreated());
    mockMvc.perform(get("/api/v1/reservas/{id}/trazabilidad", pedidoId.longValue())
            .header("Authorization", "Bearer " + tokenComprador))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[2].estado").value("EN_DESPACHO"));
    mockMvc.perform(patch("/api/v1/despachos/{id}/entregado", pedidoId.longValue())
            .header("Authorization", "Bearer " + tokenAgricultor))
        .andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("ENTREGADO"));
    mockMvc.perform(get("/api/v1/reservas/{id}/trazabilidad", pedidoId.longValue())
            .header("Authorization", "Bearer " + tokenComprador))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[3].estado").value("ENTREGADO"));
    mockMvc.perform(get("/api/v1/precios/regionales").param("categoria", "Frutas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.promedioPorKg").value(4200))
        .andExpect(jsonPath("$.transaccionesAnalizadas").value(1));
  }

  @Test
  void administradorPuedeSolicitarReporteConRangoValido() throws Exception {
    usuarioRepository.save(new Usuario("admin@example.com",
        passwordEncoder.encode("AdminClave2026"), RolUsuario.ADMIN));
    String tokenAdmin = token("admin@example.com", "AdminClave2026");
    mockMvc.perform(get("/api/v1/admin/reportes/actividad")
            .param("desde", "2026-09-29").param("hasta", "2026-09-29")
            .header("Authorization", "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.usuariosRegistrados").value(1));
  }

  private String token(String correo, String contrasena) throws Exception {
    String response = mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"correo\":\"" + correo + "\",\"contrasena\":\""
                + contrasena + "\"}"))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    return com.jayway.jsonpath.JsonPath.read(response, "$.accessToken");
  }
}
