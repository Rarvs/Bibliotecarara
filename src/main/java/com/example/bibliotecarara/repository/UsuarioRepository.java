package com.example.bibliotecarara.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bibliotecarara.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findFirstByRA(String ra);
    Usuario findFirstByEmail(String email);
    Usuario findFirstByNome(String nome);
    Usuario deleteUsuarioByEmail(String email);
    Usuario deleteUsuarioByRA(String ra);
}