package fes.aragon.modelo;

import fes.aragon.controller.JuegoController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;
import javafx.stage.Stage;

public class Nave extends ComponentesJuego {
    private final ArrayList<Rectangle> jugador = new ArrayList<>();
    private boolean derecha = false;
    private boolean izquierda = false;
    private boolean arriba = false;
    private boolean abajo = false;
    private int vida = 5;

    private int ancho = 32;
    private int alto = 32;

    private double ultimoDirX = 0;
    private double ultimoDirY = -1; // Por defecto, disparar hacia arriba

    private double velocidad; // Velocidad de la nave
    Rectangle j = new Rectangle(x, y, ancho, alto);

    private Image img;

    public Nave(int x, int y, String imagen, double velocidad) {
        super(x, y, imagen, velocidad);
        this.velocidad = velocidad; // Inicializar la velocidad
        File f = new File(imagen);
        this.img = new Image(f.toURI().toString());
        vida=5;
        jugador.add(j);
    }

    @Override
    public void pintar(GraphicsContext graficos) {
        graficos.drawImage(img, x, y, ancho, alto);
    }

    @Override
    public void teclado(KeyEvent evento, boolean presiona) {
        switch (evento.getCode()) {
            case D:
                derecha = presiona;
                break;
            case A:
                izquierda = presiona;
                break;
            case W:
                arriba = presiona;
                break;
            case S:
                abajo = presiona;
                break;
            case SPACE:
                // Llama al método disparar solo cuando se suelta la tecla de espacio
                if (!presiona) {
                    // Obtén la dirección actual de la nave
                    double dirX = getUltimoDirX();
                    double dirY = getUltimoDirY();
                    // Dispara en la dirección actual
                    SingletonItems.getInstance().getDisparo().disparar(dirX, dirY);
                }
                break;
        }
    }

    @Override
    public void raton(MouseEvent evento) {
    }

    @Override
    public void logicaCalculos() {
        //Esto de aqui calcula la coliision con los enemigos y el jugador y si hay colision limpia los enemigos excepto el Boss
            double movimientoX = 0;
            double movimientoY = 0;
        Rectangle jugador = new Rectangle(SingletonItems.getInstance().getNave().getX(), SingletonItems.getInstance().getNave().getY(), 20, 20);
        for (Rectangle e : SingletonItems.getInstance().getEnemigos().getEnemigos()) {
            if (jugador.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                System.out.println("ColisiónjUgador");
                EfectosMusica efectosMusica = new EfectosMusica("ImpactoJugador"); //Efecto de Sonido cuando impactan con el jugador
                Thread hiloEfecto = new Thread(efectosMusica);
                hiloEfecto.start();
                SingletonItems.getInstance().getDisparo().nuke();
                vida --;
                break;
            }
        }
        for (Rectangle e : SingletonItems.getInstance().getEnemigosTanque().getEnemigosTanque()) {
            if (jugador.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                System.out.println("ColisiónjUgador");
                EfectosMusica efectosMusica = new EfectosMusica("ImpactoJugador"); //Efecto de Sonido cuando impactan con el jugador
                Thread hiloEfecto = new Thread(efectosMusica);
                hiloEfecto.start();
                SingletonItems.getInstance().getDisparo().nuke();
                vida --;
                break;
            }
        }
        for (Rectangle e : SingletonItems.getInstance().getEnemigosVeloz().getEnemigosVeloz()) {
            if (jugador.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                System.out.println("ColisiónjUgador");
                EfectosMusica efectosMusica = new EfectosMusica("ImpactoJugador"); //Efecto de Sonido cuando impactan con el jugador
                Thread hiloEfecto = new Thread(efectosMusica);
                hiloEfecto.start();
                SingletonItems.getInstance().getDisparo().nuke();
                vida --;
                break;
            }
        }
        for (Rectangle e : SingletonItems.getInstance().getDisparo().getDisparosBoss()) {
            if (jugador.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                System.out.println("ColisiónjUgador");
                EfectosMusica efectosMusica = new EfectosMusica("ImpactoJugador");
                Thread hiloEfecto = new Thread(efectosMusica);
                hiloEfecto.start();
                SingletonItems.getInstance().getDisparo().nuke();
                vida --;
                break;
            }
        }

            if (derecha) {
                File f = new File(getClass().getResource("/fes/aragon/player/player_b2.png").getFile());
                this.img = new Image(f.toURI().toString());
                movimientoX += 1;
            }
            if (izquierda) {
                File f = new File(getClass().getResource("/fes/aragon/player/player_b1.png").getFile());
                this.img = new Image(f.toURI().toString());
                movimientoX -= 1;
            }
            if (arriba) {
                File f = new File(getClass().getResource("/fes/aragon/player/player_a2.png").getFile());
                this.img = new Image(f.toURI().toString());
                movimientoY -= 1;
            }
            if (abajo) {
                File f = new File(getClass().getResource("/fes/aragon/player/player_a1.png").getFile());
                this.img = new Image(f.toURI().toString());
                movimientoY += 1;
            }

            // Normalizar el vector de movimiento si es necesario
            double magnitude = Math.sqrt(movimientoX * movimientoX + movimientoY * movimientoY);
            if (magnitude != 0) {
                movimientoX = (movimientoX / magnitude) * velocidad;
                movimientoY = (movimientoY / magnitude) * velocidad;
            }

            x += movimientoX;
            y += movimientoY;

            // Asegurarse de que la nave no se salga de los límites del fondo
            if (x < 30) {
                x = 30;
            }
            if (y < 30) {
                y = 30;
            }
            if (x > SingletonItems.getInstance().getFondo().getImg().getWidth() - ancho - 30) {
                x = SingletonItems.getInstance().getFondo().getImg().getWidth() - ancho - 30;
            }
            if (y > SingletonItems.getInstance().getFondo().getImg().getHeight() - alto - 30) {
                y = SingletonItems.getInstance().getFondo().getImg().getHeight() - alto - 30;
            }

            if (derecha || izquierda || arriba || abajo) {
                ultimoDirX = movimientoX;
                ultimoDirY = movimientoY;
            }

    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public double getUltimoDirX() {
        return ultimoDirX;
    }

    public double getUltimoDirY() {
        return ultimoDirY;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public ArrayList<Rectangle> getJugador() {
        return jugador;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }
}
