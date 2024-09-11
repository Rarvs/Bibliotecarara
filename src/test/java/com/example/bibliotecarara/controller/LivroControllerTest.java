package com.example.bibliotecarara.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testCriarLivro() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Teste\",\"autor\":\"Autor Teste\",\"anoPublicacao\":2023,\"disponivel\":true}";
        String livroJson2 = "{\"titulo\":\"Livro Teste 2\",\"autor\":\"Autor Teste 2\",\"anoPublicacao\":2023,\"disponivel\":true}";

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(livroJson));

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson2))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(livroJson2));
    }

    @Test
    @Order(2)
    public void testConsultarLivroPorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/livro/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").exists());
    }

    @Test
    @Order(3)
    public void testConsultarLivroPorTitulo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/livro/porTitulo").param("titulo", "Livro Teste"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").exists());
    }

    @Test
    @Order(4)
    public void testEditarLivroPorId() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Editado\",\"autor\":\"Autor Editado\",\"anoPublicacao\":2022,\"disponivel\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/poo/livro/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().json(livroJson));
    }

    @Test
    @Order(5)
    public void testEditarLivroPorTitulo() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Reeditado\",\"autor\":\"Autor Editado\",\"anoPublicacao\":2022,\"disponivel\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/poo/livro/porTitulo").param("titulo", "Livro Editado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().json(livroJson));
    }

    @Test
    @Order(6)
    public void testExcluirLivro() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/livro/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Order(7)
    public void testExcluirPorTitulo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/livro/porTitulo").param("titulo", "Livro Teste 2"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Order(8)
    public void testConsultarLivroPorId_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/livro/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(9)
    public void testConsultarLivroPorTitulo_failure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/livro/porTitulo").param("titulo", "Livro Teste 2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    public void testEditarLivroPorId_failure() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Reeditado\",\"autor\":\"Autor Editado\",\"anoPublicacao\":2022,\"disponivel\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/poo/livro/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(11)
    public void testEditarLivroPorTitulo_failure() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Reeditado\",\"autor\":\"Autor Editado\",\"anoPublicacao\":2022,\"disponivel\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/poo/livro/porTitulo").param("titulo", "Livro Editado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}