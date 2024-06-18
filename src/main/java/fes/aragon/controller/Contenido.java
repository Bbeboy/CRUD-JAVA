package fes.aragon.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Contenido extends Pane {
    public Contenido(String ruta) throws IOException{
        FXMLLoader fxml = new FXMLLoader(getClass().getResource(ruta));
        fxml.setRoot(this);
        fxml.load();
    }
}
