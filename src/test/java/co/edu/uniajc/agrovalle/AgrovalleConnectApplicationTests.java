package co.edu.uniajc.agrovalle;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

  @BeforeEach
  void limpiarDatos() {
    productoRepository.deleteAll();
    agricultorRepository.deleteAll();
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
}
