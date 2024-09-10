package com.example.bibliotecarara.repository;

import com.example.bibliotecarara.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findFirstByRA(String ra);
    Usuario findFirstByEmail(String email);
    Usuario deleteUsuarioByEmail(String email);
    Usuario deleteUsuarioByRA(String ra);
}