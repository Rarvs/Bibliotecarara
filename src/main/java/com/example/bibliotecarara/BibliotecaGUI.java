package com.example.bibliotecarara;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.bibliotecarara.controller.LivroController;
import com.example.bibliotecarara.controller.UsuarioController;
import com.example.bibliotecarara.model.Livro;
import com.example.bibliotecarara.model.Usuario;
import com.example.bibliotecarara.services.EmprestimoService;
import com.example.bibliotecarara.services.LivroService;
import com.example.bibliotecarara.services.ReservaService;
import com.example.bibliotecarara.services.UsuarioService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class BibliotecaGUI extends Application {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    private static ApplicationContext springContext;

    private ObservableList<String> usuariosList = FXCollections.observableArrayList();
    private ObservableList<String> livrosList = FXCollections.observableArrayList();
    private ObservableList<String> emprestimoList = FXCollections.observableArrayList();

    public static void setApplicationContext(ApplicationContext context) {
        springContext = context;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Biblioteca Rara");

        // Botões
        Button btnCriarUsuario = new Button("Criar Usuário");
        Button btnCriarLivro = new Button("Criar Livro");
        Button btnCriarEmprestimo = new Button("Criar Empréstimo");

        // ListViews para exibir os usuários e livros
        ListView<String> listViewUsuarios = new ListView<>(usuariosList);
        ListView<String> listViewLivros = new ListView<>(livrosList);
        ListView<String> listViewEmprestimos = new ListView<>(emprestimoList);

        // Configurar as ações dos botões
        btnCriarUsuario.setOnAction(e -> criarUsuario());
        btnCriarLivro.setOnAction(e -> criarLivro());
        btnCriarEmprestimo.setOnAction(e -> criarEmprestimo());

        // Layout da interface
        VBox vbox = new VBox(btnCriarUsuario, listViewUsuarios, btnCriarLivro, listViewLivros, btnCriarEmprestimo, listViewEmprestimos);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para criar um novo usuário
    private void criarUsuario() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Usuário");
        dialog.setHeaderText("Insira o email do novo usuário:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(email -> {
            Usuario novoUsuario = new Usuario();
            UsuarioController usuarioController = new UsuarioController(usuarioService);
            usuarioController.createUser(novoUsuario);
            usuariosList.add(email);  // Adicionar o email do usuário à lista
            mostrarAlerta("Usuário Criado", "Usuário " + email + " foi criado com sucesso.");
        });
    }

    // Método para criar um novo livro
    private void criarLivro() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Livro");
        dialog.setHeaderText("Insira o título do novo livro:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(titulo -> {
            Livro novoLivro = new Livro();
            LivroController livroController = new LivroController(livroService);
            livroController.createLivro(novoLivro);
            livrosList.add(titulo);  // Adicionar o título do livro à lista
            mostrarAlerta("Livro Criado", "Livro " + titulo + " foi criado com sucesso.");
        });
    }

    // Método para criar um empréstimo
    private void criarEmprestimo() {
        TextInputDialog dialogUsuario = new TextInputDialog();
        dialogUsuario.setTitle("Criar Empréstimo");
        dialogUsuario.setHeaderText("Insira o email do usuário para o empréstimo:");
        Optional<String> usuarioNome = dialogUsuario.showAndWait();
        usuarioNome.ifPresent(email -> {
            Usuario novoUsuario = new Usuario();
            UsuarioController usuarioController = new UsuarioController(usuarioService);
            usuarioController.createUser(novoUsuario);
            emprestimoList.add(email);  // Adicionar o email do usuário à lista
                TextInputDialog dialogLivro = new TextInputDialog();
                dialogLivro.setTitle("Criar Empréstimo");
                dialogLivro.setHeaderText("Insira o nome do livro a ser emprestado:");
                Optional<String> livroNome = dialogLivro.showAndWait();
                livroNome.ifPresent(livro -> {
                    Livro novoLivro = new Livro();
                    LivroController livroController = new LivroController(livroService);
                    livroController.createLivro(novoLivro);
                    emprestimoList.add(livro);  // Adicionar o título do livro à lista
                        mostrarAlerta("Empréstimo Criado", "O empréstimo do livro " + livro + " para " + email + " foi criado com sucesso.");
                });
        });
    }

    // Método para exibir alertas
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
