package fes.aragon.modelo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.File;

public class Rondas {
    private int rondaActual;
    private Timeline timeline;
    private boolean[] rondasActivas;

    public Rondas() {
        this.rondaActual = 0;
        this.rondasActivas = new boolean[5];// rondas
        iniciarRonda();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> siguienteRonda())); // Duración de 1 minuto por ronda más 5 segundos de descanso
        this.timeline.setCycleCount(1);
        this.timeline.play();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> siguienteRonda())); // Duración de 1 minuto por ronda más 5 segundos de descanso
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void iniciarRonda() {
        rondasActivas[rondaActual] = true;
        switch (rondaActual) {
            case 1:
                SingletonItems.getInstance().getEnemigos().startspawn();
                break;
            case 2:
                SingletonItems.getInstance().getEnemigos().startspawn();
                SingletonItems.getInstance().getEnemigosVeloz().startspawn();
                break;
            case 3:
                SingletonItems.getInstance().getEnemigos().startspawn();
                SingletonItems.getInstance().getEnemigosVeloz().startspawn();
                SingletonItems.getInstance().getEnemigosTanque().startspawn();
                break;
            case 4:
                SingletonItems.getInstance().getDisparo().setBossAlive(true);
                System.out.println("Jefe");
                EfectosMusica efectosMusica = new EfectosMusica("AparicionBoss");
                Thread hiloEfecto = new Thread(efectosMusica);
                hiloEfecto.start();
                SingletonItems.getInstance().generarBoss();
        }
    }

    private void detenerRonda() {
        rondasActivas[rondaActual] = false;
        SingletonItems.getInstance().getEnemigos().detenerspawn();
        SingletonItems.getInstance().getEnemigosVeloz().detenerspawn();
        SingletonItems.getInstance().getEnemigosTanque().detenerspawn();
    }

    private void siguienteRonda() {
        detenerRonda();
        rondaActual++;
        if (rondaActual >=5 ) {
            rondaActual = 1;
        }
        iniciarRonda();
    }

    public boolean isRondaActiva(int ronda) {
        return rondasActivas[ronda];
    }
}
