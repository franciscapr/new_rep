
package com.duoc.backend;

import com.duoc.backend.user.User;
import com.duoc.backend.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class PatientControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Repositorio simulado para evitar depender
    // de usuarios reales de MySQL.
    @MockitoBean
    private UserRepository userRepository;

    // PASO 1: Preparar el usuario de prueba
    @BeforeEach
    void configurarUsuarioPrueba() {

        User usuario = new User();

        usuario.setUsername("prueba");
        usuario.setPassword(
            passwordEncoder.encode("123456")
        );
        usuario.setEnabled(true);

        when(userRepository.findByUsername("prueba"))
            .thenReturn(usuario);
    }

    // PASO 2: Iniciar sesión y obtener el JWT
    private String obtenerToken() {

        String loginUrl =
            "http://localhost:" + port + "/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String loginBody = """
            {
                "username": "prueba",
                "password": "123456"
            }
            """;

        HttpEntity<String> request =
            new HttpEntity<>(loginBody, headers);

        ResponseEntity<String> response =
            restTemplate.postForEntity(
                loginUrl,
                request,
                String.class
            );

        assertEquals(
            HttpStatus.OK,
            response.getStatusCode(),
            "El login debe devolver 200 OK"
        );

        String token = response.getBody();

        assertNotNull(token, "El token no debe ser nulo");

        assertTrue(
            token.startsWith("Bearer "),
            "El token debe comenzar con Bearer"
        );

        return token;
    }

    // PASO 3: Consultar pacientes usando el JWT
    private ResponseEntity<String> consultarPacientes(
        String bearerToken
    ) {

        String patientUrl =
            "http://localhost:" + port + "/patient";

        HttpHeaders headers = new HttpHeaders();

        headers.set(
            HttpHeaders.AUTHORIZATION,
            bearerToken
        );

        HttpEntity<Void> request =
            new HttpEntity<>(headers);

        return restTemplate.exchange(
            patientUrl,
            HttpMethod.GET,
            request,
            String.class
        );
    }

    // PRUEBA 1: Login y consulta de pacientes
    @Test
    void testLoginAndGetAllPatients() {

        String bearerToken = obtenerToken();

        ResponseEntity<String> response =
            consultarPacientes(bearerToken);

        assertEquals(
            HttpStatus.OK,
            response.getStatusCode()
        );

        assertNotNull(response.getBody());
    }

    // PRUEBA 2: Petición con cabecera Bearer
    @Test
    void testGetAllPatientsWithBearerToken() {

        String bearerToken = obtenerToken();

        ResponseEntity<String> response =
            consultarPacientes(bearerToken);

        assertEquals(
            HttpStatus.OK,
            response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertTrue(
            response.getBody().trim().startsWith("["),
            "La respuesta debe ser una lista JSON"
        );
    }

    // PRUEBA 3: Acceso mediante MockMvc
    @WithMockUser(
        username = "testuser",
        roles = {"USER"}
    )
    @Test
    void getMvc() throws Exception {

        mvc.perform(get("/patient"))
            .andExpect(status().isOk());
    }
}
