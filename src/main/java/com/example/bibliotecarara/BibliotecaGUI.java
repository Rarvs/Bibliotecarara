package com.example.bibliotecarara;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.bibliotecarara.controller.LivroController;
import com.example.bibliotecarara.model.Emprestimo;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Reserva;
import com.example.bibliotecarara.model.Usuario;
import com.example.bibliotecarara.services.EmprestimoService;
import com.example.bibliotecarara.services.LivroService;
import com.example.bibliotecarara.services.ReservaService;
import com.example.bibliotecarara.services.UsuarioService;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BibliotecaGUI extends Application {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Biblioteca Rara");

        Button btnCriarUsuario = new Button("Criar Usuário");
        Button btnCriarLivro = new Button("Criar Livro");
        Button btnCriarEmprestimo = new Button("Criar Empréstimo");
        Button btnCriarReserva = new Button("Criar Reserva");

        btnCriarUsuario.setOnAction(e -> criarUsuario());
        btnCriarLivro.setOnAction(e -> criarLivro());
        btnCriarEmprestimo.setOnAction(e -> criarEmprestimo());
        btnCriarReserva.setOnAction(e -> criarReserva());


        VBox vbox = new VBox(btnCriarUsuario, btnCriarLivro, btnCriarEmprestimo, btnCriarReserva);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void criarUsuario() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Usuário");
        dialog.setHeaderText("Insira o nome do novo usuário:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nome -> {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            usuarioService.createEntity(novoUsuario);
            mostrarAlerta("Usuário Criado", "Usuário " + nome + " foi criado com sucesso.");
        });
    }

    private void criarLivro() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Livro");
        dialog.setHeaderText("Insira o título do novo livro:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(titulo -> {
            Livro novoLivro = new Livro();
            LivroController livroController = new LivroController(livroService);
            livroController.createLivro(novoLivro);
            mostrarAlerta("Livro Criado", "Livro " + titulo + " foi criado com sucesso.");
        });
    }

    private void criarEmprestimo() {
        TextInputDialog dialogUsuario = new TextInputDialog();
        dialogUsuario.setTitle("Criar Empréstimo");
        dialogUsuario.setHeaderText("Insira o nome do usuário para o empréstimo:");
        Optional<String> usuarioNome = dialogUsuario.showAndWait();

        usuarioNome.ifPresent(nome -> {
            Usuario usuario = usuarioService.findByNome(nome);
            if (usuario != null) {
                TextInputDialog dialogLivro = new TextInputDialog();
                dialogLivro.setTitle("Criar Empréstimo");
                dialogLivro.setHeaderText("Insira o nome do livro a ser emprestado:");
                Optional<String> livroNome = dialogLivro.showAndWait();

                livroNome.ifPresent(livro -> {
                    Livro livroParaEmprestimo = livroService.findLivroByTitulo(livro);
                    if (livroParaEmprestimo != null) {
                        Emprestimo emprestimo = new Emprestimo();
                        emprestimo.setUsuarioId(usuario.getId());
                        emprestimo.setLivroId(livroParaEmprestimo.getId());
                        emprestimoService.createEntity(emprestimo);
                        mostrarAlerta("Empréstimo Criado", "O empréstimo do livro " + livro + " para " + nome + " foi criado com sucesso.");
                    } else {
                        mostrarAlerta("Erro", "Livro não encontrado.");
                    }
                });
            } else {
                mostrarAlerta("Erro", "Usuário não encontrado.");
            }
        });
    }

    private void criarReserva() {
        TextInputDialog dialogUsuario = new TextInputDialog();
        dialogUsuario.setTitle("Criar Reserva");
        dialogUsuario.setHeaderText("Insira o nome do usuário para a reserva:");
        Optional<String> usuarioNome = dialogUsuario.showAndWait();

        usuarioNome.ifPresent(nome -> {
            Usuario usuario = usuarioService.findByNome(nome);
            if (usuario != null) {
                TextInputDialog dialogLivro = new TextInputDialog();
                dialogLivro.setTitle("Criar Reserva");
                dialogLivro.setHeaderText("Insira o nome do livro a ser reservado:");
                Optional<String> livroNome = dialogLivro.showAndWait();

                livroNome.ifPresent(livro -> {
                    Livro livroParaReserva = livroService.findLivroByTitulo(livro);
                    if (livroParaReserva != null) {
                        Reserva reserva = new Reserva();
                        reserva.setUsuarioId(usuario.getId());
                        reserva.setLivroId(livroParaReserva.getId());
                        reservaService.createEntity(reserva);
                        mostrarAlerta("Reserva Criada", "A reserva do livro " + livro + " para " + nome + " foi criada com sucesso.");
                    } else {
                        mostrarAlerta("Erro", "Livro não encontrado.");
                    }
                });
            } else {
                mostrarAlerta("Erro", "Usuário não encontrado.");
            }
        });
    }


    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

