package com.example.bibliotecarara.controller;

import com.mysql.cj.xdevapi.JsonParser;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.json.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testInserirUsuario() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Teste\",\"email\":\"teste@example.com\"}";
        String usuarioJson2 = "{\"ra\":\"123457\",\"nome\":\"Usuário Teste\",\"email\":\"teste1@example.com\"}";
        String usuarioJson3 = "{\"ra\":\"123458\",\"nome\":\"Usuário Teste\",\"email\":\"teste2@example.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(usuarioJson));

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson2))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(usuarioJson2));

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson3))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(usuarioJson3));

    }

    @Test
    @Order(2)
    public void testConsultarUsuarioPorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/usuario/1" ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").exists());
    }

    @Test
    @Order(3)
    public void testConsultarUsuarioPorRA() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/usuario/porRA")
                        .param("ra", "123456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@example.com"));
    }

    @Test
    @Order(4)
    public void testConsultarUsuarioPorEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/usuario/porEmail")
                        .param("email", "teste@example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@example.com"));
    }

    @Test
    @Order(5)
    public void testEditarUsuarioPorId() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Editado\",\"email\":\"editado@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/poo/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().json(usuarioJson));
    }

    @Test
    @Order(6)
    public void testEditarUsuarioPorRA() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Reeditado\",\"email\":\"editado@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/poo/usuario/porRA").param("ra","123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().json(usuarioJson));
    }

    @Test
    @Order(7)
    public void testEditarUsuarioPorEmail() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Editado\",\"email\":\"editadonovamente@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/poo/usuario/porEmail").param("email","editado@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().json(usuarioJson));
    }

    @Test
    @Order(8)
    public void testRemoverUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/usuario/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Order(8)
    public void testRemoverUsuarioPorRA() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/usuario/porRA").param("ra", "123457"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Order(8)
    public void testRemoverUsuarioPorEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/usuario/porEmail").param("email", "teste2@example.com"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Order(9)
    public void testConsultarUsuarioPorId_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/usuario/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    public void testConsultarUsuarioPorEmail_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/usuario/porEmail").param("email", "no@example.com"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(11)
    public void testConsultarUsuarioPorRA_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/usuario/porEmail").param("ra", "00000"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(12)
    public void testEditarUsuarioPorId_failure() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Editado\",\"email\":\"editado@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/poo/usuario/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(13)
    public void testEditarUsuarioPorEmail_failure() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Editado\",\"email\":\"editado@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/poo/usuario/porEmail").param("email", "email@invalido.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(14)
    public void testEditarUsuarioPorRA_failure() throws Exception {
        String usuarioJson = "{\"ra\":\"123456\",\"nome\":\"Usuário Editado\",\"email\":\"editado@example.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/poo/usuario/porEmail").param("ra", "inexistente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(15)
    public void testRemoverUsuarioPorRA_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/usuario/porRA").param("ra", "inexistente"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(16)
    public void testRemoverUsuarioPorEmail_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/usuario/porEmail").param("email", "email@invalido.com"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}