package com.example.bibliotecarara.services;

import com.example.bibliotecarara.exceptions.CouldNotCreateEntityException;
import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Reserva;
import com.example.bibliotecarara.model.Usuario;
import com.example.bibliotecarara.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservaService extends BaseService<Reserva>{

    private ReservaRepository reservaRepository;

    private LivroService livroService;

    private UsuarioService usuarioService;

    public ReservaService(ReservaRepository repository, LivroService livroService, UsuarioService usuarioService) {
        super(repository);
        this.reservaRepository = repository;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @Override
    public Reserva createEntity(Reserva reserva) throws
            CouldNotCreateEntityException{
        try{
            long livroId = reserva.getLivroId();
            long usuarioId = reserva.getUsuarioId();
            Livro livro = livroService.getEntityById(livroId);
            Usuario usuario = usuarioService.getEntityById(usuarioId);
            assert livro != null;
            assert usuario != null;
            livro.setDisponivel(false);
            livroService.updateEntity(livro.getId(), livro);
            usuarioService.addReserva(usuarioId, reserva);
            repository.save(reserva);
            return reserva;

        } catch (Exception e) {
            throw new CouldNotCreateEntityException();
        }
    }

    public Reserva findByLivroIdUsuarioId(long livroId, long usuarioId){
        try{
            Optional<Reserva> reserva = reservaRepository.findByLivroIdAndUsuarioId(livroId, usuarioId);
            return reserva.get();
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Reserva findByNomeLivroRAUsuario(String titulo, String ra){
        try {
            Livro livro = livroService.findLivroByTitulo(titulo);
            Usuario usuario = usuarioService.findByRA(ra);
            return findByLivroIdUsuarioId(livro.getId(), usuario.getId());
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Reserva findByNomeLivroEmailUsuario(String titulo, String email){
        try {
            Livro livro = livroService.findLivroByTitulo(titulo);
            Usuario usuario = usuarioService.findByEmail(email);
            return findByLivroIdUsuarioId(livro.getId(), usuario.getId());
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Reserva deleteByIdLivroAndIdUsuario(long livroId, long usuarioId){
        try{
            Reserva reserva = findByLivroIdUsuarioId(livroId, usuarioId);

            Livro livro = livroService.getEntityById(livroId);
            livro.setDisponivel(true);
            Usuario usuario = usuarioService.getEntityById(usuarioId);
            usuario.removeReserva(reserva);
            usuarioService.updateEntity(usuarioId, usuario);
            livroService.updateEntity(livroId,livro);

            return reserva;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Reserva deleteReserva(Reserva reserva){
        try{
            deleteEntity(reserva.getId());
            return reserva;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }
}