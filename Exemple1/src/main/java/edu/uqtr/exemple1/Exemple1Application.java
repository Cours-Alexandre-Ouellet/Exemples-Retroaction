package edu.uqtr.exemple1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Exemple1Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Exemple1Application.class.getResource("creation-compte.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 250);
        stage.setTitle("Création de compte");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}