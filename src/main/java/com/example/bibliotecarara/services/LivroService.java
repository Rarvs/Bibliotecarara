package com.example.bibliotecarara.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.CouldNotUpdateEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.repository.LivroRepository;

@Service
public class LivroService extends BaseService<Livro> {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository){
        super(livroRepository);
        this.livroRepository = livroRepository;
    }

    public Livro findLivroByTitulo(String titulo) throws NoEntityFoundException {
        try{
            Livro livro = livroRepository.findFirstByTitulo(titulo);
            if(livro == null){
                throw new NoEntityFoundException();
            }
            return livro;
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public List<Livro> findAllLivrosByTitulo(String titulo){
        try {
            return livroRepository.findAllByTitulo(titulo);
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public Livro updateLivroByTitulo(String titulo, Livro newLivro){
        try {
            Livro livro = livroRepository.findFirstByTitulo(titulo);
            updateEntity(livro.getId(), newLivro);
            return livro;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public Livro deleteLivroByTitulo(String titulo){
        try{
            Livro livro = livroRepository.findFirstByTitulo(titulo);
            livroRepository.delete(livro);
            return livro;
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }

    public long count() {
        return livroRepository.count();
    }
}