package com.example.bibliotecarara.services;

import com.example.bibliotecarara.exceptions.CouldNotCreateEntityException;
import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.CouldNotUpdateEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.*;
import com.example.bibliotecarara.repository.BibliotecaRepository;
import com.example.bibliotecarara.repository.EmprestimoRepository;
import com.example.bibliotecarara.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BibliotecaService extends BaseService<Biblioteca>{
    private final BibliotecaRepository bibliotecaRepository;
    private final EmprestimoService emprestimoService;
    private final LivroService livroService;
    private final ReservaService reservaService;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository, LivroService livroService, UsuarioService usuarioService, EmprestimoService emprestimoService, ReservaService reservaService){
        super(bibliotecaRepository);
        this.bibliotecaRepository = bibliotecaRepository;
        this.livroService = livroService;
        this.emprestimoService = emprestimoService;
        this.reservaService = reservaService;
    }

    public Livro getLivroById(long id){
        try{
            Livro livro = livroService.getEntityById(id);
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findByLivrosContaining(livro);
            if(optBiblioteca.isEmpty()){
                throw new NoEntityFoundException();
            }
            return livro;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca saveLivroByIdInBiblioteca(long bibliotecaId, long livroId){
        try {
            Livro newLivro = livroService.getEntityById(livroId);
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            biblioteca.addLivro(newLivro);
            bibliotecaRepository.save(biblioteca);
            newLivro.setBibliotecaId(bibliotecaId);
            livroService.updateEntity(livroId, newLivro);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }



    public Biblioteca deleteLivroByIdFromBiblioteca(long bibliotecaId, long livroId){
        try{
            Livro newLivro = livroService.getEntityById(livroId);
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            biblioteca.removeLivro(newLivro);
            bibliotecaRepository.save(biblioteca);

            newLivro.setBibliotecaId(null);
            livroService.updateEntity(newLivro.getId(), newLivro);
            return biblioteca;
        }catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Livro getLivroByTituloInBiblioteca(long bibliotecaId, String tituloLivro){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            List<Livro> livros = livroService.findAllLivrosByTitulo(tituloLivro);
            System.out.println(livros);
            for(Livro livro: livros){
                if(livro.getBibliotecaId().equals(biblioteca.getId())){
                    return livro;
                }
            }
            throw new NoEntityFoundException();
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca saveLivroByTituloInBiblioteca(long bibliotecaId, String tituloLivro){
        try {
            List<Livro> livros = livroService.findAllLivrosByTitulo(tituloLivro);
            Livro newLivro = livroService.findLivroByTitulo(tituloLivro);

            if(livros.isEmpty()){
                Livro livro = new Livro();
                livro.setTitulo(tituloLivro);
                newLivro = livroService.createEntity(livro);
            }

            Biblioteca biblioteca = getEntityById(bibliotecaId);
            biblioteca.addLivro(newLivro);
            bibliotecaRepository.save(biblioteca);

            newLivro.setBibliotecaId(bibliotecaId);
            livroService.updateEntity(newLivro.getId(), newLivro);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteLivroByTituloFromBiblioteca(long bibliotecaId, String tituloLivro) {
        try{
            Livro newLivro = livroService.findLivroByTitulo(tituloLivro);
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            biblioteca.removeLivro(newLivro);
            bibliotecaRepository.save(biblioteca);

            newLivro.setBibliotecaId(null);
            livroService.updateEntity(newLivro.getId(), newLivro);
            return biblioteca;
        }catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Emprestimo createEmprestimoWithBiblioteca(long bibliotecaId, Emprestimo emprestimo){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo newEmprestimo = emprestimoService.createEntity(emprestimo);
            newEmprestimo.setBibliotecaId(bibliotecaId);
            emprestimoService.updateEntity(newEmprestimo.getId(), newEmprestimo);
            biblioteca.addEmprestimo(newEmprestimo);
            bibliotecaRepository.save(biblioteca);
            return newEmprestimo;

        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public Emprestimo getEmprestimoById(long bibliotecaId, long emprestimoId) {
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo newEmprestimo = emprestimoService.getEntityById(emprestimoId);
            if(biblioteca.getEmprestimos().contains(newEmprestimo)){
                return newEmprestimo;
            }else{
                throw new NoEntityFoundException();
            }
        }catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addEmprestimoById(long bibliotecaId, long emprestimoId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.getEntityById(emprestimoId);
            biblioteca.addEmprestimo(emprestimo);
            emprestimo.setBibliotecaId(bibliotecaId);
            emprestimoService.updateEntity(emprestimoId, emprestimo);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteEmprestimoById(long bibliotecaId, long emprestimoId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.getEntityById(emprestimoId);
            biblioteca.removeEmprestimo(emprestimo);
            emprestimoService.deleteEmprestimo(emprestimo);
            bibliotecaRepository.save(biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Emprestimo getEmprestimoByIdLivroIdUsuario(long bibliotecaId, long livroId, long usuarioId){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByLivroIdUsuarioId(livroId, usuarioId);

            if(!Objects.equals(emprestimo.getBibliotecaId(), biblioteca.getId())){
                throw new NoEntityFoundException();
            }

            return emprestimo;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addEmprestimoByIdLivroIdUsuario(long bibliotecaId, long livroId, long usuarioId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByLivroIdUsuarioId(livroId, usuarioId);
            biblioteca.addEmprestimo(emprestimo);
            emprestimo.setBibliotecaId(bibliotecaId);
            emprestimoService.updateEntity(emprestimo.getId(), emprestimo);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteEmprestimoByIdLivroIdUsuario(long bibliotecaId, long livroId, long usuarioId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByLivroIdUsuarioId(livroId, usuarioId);
            if(emprestimo.getBibliotecaId().equals(biblioteca.getId())){
                biblioteca.removeEmprestimo(emprestimo);
                updateEntity(bibliotecaId, biblioteca);
            }
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Emprestimo getEmprestimoByNomeLivroRAUsuario(long bibliotecaId, String titulo, String ra){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByNomeLivroRAUsuario(titulo, ra);

            if(!Objects.equals(emprestimo.getBibliotecaId(), biblioteca.getId())){
                throw new NoEntityFoundException();
            }

            return emprestimo;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addEmprestimoByNomeLivroRAUsuario(long bibliotecaId, String titulo, String ra){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByNomeLivroRAUsuario(titulo, ra);
            biblioteca.addEmprestimo(emprestimo);
            emprestimo.setBibliotecaId(bibliotecaId);
            emprestimoService.updateEntity(emprestimo.getId(), emprestimo);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteEmprestimoByNomeLivroRAUsuario(long bibliotecaId, String titulo, String ra){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByNomeLivroRAUsuario(titulo, ra);
            if(emprestimo.getBibliotecaId().equals(biblioteca.getId())){
                biblioteca.removeEmprestimo(emprestimo);
                updateEntity(bibliotecaId, biblioteca);
            }
            emprestimoService.deleteEmprestimo(emprestimo);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Emprestimo getEmprestimoByNomeLivroEmailUsuario(long bibliotecaId, String titulo, String email){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByNomeLivroEmailUsuario(titulo, email);

            if(!Objects.equals(emprestimo.getBibliotecaId(), biblioteca.getId())){
                throw new NoEntityFoundException();
            }

            return emprestimo;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addEmprestimoByNomeLivroEmailUsuario(long bibliotecaId, String titulo, String email){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByNomeLivroEmailUsuario(titulo, email);
            biblioteca.addEmprestimo(emprestimo);
            emprestimo.setBibliotecaId(bibliotecaId);
            emprestimoService.updateEntity(emprestimo.getId(), emprestimo);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteEmprestimoByNomeLivroEmailUsuario(long bibliotecaId, String titulo, String email){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Emprestimo emprestimo = emprestimoService.findByNomeLivroEmailUsuario(titulo, email);
            if(emprestimo.getBibliotecaId().equals(biblioteca.getId())){
                biblioteca.removeEmprestimo(emprestimo);
                updateEntity(bibliotecaId, biblioteca);
            }
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }


    public Biblioteca createReservaWithBiblioteca(long bibliotecaId, Reserva reserva) {
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva newReserva = reservaService.createEntity(reserva);
            newReserva.setBibliotecaId(bibliotecaId);
            reservaService.updateEntity(newReserva.getId(), newReserva);
            biblioteca.addReserva(newReserva);
            bibliotecaRepository.save(biblioteca);
            return biblioteca;

        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public Reserva getReservaById(long bibliotecaId, long reservaId) {
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva newReserva = reservaService.getEntityById(reservaId);
            if(biblioteca.getReservas().contains(newReserva)){
                return newReserva;
            }else{
                throw new NoEntityFoundException();
            }
        }catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addReservaById(long bibliotecaId, long reservaId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.getEntityById(reservaId);
            biblioteca.addReserva(reserva);
            reserva.setBibliotecaId(bibliotecaId);
            reservaService.updateEntity(reservaId, reserva);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteReservaById(long bibliotecaId, long reservaId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.getEntityById(reservaId);
            biblioteca.removeReserva(reserva);
            reservaService.deleteReserva(reserva);
            bibliotecaRepository.save(biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Reserva getReservaByIdLivroIdUsuario(long bibliotecaId, long livroId, long usuarioId){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByLivroIdUsuarioId(livroId, usuarioId);

            if(!Objects.equals(reserva.getBibliotecaId(), biblioteca.getId())){
                throw new NoEntityFoundException();
            }

            return reserva;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addReservaByIdLivroIdUsuario(long bibliotecaId, long livroId, long usuarioId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByLivroIdUsuarioId(livroId, usuarioId);
            biblioteca.addReserva(reserva);
            reserva.setBibliotecaId(bibliotecaId);
            reservaService.updateEntity(reserva.getId(), reserva);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteReservaByIdLivroIdUsuario(long bibliotecaId, long livroId, long usuarioId){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByLivroIdUsuarioId(livroId, usuarioId);
            if(reserva.getBibliotecaId().equals(biblioteca.getId())){
                biblioteca.removeReserva(reserva);
                updateEntity(bibliotecaId, biblioteca);
            }
            reservaService.deleteReserva(reserva);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Reserva getReservaByNomeLivroRAUsuario(long bibliotecaId, String titulo, String ra){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByNomeLivroRAUsuario(titulo, ra);

            if(!Objects.equals(reserva.getBibliotecaId(), biblioteca.getId())){
                throw new NoEntityFoundException();
            }

            return reserva;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addReservaByNomeLivroRAUsuario(long bibliotecaId, String titulo, String ra){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByNomeLivroRAUsuario(titulo, ra);
            biblioteca.addReserva(reserva);
            reserva.setBibliotecaId(bibliotecaId);
            reservaService.updateEntity(reserva.getId(), reserva);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteReservaByNomeLivroRAUsuario(long bibliotecaId, String titulo, String ra){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByNomeLivroRAUsuario(titulo, ra);
            if(reserva.getBibliotecaId().equals(biblioteca.getId())){
                biblioteca.removeReserva(reserva);
                updateEntity(bibliotecaId, biblioteca);
            }
            reservaService.deleteReserva(reserva);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Reserva getReservaByNomeLivroEmailUsuario(long bibliotecaId, String titulo, String email){
        try {
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByNomeLivroEmailUsuario(titulo, email);

            if(!Objects.equals(reserva.getBibliotecaId(), biblioteca.getId())){
                throw new NoEntityFoundException();
            }

            return reserva;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Biblioteca addReservaByNomeLivroEmailUsuario(long bibliotecaId, String titulo, String email){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByNomeLivroEmailUsuario(titulo, email);
            biblioteca.addReserva(reserva);
            reserva.setBibliotecaId(bibliotecaId);
            reservaService.updateEntity(reserva.getId(), reserva);

            updateEntity(bibliotecaId, biblioteca);
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Biblioteca deleteReservaByNomeLivroEmailUsuario(long bibliotecaId, String titulo, String email){
        try{
            Biblioteca biblioteca = getEntityById(bibliotecaId);
            Reserva reserva = reservaService.findByNomeLivroEmailUsuario(titulo, email);
            if(reserva.getBibliotecaId().equals(biblioteca.getId())){
                biblioteca.removeReserva(reserva);
                updateEntity(bibliotecaId, biblioteca);
            }
            return biblioteca;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

}
