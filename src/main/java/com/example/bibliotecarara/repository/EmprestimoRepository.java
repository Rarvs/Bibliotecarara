package com.example.bibliotecarara.repository;

import com.example.bibliotecarara.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Optional<Emprestimo> findByLivroIdAndUsuarioId(long livroId, long usarioId);
}