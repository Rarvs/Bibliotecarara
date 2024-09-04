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
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "emprestimo_generator")
    private Long id;
    private Long livroId;
    private Long usuarioId;
    private Date dataEmprestimo = Date.from(Instant.now());

    public Emprestimo(long livroId, long usuarioId){
        this.livroId = livroId;
        this.usuarioId = usuarioId;
    }

}