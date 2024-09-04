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
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "livro_generator")
    private Long id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel = true;

    public Livro copyOf(Livro oLivro){
        long newId = oLivro.id != null ? oLivro.id : this.id;
        String newTitulo = oLivro.titulo != null ? oLivro.titulo : this.titulo;
        String newAutor = oLivro.autor != null ? oLivro.autor : this.autor;
        int newAno = oLivro.anoPublicacao != this.anoPublicacao ? oLivro.anoPublicacao : this.anoPublicacao;
        boolean newDisponivel = oLivro.disponivel != this.disponivel ? oLivro.disponivel : this.disponivel;

        return new Livro(newId, newTitulo, newAutor, newAno, newDisponivel);
    }
}
