package com.example.bibliotecarara.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livro extends WithCopy<Livro> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private Long bibliotecaId = null;
    private boolean disponivel = true;


    @Override
    public void updateWith(Livro copy) {
        setTitulo(copy.getTitulo() != null ? copy.getTitulo() : this.getTitulo());
        setAutor(copy.getAutor() != null ? copy.getAutor() : this.getAutor());
        setAnoPublicacao(copy.getAnoPublicacao() != this.getAnoPublicacao() ? copy.getAnoPublicacao() : this.getAnoPublicacao());
        setDisponivel(copy.isDisponivel() != this.isDisponivel() ? copy.isDisponivel() :  this.isDisponivel());
        setBibliotecaId(copy.getBibliotecaId() != null ? copy.getBibliotecaId() : this.getBibliotecaId());
    }

}