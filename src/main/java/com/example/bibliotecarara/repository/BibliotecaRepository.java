package com.example.bibliotecarara.repository;

import com.example.bibliotecarara.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
    Optional<Biblioteca> findByLivrosContaining(Livro livro);
    Optional<Biblioteca> findByEmprestimosContaining(Emprestimo emprestimo);
}
