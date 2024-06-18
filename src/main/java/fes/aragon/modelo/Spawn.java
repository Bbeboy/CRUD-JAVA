package fes.aragon.modelo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Spawn {
    private final ArrayList<Rectangle> enemigos;
    private final ArrayList<Vida> vidaEnemigo=new ArrayList<>();
    private final Vida vida;
    private final double[][] spawnPoints;
    private int spawnIndex = 0;
    private Timeline timeline;
    private final double anchoEnemigo;
    private final double altoEnemigo;

    public Spawn(ArrayList<Rectangle> enemigos, double spawnTiempo, double anchoEnemigo, double altoEnemigo, Vida vida) {
        this.enemigos = enemigos;
        this.vida = vida;
        this.anchoEnemigo = anchoEnemigo;
        this.altoEnemigo = altoEnemigo;
        this.spawnPoints = new double[][] {
                {320, 0},
                {320, 640},
                {640, 320},
                {0, 320}
        };
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(spawnTiempo), event -> spawnEnemigos()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    protected void spawnEnemigos() {
        double[] point = spawnPoints[spawnIndex];
        Rectangle r = new Rectangle(point[0], point[1], anchoEnemigo, altoEnemigo);
        Vida s = new Vida();
        vidaEnemigo.add(s);
        enemigos.add(r);
        spawnIndex = (spawnIndex + 1) % spawnPoints.length;
    }

    public void startSpawn() {
        timeline.play();
    }

    public void stopSpawn() {
        timeline.stop();
    }

    public ArrayList<Vida> getVidaEnemigo() {
        return vidaEnemigo;
    }

    public ArrayList<Rectangle> getEnemigos() {
        return enemigos;
    }

    protected double[][] getSpawnPoints() {
        return spawnPoints;
    }

    protected int getSpawnIndex() {
        return spawnIndex;
    }

    protected void incrementSpawnIndex() {
        spawnIndex = (spawnIndex + 1) % spawnPoints.length;
    }

    protected double getAnchoEnemigo() {
        return anchoEnemigo;
    }

    protected double getAltoEnemigo() {
        return altoEnemigo;
    }
}
