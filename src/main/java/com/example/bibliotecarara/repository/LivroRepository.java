package com.example.bibliotecarara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bibliotecarara.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Livro findFirstByTitulo(String s);
    List<Livro> findAll();
    List<Livro> findAllByTitulo(String s);
}
