package fes.aragon.controller;

import fes.aragon.modelo.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class JuegoController {

    @FXML
    private Canvas canvas;
    private GraphicsContext graficos;
    private Scene escena;
    private AnimationTimer tiempo;
    private Thread hiloFondo;


    public void setEscena(Scene escena) {
        this.escena = escena;
    }
    public void iniciar() {
        componentesIniciar();
        cerrarJuego();
        pintar();
        eventosTeclado();
        eventoRaton();
        ciclo();

    }

    private void eventoRaton() {
        escena.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SingletonItems.getInstance().getNave().raton(mouseEvent);
              //
            }
        });
    }

    private void eventosTeclado() {
        escena.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent arg0) {
                // TODO Auto-generated method stub
                System.out.println("Evento 2");
                SingletonItems.getInstance().getNave().teclado(arg0,true);

            }
        });
        escena.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                // TODO Auto-generated method stub
                System.out.println("Evento 1");
                SingletonItems.getInstance().getNave().teclado(arg0,false);
            }

        });
    }

    private void componentesIniciar() {
        graficos = canvas.getGraphicsContext2D();
        SingletonItems.getInstance();
        MusicaCiclica entrada = new MusicaCiclica("PraderaTheme");
        hiloFondo = new Thread(entrada);
        hiloFondo.start();
    }

    private void ciclo() {
        long tiempoInicio = System.nanoTime();
        tiempo = new AnimationTimer() {
            @Override
            public void handle(long tiempoActual) {
                double t = (tiempoActual - tiempoInicio) / 1000000000.0;
                // personajeA.setTiempo(t);
                calculosLogica();
                pintar();

            }

        };
        tiempo.start();
    }

    private void calculosLogica() {
        for (ComponentesJuego el:SingletonItems.getInstance().getElementos()){
            el.logicaCalculos();
        }

    }

    private void pintar() {
        for (ComponentesJuego el:SingletonItems.getInstance().getElementos()){
            el.pintar(graficos);
        }
    }
    public void cerrarJuego(){
        Stage stage=(Stage)canvas.getScene().getWindow();
        stage.setOnCloseRequest((t) -> {
                tiempo.stop();
                hiloFondo.stop();
                stage.close();
            }
        );
    }

}
