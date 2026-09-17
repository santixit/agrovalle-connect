package co.edu.uniajc.agrovalle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Verifies that the Spring context and readiness endpoint work. */
@SpringBootTest
@AutoConfigureMockMvc
class AgrovalleConnectApplicationTests {
  @Autowired private MockMvc mockMvc;

  @Test
  void contextLoads() { }

  @Test
  void healthEndpointReportsServiceReadiness() throws Exception {
    mockMvc.perform(get("/api/v1/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("UP"))
        .andExpect(jsonPath("$.service").value("agrovalle-connect"));
  }
}
