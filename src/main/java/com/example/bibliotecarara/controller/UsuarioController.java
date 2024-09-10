package com.example.bibliotecarara.controller;

import com.example.bibliotecarara.exceptions.CouldNotCreateEntityException;
import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.CouldNotUpdateEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Usuario;
import com.example.bibliotecarara.repository.UsuarioRepository;
import com.example.bibliotecarara.services.UsuarioService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/poo/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable("id") long id){
        try{
            return ResponseEntity.ok(service.getEntityById(id));
        } catch (NoEntityFoundException e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/porRA")
    public ResponseEntity<Usuario> getUserByRA(@PathParam("ra") String ra){
        try{
            return ResponseEntity.ok(service.findByRA(ra));
        } catch (NoEntityFoundException e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/porEmail")
    public ResponseEntity<Usuario> getUserByEmail(@PathParam("email") String email){
        try {
            return ResponseEntity.ok(service.findByEmail(email));
        } catch (NoEntityFoundException e){
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario){
        try {
            return ResponseEntity.created(URI.create("")).body(service.createEntity(usuario));
        } catch (CouldNotCreateEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable("id") long id, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.accepted().body(service.updateEntity(id, usuario));
        } catch (CouldNotUpdateEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/porEmail")
    public ResponseEntity<Usuario> updateUserByEmail(@RequestParam("email") String email, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.accepted().body(service.updateUsuarioByEmail(email, usuario));
        } catch (CouldNotUpdateEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/porRA")
    public ResponseEntity<Usuario> updateUserByRA(@RequestParam("ra") String ra, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.accepted().body(service.updateUsuarioByRA(ra, usuario));
        } catch (CouldNotUpdateEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable("id") Long id){
        try {
            return ResponseEntity.accepted().body(service.deleteEntity(id));
        } catch (CouldNotDeleteEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/porEmail")
    public ResponseEntity<Usuario> deleteUserByEmail(@RequestParam("email") String email){
        try {
            return ResponseEntity.accepted().body(service.deleteUsuarioByEmail(email));
        } catch (CouldNotDeleteEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/porRA")
    public ResponseEntity<Usuario> deleteUserByRA(@RequestParam("ra") String ra){
        try {
            return ResponseEntity.accepted().body(service.deleteUsuarioByRA(ra));
        } catch (CouldNotDeleteEntityException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
