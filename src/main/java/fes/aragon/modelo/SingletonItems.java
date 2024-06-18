package fes.aragon.modelo;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SingletonItems {
    private static SingletonItems items;
    private Nave nave;
    private Fondo fondo;
    private Enemigos enemigos;
    private EnemigosTanque enemigosTanque;
    private Boss boss;
    private EnemigosVeloz enemigosVeloz;
    private Disparo disparo;
    private ArrayList<ComponentesJuego> elementos;
    private PowerUps powerups;
    private LifeBar jugadorVida;
    private Rondas rondas;

    private SingletonItems() {
        this.iniciar();
    }

    public static SingletonItems getInstance() {
        if (items == null) {
            items = new SingletonItems();
        }
        return items;
    }

    public void iniciar() {

        nave = new Nave(320, 320, getClass().getResource("/fes/aragon/player/player_a1.png").getFile(), 2);
        fondo = new Fondo(0, 0, getClass().getResource("/fes/aragon/fondo/fondo_ver2.png").getFile(), 1);
        enemigosTanque = new EnemigosTanque(38, 38, getClass().getResource("/fes/aragon/enemigos/E_tank.png").getFile(), 0.5);
        enemigos = new Enemigos(32, 32, getClass().getResource("/fes/aragon/enemigos/E_normal.png").getFile(), 0.8);
        enemigosVeloz = new EnemigosVeloz(32, 32, getClass().getResource("/fes/aragon/enemigos/E_fast.png").getFile(), 1.2);
        disparo = new Disparo(0, 0, getClass().getResource("/fes/aragon/player/player_shot.png").getFile(), 5);
        powerups = new PowerUps(0, 0, getClass().getResource("/fes/aragon/powerUp/PowerUp.png").getFile(), 1);
        jugadorVida = new LifeBar(0,0,getClass().getResource("/fes/aragon/fondo/lifebar_100.png").getFile(),0);
        rondas = new Rondas();

        elementos = new ArrayList<>();
        elementos.add(fondo);
        elementos.add(jugadorVida);
        elementos.add(nave);
        elementos.add(enemigos);
        elementos.add(enemigosVeloz);
        elementos.add(enemigosTanque);
        elementos.add(disparo);
        elementos.add(powerups);

        // Crear un Timeline para generar un nuevo power-up cada 20 segundos
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            powerups.generarPowerUpAleatorio();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repetir indefinidamente
        timeline.play();
    }
    public void generarBoss(){
        boss = new Boss(96, 96, getClass().getResource("/fes/aragon/enemigos/E_bossver1.png").getFile(), 1);
        elementos.add(boss);
    }
    public ArrayList<ComponentesJuego> getElementos() {
        return elementos;
    }

    public Nave getNave() {
        return nave;
    }

    public Fondo getFondo() {
        return fondo;
    }

    public Disparo getDisparo() {
        return disparo;
    }

    public Enemigos getEnemigos() {
        return enemigos;
    }
    public EnemigosVeloz getEnemigosVeloz() {
        return enemigosVeloz;
    }
    public EnemigosTanque getEnemigosTanque() {
        return enemigosTanque;
    }

    public Boss getBoss() {
        return boss;
    }

    public PowerUps getPowerups() {
        return powerups;
    }
}
