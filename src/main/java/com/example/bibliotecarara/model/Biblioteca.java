package com.example.bibliotecarara.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Biblioteca extends WithCopy<Biblioteca>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @OneToMany(targetEntity = Livro.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Livro> livros = Set.of();
    @OneToMany(targetEntity = Reserva.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = Set.of();
    @OneToMany(targetEntity = Emprestimo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Emprestimo> emprestimos = Set.of();


    @Override
    public void updateWith(Biblioteca copy) {
        setId(copy.getId() != null ? copy.getId() : this.getId());
        setNome(copy.getNome() != null ? copy.getNome() : this.getNome());
        setLivros(copy.getLivros() != null ? copy.getLivros() : this.getLivros());
        setReservas(copy.getReservas() != null ? copy.getReservas() : this.getReservas());
        setEmprestimos(copy.getEmprestimos() != null ? copy.getEmprestimos() : this.getEmprestimos());

    }

    public void addLivro(Livro livro) throws Exception{
        if(livros.contains(livro)){
            throw new Exception();
        }
        livros.add(livro);
    }

    public void removeLivro(Livro livro) throws Exception{
        if(livros.contains(livro)){
            livros.remove(livro);
         }else{
            throw new Exception();
        }
    }

    public void addEmprestimo(Emprestimo emprestimo) throws Exception{
        if(emprestimos.contains(emprestimo)){
            throw new Exception();
        }
        emprestimos.add(emprestimo);
    }

    public void removeEmprestimo(Emprestimo emprestimo) throws Exception{
        if(emprestimos.contains(emprestimo)){
            emprestimos.remove(emprestimo);
        }else{
            throw new Exception();
        }
    }

    public void addReserva(Reserva reserva) throws Exception{
        if(reservas.contains(reserva)){
            throw new Exception();
        }
        reservas.add(reserva);
    }

    public void removeReserva(Reserva reserva) throws Exception{
        if(reservas.contains(reserva)){
            reservas.remove(reserva);
        }else{
            throw new Exception();
        }
    }
}