package com.example.bibliotecarara.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reserva extends WithCopy<Reserva> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long livroId;
    private Date dataReserva = Date.from(Instant.now());
    private Long usuarioId;
    private Long bibliotecaId;

    public Reserva(long livroId, long usuario, long biblioteca){
        this.livroId = livroId;
        this.usuarioId = usuario;
        this.bibliotecaId = biblioteca;
    }


    @Override
    public void updateWith(Reserva copy) {
        setId(copy.getId() != null ? copy.getId() : this.getId());
        setLivroId(copy.getLivroId() != null ? copy.getLivroId() : this.getLivroId());
        setDataReserva(copy.getDataReserva() != this.getDataReserva() ? copy.getDataReserva() : this.getDataReserva());
        setUsuarioId(copy.getUsuarioId() != null ? copy.getUsuarioId() : this.getUsuarioId());
        setBibliotecaId(copy.getBibliotecaId() != null ? copy.getBibliotecaId() : this.getBibliotecaId());
    }
}