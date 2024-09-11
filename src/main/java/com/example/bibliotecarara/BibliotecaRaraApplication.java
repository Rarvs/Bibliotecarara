package com.example.bibliotecarara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

//import javafx.application.Application;

@SpringBootApplication
public class BibliotecaRaraApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BibliotecaRaraApplication.class, args);

        // Set the Spring context in the JavaFX application
//        BibliotecaGUI.setApplicationContext(context);

        // Launch JavaFX application
//        Application.launch(BibliotecaGUI.class, args);
    }
}
