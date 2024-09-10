package com.example.bibliotecarara.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bibliotecarara.exceptions.CouldNotCreateEntityException;
import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.CouldNotUpdateEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Emprestimo;
import com.example.bibliotecarara.model.Reserva;
import com.example.bibliotecarara.model.Usuario;
import com.example.bibliotecarara.repository.UsuarioRepository;

@Service
public class UsuarioService extends BaseService<Usuario>{

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.repository = repository;
    }

   public Usuario findByNome(String nome) throws NoEntityFoundException {
        Usuario usuario = repository.findFirstByNome(nome);
        if(usuario == null){
            throw new NoEntityFoundException();
        }
        return usuario;
   }

    public Usuario findByRA(String ra) {
        Usuario usuario = repository.findFirstByRA(ra);
        if(usuario == null){
            throw new NoEntityFoundException();
        }
        return usuario;
    }


    public Usuario findByEmail(String email) {
        Usuario usuario = repository.findFirstByEmail(email);
        if(usuario == null){
            throw new NoEntityFoundException();
        }
        return usuario;
    }


    public Usuario addEmprestimo(long usuarioId, Emprestimo emprestimo){
        try{
            Optional<Usuario> optionalUsuario = repository.findById(usuarioId);
            if(optionalUsuario.isEmpty()){
                throw new CouldNotCreateEntityException();
            }
            Usuario usuario = optionalUsuario.get();
            usuario.addEmprestimo(emprestimo);
            updateEntity(usuarioId, usuario);
            return usuario;
        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public Usuario removeEmprestimo(long usuarioId, Emprestimo emprestimo){
        try{
            Optional<Usuario> optionalUsuario = repository.findById(usuarioId);
            if(optionalUsuario.isEmpty()){
                throw new CouldNotCreateEntityException();
            }
            Usuario usuario = optionalUsuario.get();
            usuario.removeEmprestimo(emprestimo);
            updateEntity(usuarioId, usuario);
            return usuario;
        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public Usuario addReserva(long usuarioId, Reserva reserva){
        try{
            Optional<Usuario> optionalUsuario = repository.findById(usuarioId);
            if(optionalUsuario.isEmpty()){
                throw new CouldNotCreateEntityException();
            }
            Usuario usuario = optionalUsuario.get();
            usuario.addReserva(reserva);
            updateEntity(usuarioId, usuario);
            return usuario;
        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public Usuario removeReserva(long usuarioId, Reserva reserva){
        try{
            Optional<Usuario> optionalUsuario = repository.findById(usuarioId);
            if(optionalUsuario.isEmpty()){
                throw new CouldNotCreateEntityException();
            }
            Usuario usuario = optionalUsuario.get();
            usuario.removeReserva(reserva);
            updateEntity(usuarioId, usuario);
            return usuario;
        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public Usuario deleteUsuarioByEmail(String email) {
        try{
            Usuario usuario = findByEmail(email);
            return deleteEntity(usuario.getId());
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Usuario deleteUsuarioByRA(String ra) {
        try{
            Usuario usuario = findByRA(ra);
            return deleteEntity(usuario.getId());
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public Usuario updateUsuarioByEmail(String email, Usuario newUsuario){
        try{
            Usuario usuario = findByEmail(email);
            return updateEntity(usuario.getId(), newUsuario);
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Usuario updateUsuarioByRA(String ra, Usuario newUsuario){
        try{
            Usuario usuario = findByRA(ra);
            return updateEntity(usuario.getId(), newUsuario);
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Object listAll() {
        return repository.findAll();
    }
}