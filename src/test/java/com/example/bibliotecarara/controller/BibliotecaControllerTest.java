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
public class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void createBiblioteca() throws Exception {
        String bibliotecaJson = "{\"nome\":\"Biblioteca teste\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/poo/biblioteca/").contentType(MediaType.APPLICATION_JSON).content(bibliotecaJson)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().json(bibliotecaJson));
    }

    @Test
    @Order(2)
    public void testAdicionarLivroComParametro() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Teste\",\"autor\":\"Autor Teste\",\"anoPublicacao\":2023,\"disponivel\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/poo/biblioteca/1/livro").param("idLivro", "1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Order(3)
    public void testAdicionarLivroComBody() throws Exception {
        String livroJson = "{\"titulo\":\"Livro Teste\",\"autor\":\"Autor Teste\",\"anoPublicacao\":2023,\"disponivel\":true}";

        mockMvc.perform(MockMvcRequestBuilders.put("/poo/biblioteca/1/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void testRemoverLivro() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/biblioteca/1/livro/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(5)
    public void testConsultarLivroPorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/livro/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(6)
    public void testConsultarLivroPorTitulo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/livro/porTitulo")
                        .param("titulo", "Livro Teste"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.autor").value("Autor Teste"));
    }

    @Test
    @Order(7)
    public void testAdicionarEmprestimo() throws Exception {
        String emprestimoJson = "{\"livroId\":1,\"usuarioId\":1,\"dataEmprestimo\":\"2023-06-26\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/biblioteca/1/emprestimo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emprestimoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("CREATED"));
    }

    @Test
    @Order(8)
    public void testRemoverEmprestimo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/biblioteca/1/emprestimo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("ACCEPTED"));
    }

    @Test
    @Order(9)
    public void testConsultarEmprestimoPorIdLivroIdUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/emprestimo/porIdLivroIdUsuario")
                        .param("livroId", "1")
                        .param("usuarioId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataEmprestimo").exists());
    }

    @Test
    @Order(10)
    public void testConsultarEmprestimoPorNomeLivroRAUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/emprestimo/porNomeLivroRAUsuario")
                        .param("nomeLivro", "Livro Teste")
                        .param("RA", "123456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataEmprestimo").exists());
    }

    @Test
    @Order(11)
    public void testConsultarEmprestimoPorNomeLivroEmailUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/emprestimo/porNomeLivroEmailUsuario")
                        .param("nomeLivro", "Livro Teste")
                        .param("email", "usuario@teste.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataEmprestimo").exists());
    }

    @Test
    @Order(12)
    public void testAdicionarReserva() throws Exception {
        String reservaJson = "{\"livroId\":1,\"usuarioId\":1,\"dataReserva\":\"2023-06-26\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/poo/biblioteca/1/reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("CREATED"));
    }

    @Test
    @Order(13)
    public void testRemoverReserva() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/poo/biblioteca/1/reserva/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("ACCEPTED"));
    }

    @Test
    @Order(14)
    public void testConsultarReservaPorIdLivroIdUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/reserva/porIdLivroIdUsuario")
                        .param("livroId", "1")
                        .param("usuarioId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataReserva").exists());
    }

    @Test
    @Order(15)
    public void testConsultarReservaPorNomeLivroRAUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/reserva/porNomeLivroRAUsuario")
                        .param("nomeLivro", "Livro Teste")
                        .param("RA", "123456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataReserva").exists());
    }

    @Test
    @Order(16)
    public void testConsultarReservaPorNomeLivroEmailUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poo/biblioteca/1/reserva/porNomeLivroEmailUsuario")
                        .param("nomeLivro", "Livro Teste")
                        .param("email", "usuario@teste.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataReserva").exists());
    }
}