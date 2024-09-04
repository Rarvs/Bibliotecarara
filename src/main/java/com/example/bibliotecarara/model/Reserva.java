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
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "reserva_generator")
    private Long id;
    private Long livroId;
    private Long usuarioId;
    private Date dataReserva = Date.from(Instant.now());

    public Reserva(long livroId, long usuarioId){
        this.livroId = livroId;
        this.usuarioId = usuarioId;
    }

}