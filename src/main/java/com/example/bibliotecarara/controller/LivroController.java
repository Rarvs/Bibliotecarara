package com.example.bibliotecarara.controller;

import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.services.LivroService;
import jakarta.websocket.server.PathParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poo/livro")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable("id") long id){
        try {
            Livro livro = service.getEntityById(id);
            return ResponseEntity.ok(livro);
        } catch (NoEntityFoundException nEfe){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/porTitulo")
    public ResponseEntity<Livro> getLivroByTitulo(@RequestParam("titulo") String titulo) {
        try {
            return ResponseEntity.ok(service.findLivroByTitulo(titulo));
        } catch (NoEntityFoundException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/livros")
    public ResponseEntity<List<Livro>> getAllLivros(){
        List<Livro> livros = service.findAll();
        if(!livros.isEmpty()){
            return new ResponseEntity<>(livros, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/livros")
    public ResponseEntity<List<Livro>> postAllLivros(@RequestBody List<Livro> livros){
        try {
            return ResponseEntity.ok(service.saveAll(livros));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro){
        try {
            return new ResponseEntity<>(service.createEntity(livro), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable("id") long id, @RequestBody Livro livro){
        try {
            return new ResponseEntity<>(service.updateEntity(id, livro), HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/porTitulo")
    public ResponseEntity<Livro> updateLivro(@RequestParam("titulo") String titulo, @RequestBody Livro livro){
        try {
            return new ResponseEntity<>(service.updateLivroByTitulo(titulo, livro), HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Livro> deleteLivro(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(service.deleteEntity(id), HttpStatus.ACCEPTED);
        } catch (CouldNotDeleteEntityException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/porTitulo")
    public ResponseEntity<Livro> deleteLivroByTitulo(@PathParam("titulo") String titulo) {
        try {
            return new ResponseEntity<>(service.deleteLivroByTitulo(titulo), HttpStatus.ACCEPTED);
        }  catch (CouldNotDeleteEntityException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
