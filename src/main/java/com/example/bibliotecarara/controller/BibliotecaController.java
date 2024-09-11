package com.example.bibliotecarara.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.CouldNotUpdateEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.Biblioteca;
import com.example.bibliotecarara.model.Emprestimo;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Reserva;
import com.example.bibliotecarara.services.BibliotecaService;

@RestController
@RequestMapping("/rara/biblioteca")
public class BibliotecaController {
    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping("/")
    public ResponseEntity<Biblioteca> createBiblioteca(@RequestBody Biblioteca biblioteca){
        try{
            return new ResponseEntity<>(bibliotecaService.createEntity(biblioteca), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Biblioteca>> getBibliotecas(){
        try {
            return ResponseEntity.ok(bibliotecaService.findAll());
        } catch (NoEntityFoundException e){
            return noContent().build();
        }
    }

    @GetMapping("/{idBiblioteca}")
    public ResponseEntity<Biblioteca> getBibliotecaById(@PathVariable long idBiblioteca){
        try{
            return ok(bibliotecaService.getEntityById(idBiblioteca));
        } catch (Exception e){
            return noContent().build();
        }
    }

    //Livro por id
    @GetMapping("/{idBiblioteca}/livro/{idLivro}")
    public ResponseEntity<Livro> getLivroById(@PathVariable long idBiblioteca, @PathVariable long idLivro) {
        try{
            return ok(bibliotecaService.getLivroById(idLivro));
        } catch (NoEntityFoundException e){
            return noContent().build();
        }
    }

    @PutMapping("/{idBiblioteca}/livro/{idLivro}")
    public ResponseEntity<Biblioteca> saveLivroInBiblioteca(@PathVariable long idBiblioteca, @PathVariable long idLivro){
        try {
            return new ResponseEntity<>(bibliotecaService.saveLivroByIdInBiblioteca(idBiblioteca, idLivro), HttpStatus.ACCEPTED);
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/livro/{idLivro}")
    public ResponseEntity<Biblioteca> deleteLivroByIdFromBiblioteca(@PathVariable long idBiblioteca, @PathVariable long idLivro){
        try {
            return new ResponseEntity<>(bibliotecaService.deleteLivroByIdFromBiblioteca(idBiblioteca, idLivro), HttpStatus.ACCEPTED);
        } catch (CouldNotDeleteEntityException e){
            return badRequest().build();
        }
    }

    //Livro por titulo
    @GetMapping("/{idBiblioteca}/livro/porTitulo")
    public ResponseEntity<Livro> getLivroByTituloInBibilioteca(@PathVariable long idBiblioteca, @RequestParam String titulo){
        try {
            return ok(bibliotecaService.getLivroByTituloInBiblioteca(idBiblioteca, titulo));
        } catch (NoEntityFoundException e){
            return noContent().build();
        }
    }

    @PutMapping("/{idBiblioteca}/livro/porTitulo")
    public ResponseEntity<Biblioteca> saveLivroByTituloInBibilioteca(@PathVariable long idBiblioteca, @RequestParam String titulo){
        try {
            return new ResponseEntity<>(bibliotecaService.saveLivroByTituloInBiblioteca(idBiblioteca, titulo), HttpStatus.ACCEPTED);
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/livro/porTitulo")
    public ResponseEntity<Biblioteca> deleteLivroByTituloInBiblioteca(@PathVariable long idBiblioteca, @RequestParam String titulo){
        try {
            return new ResponseEntity<>(bibliotecaService.deleteLivroByTituloFromBiblioteca(idBiblioteca, titulo), HttpStatus.ACCEPTED);
        } catch (CouldNotDeleteEntityException e){
            return badRequest().build();
        }
    }
    //FIM LIVRO

    //Emprestimo
    @PostMapping("/{idBiblioteca}/emprestimo")
    public ResponseEntity<Emprestimo> createEmprestimo(@PathVariable long idBiblioteca, @RequestBody Emprestimo emprestimo){
        try{
            return new ResponseEntity<>(bibliotecaService.createEmprestimoWithBiblioteca(idBiblioteca, emprestimo),HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Emprestimo por id
    @GetMapping("/{idBiblioteca}/emprestimo/{idEmprestimo}")
    public ResponseEntity<Emprestimo> getEmprestimoById(@PathVariable long idBiblioteca, @PathVariable long idEmprestimo){
        try{
            return ok(bibliotecaService.getEmprestimoById(idBiblioteca, idEmprestimo));
        }catch (NoEntityFoundException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{idBiblioteca}/emprestimo/{idEmprestimo}")
    public ResponseEntity<Biblioteca> addEmprestimoById(@PathVariable long idBiblioteca, @PathVariable long idEmprestimo){
        try{
            return ok(bibliotecaService.addEmprestimoById(idBiblioteca, idEmprestimo));
        }catch (NoEntityFoundException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/emprestimo/{idEmprestimo}")
    public ResponseEntity<Biblioteca> deleteEmprestimoById(@PathVariable long idBiblioteca, @PathVariable long idEmprestimo){
        try{
            return ok(bibliotecaService.deleteEmprestimoById(idBiblioteca, idEmprestimo));
        }catch (NoEntityFoundException e){
            return badRequest().build();
        }
    }

    //Emprestimo por idLivroIdUsuario
    @GetMapping("/{idBiblioteca}/emprestimo/porIdLivroIdUsuario")
    public ResponseEntity<Emprestimo> getEmprestimoByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam long idLivro, @RequestParam long idUsuario){
        try {
            return ok(bibliotecaService.getEmprestimoByIdLivroIdUsuario(idBiblioteca, idLivro, idUsuario));
        } catch (CouldNotDeleteEntityException e) {
            return badRequest().build();
        }
    }

    @PutMapping("/{idBiblioteca}/emprestimo/porIdLivroIdUsuario")
    public ResponseEntity<Biblioteca> addEmprestimoByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam long idLivro, @RequestParam long idUsuario){
        try {
            return ok(bibliotecaService.addEmprestimoByIdLivroIdUsuario(idBiblioteca, idLivro, idUsuario));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/emprestimo/porIdLivroIdUsuario")
    public ResponseEntity<Biblioteca> deleteEmprestimoByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam long idLivro, @RequestParam long idUsuario){
        try {
            return ok(bibliotecaService.deleteEmprestimoByIdLivroIdUsuario(idBiblioteca, idLivro, idUsuario));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    //Emprestimo porNomeLivroRAUsuario
    @GetMapping("/{idBiblioteca}/emprestimo/porNomeLivroRAUsuario")
    public ResponseEntity<Emprestimo> getEmprestimoByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String ra){
        try {
            return ok(bibliotecaService.getEmprestimoByNomeLivroRAUsuario(idBiblioteca, titulo, ra));
        } catch (CouldNotDeleteEntityException e) {
            return badRequest().build();
        }
    }

    @PutMapping("/{idBiblioteca}/emprestimo/porNomeLivroRAUsuario")
    public ResponseEntity<Biblioteca> addEmprestimoByNomeLivroRAUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String ra){
        try {
            return ok(bibliotecaService.addEmprestimoByNomeLivroRAUsuario(idBiblioteca, titulo, ra));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/emprestimo/porNomeLivroRAUsuario")
    public ResponseEntity<Biblioteca> deleteEmprestimoByNomeLivroRAUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String ra){
        try {
            return ok(bibliotecaService.deleteEmprestimoByNomeLivroRAUsuario(idBiblioteca, titulo, ra));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    //Emprestimo porNomeLivroEmailUsuario
    @GetMapping("/{idBiblioteca}/emprestimo/porNomeLivroEmailUsuario")
    public ResponseEntity<Emprestimo> getEmprestimoByNomeLivroEmailUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String email){
        try {
            return ok(bibliotecaService.getEmprestimoByNomeLivroEmailUsuario(idBiblioteca, titulo, email));
        } catch (CouldNotDeleteEntityException e) {
            return badRequest().build();
        }
    }

    @PutMapping("/{idBiblioteca}/emprestimo/porNomeLivroEmailUsuario")
    public ResponseEntity<Biblioteca> addEmprestimoByNomeLivroEmailUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String email){
        try {
            return ok(bibliotecaService.addEmprestimoByNomeLivroEmailUsuario(idBiblioteca, titulo, email));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/emprestimo/porNomeLivroEmailUsuario")
    public ResponseEntity<Biblioteca> deleteEmprestimoByNomeLivroEmailUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String email){
        try {
            return ok(bibliotecaService.deleteEmprestimoByNomeLivroEmailUsuario(idBiblioteca, titulo, email));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }






    @PostMapping("/{idBiblioteca}/reserva")
    public ResponseEntity<Biblioteca> createReserva(@PathVariable long idBiblioteca, @RequestBody Reserva reserva){
        try{
            return new ResponseEntity<>(bibliotecaService.createReservaWithBiblioteca(idBiblioteca, reserva),HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idBiblioteca}/reserva/{idReserva}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable long idBiblioteca, @PathVariable long idReserva){
        try{
            return ok(bibliotecaService.getReservaById(idBiblioteca, idReserva));
        }catch (NoEntityFoundException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{idBiblioteca}/reserva/{idReserva}")
    public ResponseEntity<Biblioteca> addReservaById(@PathVariable long idBiblioteca, @PathVariable long idReserva){
        try{
            return ok(bibliotecaService.addReservaById(idBiblioteca, idReserva));
        }catch (NoEntityFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idBiblioteca}/reserva/{idReserva}")
    public ResponseEntity<Biblioteca> deleteReservaById(@PathVariable long idBiblioteca, @PathVariable long idReserva){
        try{
            return ok(bibliotecaService.deleteReservaById(idBiblioteca, idReserva));
        }catch (NoEntityFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idBiblioteca}/reserva/porIdLivroIdUsuario")
    public ResponseEntity<Reserva> getReservaByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam long idLivro, @RequestParam long idUsuario){
        try {
            return ok(bibliotecaService.getReservaByIdLivroIdUsuario(idBiblioteca, idLivro, idUsuario));
        } catch (CouldNotDeleteEntityException e) {
            return badRequest().build();
        }
    }

    @PutMapping("/{idBiblioteca}/reserva/porIdLivroIdUsuario")
    public ResponseEntity<Biblioteca> addReservaByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam long idLivro, @RequestParam long idUsuario){
        try {
            return ok(bibliotecaService.addReservaByIdLivroIdUsuario(idBiblioteca, idLivro, idUsuario));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/reserva/porIdLivroIdUsuario")
    public ResponseEntity<Biblioteca> deleteReservaByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam long idLivro, @RequestParam long idUsuario){
        try {
            return ok(bibliotecaService.deleteReservaByIdLivroIdUsuario(idBiblioteca, idLivro, idUsuario));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @GetMapping("/{idBiblioteca}/reserva/porNomeLivroRAUsuario")
    public ResponseEntity<Reserva> getReservaByIdLivroIdUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String ra){
        try {
            return ok(bibliotecaService.getReservaByNomeLivroRAUsuario(idBiblioteca, titulo, ra));
        } catch (CouldNotDeleteEntityException e) {
            return badRequest().build();
        }
    }

    @PutMapping("/{idBiblioteca}/reserva/porNomeLivroRAUsuario")
    public ResponseEntity<Biblioteca> addReservaByNomeLivroRAUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String ra){
        try {
            return ok(bibliotecaService.addReservaByNomeLivroRAUsuario(idBiblioteca, titulo, ra));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/reserva/porNomeLivroRAUsuario")
    public ResponseEntity<Biblioteca> deleteReservaByNomeLivroRAUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String ra){
        try {
            return ok(bibliotecaService.deleteReservaByNomeLivroRAUsuario(idBiblioteca, titulo, ra));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @GetMapping("/{idBiblioteca}/reserva/porNomeLivroEmailUsuario")
    public ResponseEntity<Reserva> getReservaByNomeLivroEmailUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String email){
        try {
            return ok(bibliotecaService.getReservaByNomeLivroEmailUsuario(idBiblioteca, titulo, email));
        } catch (CouldNotDeleteEntityException e) {
            return badRequest().build();
        }
    }

    @PutMapping("/{idBiblioteca}/reserva/porNomeLivroEmailUsuario")
    public ResponseEntity<Biblioteca> addReservaByNomeLivroEmailUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String email){
        try {
            return ok(bibliotecaService.addReservaByNomeLivroEmailUsuario(idBiblioteca, titulo, email));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

    @DeleteMapping("/{idBiblioteca}/reserva/porNomeLivroEmailUsuario")
    public ResponseEntity<Biblioteca> deleteReservaByNomeLivroEmailUsuario(@PathVariable long idBiblioteca, @RequestParam String titulo, @RequestParam String email){
        try {
            return ok(bibliotecaService.deleteReservaByNomeLivroEmailUsuario(idBiblioteca, titulo, email));
        } catch (CouldNotUpdateEntityException e){
            return badRequest().build();
        }
    }

//    @GetMapping("/livros")
//    public ResponseEntity<List<Livro>> getAllLivros() {
//        return livroController.getAllLivros();
//    }
//
//    @GetMapping("/livro/porTitulo")
//    public ResponseEntity<List<Livro>> getLivroByTitulo(@RequestParam("titulo") String titulo) {
//        return livroController.getLivroByTitulo(titulo);
//    }

////    @DeleteMapping("/livro/{id}")
////    public ResponseEntity<String> deleteLivro(@PathVariable("id") long id) {
////        return livroController.deleteLivro(id);
////    }
////
////    @DeleteMapping("/livro/porTitulo")
////    public ResponseEntity<String> deleteLivroByTitulo(@RequestParam("titulo") String titulo) {
////        return livroController.deleteLivroByTitulo(titulo);
////    }
//
//    @PostMapping("/livro")
//    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) {
//        return livroController.createLivro(livro);
//    }
//
//
//
//
//    //Emprestimo
//    @GetMapping("/emprestimo/{id}")
//    public ResponseEntity<Emprestimo> getEmpresetimoById(@PathVariable("id") long id) {
//        return emprestimoController.getEmprestimoById(id);
//    }
//
//    @GetMapping("/emprestimo/porNomeLivroRAUsuario")
//    public ResponseEntity<Emprestimo> getEmprestimoPorNomeLivroRAUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("raUsuario") String raUsuario){
//            return emprestimoController.getEmprestimoPorNomeLivroRAUsuario(nomeLivro, raUsuario);
//    }
//
//    @GetMapping("/emprestimo/porNomeLivroEmailUsuario")
//    public ResponseEntity<Emprestimo> getEmprestimoPorNomeLivroEmailUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("emailUsuario") String emailUsuario){
//        return emprestimoController.getEmprestimoPorNomeLivroEmailUsuario(nomeLivro, emailUsuario);
//    }
//
//    @GetMapping("/emprestimo/porIdLivroIdUsuario")
//    public ResponseEntity<Emprestimo> getEmpresetimoPorIdLivroIdUsuario(@RequestParam("livro") long livroId, @RequestParam("usuario") long usuarioId) {
//            return emprestimoController.getEmprestimoPorIdLivroIdUsuario(livroId, usuarioId);
//    }
//    @PostMapping("/emprestimo")
//    public ResponseEntity<Emprestimo> createEmprestimo(@RequestBody Emprestimo emprestimo) {
//            return emprestimoController.createEmprestimo(emprestimo);
//    }
//
//    @PostMapping("/emprestimo/porIdLivroIdUsuario")
//    public ResponseEntity<Emprestimo> createEmprestimoPorIdLivroIdUsuario(@PathParam("livroId") long livroId, @PathParam("usuarioId") long usuarioId){
//        return emprestimoController.createEmprestimoPorIdLivroIdUsuario(livroId, usuarioId);
//    }
//
//    @PostMapping("/emprestimo/porNomeLivroRAUsuario")
//    public ResponseEntity<Emprestimo> createEmprestimoPorNomeLivroRAUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("raUsuario") String raUsuario){
//        return emprestimoController.createEmprestimoPorNomeLivroRAUsuario(nomeLivro, raUsuario);
//    }
//
//    @PostMapping("/emprestimo/porNomeLivroEmailUsuario")
//    public ResponseEntity<Emprestimo> createEmprestimoPorNomeLivroEmailUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("emailUsuario") String emailUsuario){
//        return emprestimoController.createEmprestimoPorNomeLivroEmailUsuario(nomeLivro, emailUsuario);
//    }
//
//    @DeleteMapping("/emprestimo/{id}")
//    public ResponseEntity<String> deleteEmprestimo(@PathVariable("id") Long id){
//        return emprestimoController.deleteEmprestimo(id);
//    }
//
//    @DeleteMapping("/emprestimo/porIdLivroIdUsuario")
//    public ResponseEntity<String> deleteEmprestimoPorIdLivroIdUsuario(@PathParam("idLivro") long idLivro, @PathParam("idUsuario") long idUsuario){
//        return emprestimoController.deleteEmprestimoPorIdLivroIdUsuario(idLivro,idUsuario);
//    }
//
//    @DeleteMapping("/emprestimo/porNomeLivroRAUsuario")
//    public ResponseEntity<String> deleteEmprestimoPorNomeLivroRAUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("raUsuario") String raUsuario){
//        return emprestimoController.deleteEmprestimoPorNomeLivroRAUsuario(nomeLivro, raUsuario);
//    }
//
//    @DeleteMapping("/emprestimo/porNomeLivroEmailUsuario")
//    public ResponseEntity<String> deleteEmprestimoPorNomeLivroEmailUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("emailUsuario") String emailUsuario){
//        return emprestimoController.deleteEmprestimoPorNomeLivroEmailUsuario(nomeLivro, emailUsuario);
//    }
//
//
//
//
//    //Reserva
//    @GetMapping("/reserva/{id}")
//    public ResponseEntity<Reserva> getReservaById(@PathVariable("id") long id) {
//        return reservaController.getReservaById(id);
//    }
//
//    @GetMapping("/reserva/porNomeLivroRAUsuario")
//    public ResponseEntity<Reserva> getReservaPorNomeLivroRAUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("raUsuario") String raUsuario){
//        return reservaController.getReservaPorNomeLivroRAUsuario(nomeLivro, raUsuario);
//    }
//
//    @GetMapping("/reserva/porNomeLivroEmailUsuario")
//    public ResponseEntity<Reserva> getReservaPorNomeLivroEmailUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("emailUsuario") String emailUsuario){
//        return reservaController.getReservaPorNomeLivroEmailUsuario(nomeLivro, emailUsuario);
//    }
//
//    @GetMapping("/reserva/porIdLivroIdUsuario")
//    public ResponseEntity<Reserva> getReservaPorIdLivroIdUsuario(@RequestParam("livro") long livroId, @RequestParam("usuario") long usuarioId) {
//        return reservaController.getReservaPorIdLivroIdUsuario(livroId, usuarioId);
//    }
//    @PostMapping("/reserva")
//    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
//        return reservaController.createReserva(reserva);
//    }
//
//    @PostMapping("/reserva/porIdLivroIdUsuario")
//    public ResponseEntity<Reserva> createReservaPorIdLivroIdUsuario(@PathParam("livroId") long livroId, @PathParam("usuarioId") long usuarioId){
//        return reservaController.createReservaPorIdLivroIdUsuario(livroId, usuarioId);
//    }
//
//    @PostMapping("/reserva/porNomeLivroRAUsuario")
//    public ResponseEntity<Reserva> createReservaPorNomeLivroRAUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("raUsuario") String raUsuario){
//        return reservaController.createReservaPorNomeLivroRAUsuario(nomeLivro, raUsuario);
//    }
//
//    @PostMapping("/reserva/porNomeLivroEmailUsuario")
//    public ResponseEntity<Reserva> createReservaPorNomeLivroEmailUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("emailUsuario") String emailUsuario){
//        return reservaController.createReservaPorNomeLivroEmailUsuario(nomeLivro, emailUsuario);
//    }
//
//    @DeleteMapping("/reserva/{id}")
//    public ResponseEntity<String> deleteReserva(@PathVariable("id") Long id){
//        return reservaController.deleteReserva(id);
//    }
//
//    @DeleteMapping("/reserva/porIdLivroIdUsuario")
//    public ResponseEntity<String> deleteReservaPorIdLivroIdUsuario(@PathParam("idLivro") long idLivro, @PathParam("idUsuario") long idUsuario){
//        return reservaController.deleteReservaPorIdLivroIdUsuario(idLivro,idUsuario);
//    }
//
//    @DeleteMapping("/reserva/porNomeLivroRAUsuario")
//    public ResponseEntity<String> deleteReservaPorNomeLivroRAUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("raUsuario") String raUsuario){
//        return reservaController.deleteReservaPorNomeLivroRAUsuario(nomeLivro, raUsuario);
//    }
//
//    @DeleteMapping("/reserva/porNomeLivroEmailUsuario")
//    public ResponseEntity<String> deleteReservaPorNomeLivroEmailUsuario(@PathParam("nomeLivro") String nomeLivro, @PathParam("emailUsuario") String emailUsuario){
//        return reservaController.deleteReservaPorNomeLivroEmailUsuario(nomeLivro, emailUsuario);
//    }
}
