package com.example.bibliotecarara.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Emprestimo extends WithCopy<Emprestimo>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long livroId;
    private Long usuarioId;
    private Long bibliotecaId;
    private Date dataEmprestimo = Date.from(Instant.now());

    public Emprestimo(long livroId, long usuarioId, long bibliotecaId){
        this.livroId = livroId;
        this.usuarioId = usuarioId;
        this.bibliotecaId = bibliotecaId;
    }



    @Override
    public void updateWith(Emprestimo copy) {
        setId(copy.getId() != null ? copy.getId() : this.getId());
        setLivroId(copy.getLivroId() != null ? copy.getLivroId() : this.getLivroId());
        setUsuarioId(copy.getUsuarioId() != null ? copy.getUsuarioId() : this.getUsuarioId());
        setBibliotecaId(copy.getBibliotecaId() != null ? copy.getBibliotecaId() : this.getBibliotecaId());
        setDataEmprestimo(copy.getDataEmprestimo() != null ? copy.getDataEmprestimo() : this.getDataEmprestimo());
    }
}