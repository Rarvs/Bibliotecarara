package com.example.bibliotecarara.repository;

import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Reserva;
import com.example.bibliotecarara.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findById(long id);
    Optional<Reserva> findFirstByLivroIdAndUsuarioId(long livroId, long usuarioId);
    Optional<Reserva> findByLivroIdAndUsuarioId(long livroId, long usarioId);

    Optional<Reserva> deleteReservaById(long id);
}