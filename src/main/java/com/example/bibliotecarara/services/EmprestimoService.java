package com.example.bibliotecarara.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bibliotecarara.exceptions.CouldNotCreateEntityException;
import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Emprestimo;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Usuario;
import com.example.bibliotecarara.repository.EmprestimoRepository;

@Service
public class EmprestimoService extends BaseService<Emprestimo>{

    private LivroService livroService;
    private UsuarioService usuarioService;

    private ReservaService reservaService;

    private EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository, LivroService livroService, UsuarioService usuarioService, ReservaService reservaService) {
        super(repository);
        this.repository = repository;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
        this.reservaService = reservaService;
    }

    @Override
    public Emprestimo createEntity(Emprestimo emprestimo) throws CouldNotCreateEntityException {
        try {
            long livroId = emprestimo.getLivroId();
            long usuarioId = emprestimo.getUsuarioId();
            Livro livro = livroService.getEntityById(livroId);
            Usuario usuario = usuarioService.getEntityById(usuarioId);
            assert livro != null;
            assert usuario != null;
            if (livro.isDisponivel()) {
                repository.save(emprestimo);
                livro.setDisponivel(false);
                livroService.updateEntity(livro.getId(), livro);
                usuarioService.addEmprestimo(usuarioId, emprestimo);
                return emprestimo;
            } else if (reservaService.findByLivroIdUsuarioId(livroId, usuarioId).getLivroId() == livroId && reservaService.findByLivroIdUsuarioId(livroId, usuarioId).getUsuarioId() == usuarioId) {
                repository.save(emprestimo);
                reservaService.deleteByIdLivroAndIdUsuario(livroId, usuarioId);
                livro.setDisponivel(false);
                livroService.updateEntity(livro.getId(), livro);
                usuarioService.addEmprestimo(usuarioId, emprestimo);
                return emprestimo;
            } else {
                throw new CouldNotCreateEntityException();
            }
        } catch (Exception e) {
            throw new CouldNotCreateEntityException();
        }
    }

    public Emprestimo findByLivroIdUsuarioId(long livroId, long usuarioId){
        try{
            Optional<Emprestimo> emprestimo = repository.findByLivroIdAndUsuarioId(livroId, usuarioId);
            return emprestimo.get();
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Emprestimo findByNomeLivroRAUsuario(String titulo, String ra){
        try {
            Livro livro = livroService.findLivroByTitulo(titulo);
            Usuario usuario = usuarioService.findByRA(ra);
            return findByLivroIdUsuarioId(livro.getId(), usuario.getId());
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Emprestimo findByNomeLivroEmailUsuario(String titulo, String email){
        try {
            Livro livro = livroService.findLivroByTitulo(titulo);
            Usuario usuario = usuarioService.findByEmail(email);
            return findByLivroIdUsuarioId(livro.getId(), usuario.getId());
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Emprestimo deleteEmprestimo(Emprestimo emprestimo){
        try{
            Livro livro = livroService.getEntityById(emprestimo.getLivroId());
            livro.setDisponivel(true);
            Usuario usuario = usuarioService.getEntityById(emprestimo.getUsuarioId());
            usuario.removeEmprestimo(emprestimo);
            usuarioService.updateEntity(usuario.getId(), usuario);
            livroService.updateEntity(livro.getId(), livro);
            return emprestimo;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Object listAll() {
        return repository.findAll();
    }
}