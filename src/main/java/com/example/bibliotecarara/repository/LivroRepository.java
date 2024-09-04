package com.example.bibliotecarara.repository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Livro findFirstByTitulo(String s);
    List<Livro> findAll();
    List<Livro> findAllByTitulo(String s);
}
