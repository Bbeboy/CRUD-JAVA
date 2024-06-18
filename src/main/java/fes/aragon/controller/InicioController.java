package fes.aragon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class InicioController {
    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnArchivo;

    @FXML
    private BorderPane btnPrincipal;

    @FXML
    private Button btnRacionales;
    @FXML
    private Button btnJuego;

    @FXML
    void abrirArchivo(ActionEvent event) {
        ventana("/fes/aragon/xml/archivo.fxml");
    }

    @FXML
    void abrirRacionales(ActionEvent event) {
        ventana("/fes/aragon/xml/racionales.fxml");
    }

    @FXML
    void accionAbrirUsuarios(ActionEvent event) {
        ventana("/fes/aragon/xml/tabla_usuario.fxml");
    }

    private void ventana (String ruta){
        try {
            Contenido contenido = new Contenido(ruta);
            btnPrincipal.setCenter(contenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void accionAbrirJuego(ActionEvent event) {
        try {
            FXMLLoader modificar= new FXMLLoader(getClass().getResource("/fes/aragon/xml/juego.fxml"));
            Parent parent=(Parent)modificar.load();
            Scene scene=new Scene(parent, 640, 640);
            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            ((JuegoController)modificar.getController()).setEscena(scene);
            ((JuegoController)modificar.getController()).iniciar();
            stage.show();
            stage.requestFocus();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
