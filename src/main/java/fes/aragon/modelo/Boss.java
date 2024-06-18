package fes.aragon.modelo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.pow;


public class Boss extends ComponentesJuego {
    private final ArrayList<Rectangle> boss = new ArrayList<>();
    private final ArrayList<Vida> vidaboss = new ArrayList<>();
    int vidaBoss = 200;
    private Image img;
    private Random random;
    private Timeline timeline;
    private final int SCENE_WIDTH = 500;
    private final int SCENE_HEIGHT = 500;
    private Timer timer;
    private double movX = 0;
    private double movY = 0;
    private static final double MOVE_INTERVAL = 1; // Intervalo de movimiento (en segundos)
    private static final double MOVE_STEP = 0.12; // Tamaño del paso de movimiento

    public Boss(int x, int y, String imagen, double velocidad) {
        super(x, y, imagen, velocidad);
        File f = new File(imagen);
        this.img = new Image(f.toURI().toString());
        this.random = new Random();
        int xx = 380;
        int yy = 380;
        // Esto genera los enemigos
        Rectangle r = new Rectangle(xx, yy, x, y);
        Vida s = new Vida();
        boss.add(r);
        timeline = new Timeline(new KeyFrame(Duration.seconds(6), event -> logicaCalculos()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> dispararBossHaciaJugador()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void pintar(GraphicsContext graficos) {
        for (Rectangle rectangulo : boss) {
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
        // Si el timer no ha sido inicializado, inicialízalo
            moverEnemigos();
    }

    private void moverEnemigos() {
        for (Rectangle rectangle : boss) {
            // Si queda algún movimiento acumulado de la iteración anterior, aplícalo
            if (movX != 0 || movY != 0) {
                double moveX = Math.min(Math.abs(movX), MOVE_STEP) * Math.signum(movX);
                double moveY = Math.min(Math.abs(movY), MOVE_STEP) * Math.signum(movY);

                double newX = rectangle.getX() + moveX;
                double newY = rectangle.getY() + moveY;

                // Asegurarse de que los enemigos no se salgan del escenario
                if (newX >= 0 && newX <= SCENE_WIDTH - rectangle.getWidth()) {
                    rectangle.setX(newX);
                }
                if (newY >= 0 && newY <= SCENE_HEIGHT - rectangle.getHeight()) {
                    rectangle.setY(newY);
                }

                movX -= moveX;
                movY -= moveY;
            } else {
                // Generar movimientos aleatorios
                movX = (random.nextDouble() - 0.5) * velocidad * 30; // Multiplica por 2 para mover una mayor distancia
                movY = (random.nextDouble() - 0.5) * velocidad * 30; // Multiplica por 2 para mover una mayor distancia

                // Calcular nuevas posiciones
                double newX = rectangle.getX() + movX;
                double newY = rectangle.getY() + movY;

                // Asegurarse de que los enemigos no se salgan del escenario
                if (newX >= 0 && newX <= SCENE_WIDTH - rectangle.getWidth()) {
                    rectangle.setX(newX);
                }
                if (newY >= 0 && newY <= SCENE_HEIGHT - rectangle.getHeight()) {
                    rectangle.setY(newY);
                }
            }
        }
    }

    public void dispararBossHaciaJugador() {
        // Obtener las coordenadas del jugador
        double jugadorX = SingletonItems.getInstance().getNave().getX();
        double jugadorY = SingletonItems.getInstance().getNave().getY();

        // Obtener las coordenadas del boss
        double bossX = boss.get(0).getX() + 48;
        double bossY = boss.get(0).getY() + 48;

        // Calcular la dirección del disparo
        double dirX = jugadorX - bossX;
        double dirY = jugadorY - bossY;
        double length = Math.sqrt(dirX * dirX + dirY * dirY);

        // Normalizar la dirección
        dirX /= length;
        dirY /= length;

        // Crear una Timeline para disparar 5 veces con 0.17 segundos de diferencia
        double finalDirX = dirX;
        double finalDirY = dirY;
        Timeline shootTimeline = new Timeline(new KeyFrame(Duration.seconds(0.17), event -> {
            // Reproducir el sonido de disparo
            EfectosMusica efectosMusica = new EfectosMusica("Disparo");
            Thread hiloEfecto = new Thread(efectosMusica);
            hiloEfecto.start();
            DisparoDireccion disparoDireccion = new DisparoDireccion(bossX, bossY, finalDirX, finalDirY, 5.0); // Ajusta la velocidad de disparo
            SingletonItems.getInstance().getDisparo().addDisparo(disparoDireccion);
        }));
        shootTimeline.setCycleCount(5); // 5 disparos
        shootTimeline.play();
    }

    public ArrayList<Rectangle> getBoss() {
        return boss;
    }

    public ArrayList<Vida> getVidaBoss() {
        return vidaboss;
    }
}
