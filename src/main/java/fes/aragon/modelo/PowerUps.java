package fes.aragon.modelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class PowerUps extends ComponentesJuego {
    private ArrayList<Rectangle> powerups = new ArrayList<>();
    private final Random random = new Random();
    private Timeline timeline;
    private Image img;

    public PowerUps(int x, int y, String imagen, double velocidad) {
        super(x, y, imagen, velocidad);
        File f = new File(imagen);
        this.img = new Image(f.toURI().toString());
    }

    public void iniciar() {
        powerups.clear(); // Limpiamos la lista de power-ups

        // Generamos un nuevo power-up aleatorio al inicio del juego
        generarPowerUpAleatorio();

        // Crear un Timeline para generar un nuevo power-up cada 5 segundos
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            powerups.clear(); // Limpiamos la lista antes de generar uno nuevo
            generarPowerUpAleatorio();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repetir indefinidamente
        timeline.play();
    }

    public void generarPowerUpAleatorio() {
        int xx = random.nextInt(500 - 140);
        int yy = random.nextInt(500 - 140);
        Rectangle r = new Rectangle(xx, yy, 32, 32);
        EfectosMusica efectosMusica = new EfectosMusica("GenerarPower");
        Thread hiloEfecto = new Thread(efectosMusica);
        hiloEfecto.start();
        powerups.add(r);

        // Crear un Timeline para eliminar automáticamente el power-up después de 4 segundos si no colisiona con el jugador
        Timeline removerTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (powerups.contains(r)) { // Verificar si el power-up aún está en la lista
                powerups.remove(r); // Eliminar el power-up si sigue en la lista después de 5 segundos
            }
        }));
        removerTimeline.setCycleCount(1); // Ejecutar una vez
        removerTimeline.play();
    }

    @Override
    public void pintar(GraphicsContext graficos) {
        for (Rectangle rectangulo : powerups) {
            graficos.drawImage(img, rectangulo.getX(), rectangulo.getY(), rectangulo.getWidth(), rectangulo.getHeight());
        }
    }

    @Override
    public void teclado(KeyEvent evento, boolean presiona) {

    }

    @Override
    public void raton(MouseEvent evento) {

    }

    @Override
    public void logicaCalculos() {
        Rectangle jugador = new Rectangle(SingletonItems.getInstance().getNave().getX(), SingletonItems.getInstance().getNave().getY(), 20, 20);
        for (Rectangle powerup : powerups) {
            if (jugador.intersects(powerup.getBoundsInLocal())) {
                powerups.remove(powerup);
                aplicarEfectoPowerUp();
                break;
            }
        }
    }

    private void aplicarEfectoPowerUp() {
        int randomNumber = random.nextInt(3);
        if (randomNumber == 0) {
            // Power-up de escopeta
            EfectosMusica efectosMusica = new EfectosMusica("Escopeta");
            Thread hiloEfecto = new Thread(efectosMusica);
            hiloEfecto.start();
            SingletonItems.getInstance().getDisparo().setTipoDisparo(Disparo.TipoDisparo.ESCOPETA);
            // Crear un Timeline para cambiar al disparo normal después de 10 segundos
            Timeline escopetaTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                SingletonItems.getInstance().getDisparo().setTipoDisparo(Disparo.TipoDisparo.NORMAL);
            }));
            escopetaTimeline.setCycleCount(1); // Ejecutar una vez
            escopetaTimeline.play();
        } else if (randomNumber == 1) {
            // Modificar velocidad del jugador
            double nuevaVelocidad = 1.5;
            EfectosMusica efectosMusica = new EfectosMusica("PowerUpVelocidad");
            Thread hiloEfecto = new Thread(efectosMusica);
            hiloEfecto.start();
            SingletonItems.getInstance().getNave().setVelocidad(nuevaVelocidad);
            Timeline velocidadTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                double velocidadOriginal = 1; // vuelve a la velocidad original del jugador
                SingletonItems.getInstance().getNave().setVelocidad(velocidadOriginal);
            }));
            velocidadTimeline.setCycleCount(1);
            velocidadTimeline.play();
        } else {
            // Power-up de velocidad de disparo
            double nuevaVelocidadDisparo = 6; // Nueva velocidad de disparo
            EfectosMusica efectosMusica = new EfectosMusica("PowerUpVelocidadDisparo");
            Thread hiloEfecto = new Thread(efectosMusica);
            hiloEfecto.start();
            SingletonItems.getInstance().getDisparo().setVelocidadDisparo(nuevaVelocidadDisparo); // Cambiar la velocidad de disparo
            Timeline velocidadDisparoTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                double velocidadDisparoOriginal = 3; // Vuelve a la velocidad de disparo original
                SingletonItems.getInstance().getDisparo().setVelocidadDisparo(velocidadDisparoOriginal);
            }));
            velocidadDisparoTimeline.setCycleCount(1);
            velocidadDisparoTimeline.play();
        }
    }
}
