package com.example.bibliotecarara.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario extends WithCopy<Usuario>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String RA;
    private String nome;
    private String email;
    @OneToMany(targetEntity = Emprestimo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Emprestimo> emprestimos = Set.of();
    @OneToMany(targetEntity = Reserva.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = Set.of();


    @Override
    public void updateWith(Usuario copy) {
        setId(copy.getId() != null ? copy.getId() : this.getId());
        setNome(copy.getNome() != null ? copy.getNome() : this.getNome());
        setRA(copy.getRA() != null ? copy.getRA() : this.getRA());
        setEmail(copy.getEmail() != null ? copy.getEmail() : this.getEmail());
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

}