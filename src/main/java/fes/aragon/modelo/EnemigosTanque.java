package fes.aragon.modelo;

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

import static java.lang.Math.pow;

public class EnemigosTanque extends ComponentesJuego{
    private final ArrayList<Rectangle> enemigosTanque =new ArrayList<>();
    private final double spawnTiempo = 0.5; // tiempo de aparicion
    private Image img;
    private Spawn spawn;
    private double anchopantalla = 640;
    private double altopantalla = 640;
    private final double[][] spawnPoints = {
            {anchopantalla / 2, 0},             // punto a
            {anchopantalla / 2, altopantalla},  // punto b
            {anchopantalla, altopantalla / 2},  // punto c
            {0, altopantalla / 2}             // punto d
    };

    private int spawnIndex = 0;
    public EnemigosTanque(int x, int y, String imagen, double velocidad) {
        super(x, y, imagen, velocidad);
        File f = new File(imagen);
        this.img = new Image(f.toURI().toString());
        Vida s = new Vida();
        this.spawn = new Spawn(enemigosTanque, 1, 32, 32, s);
    }
    @Override
    public void pintar(GraphicsContext graficos) {
        for (Rectangle rectangulo : enemigosTanque) {
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

        for (Rectangle rectangle : enemigosTanque) {

            //Aqui se calcula se calcula la diferencia entre los componentes X y Y del jugador y los enemigos
            double deltaX = SingletonItems.getInstance().getNave().getX() - rectangle.getX();
            double deltaY = SingletonItems.getInstance().getNave().getY() - rectangle.getY();

            //Calculamos la distancia entre el jugador y los enemigos
            double distancia = Math.sqrt((pow(deltaX, 2)) + (pow(deltaY, 2)));

            //Este ultimo calculo normaliza el vector para que los enemigos no avancen a mayor velocidad
            //cuando se mueven en diagonal
            double movimientoX = (deltaX / distancia) * velocidad;
            double movimientoY = (deltaY / distancia) * velocidad;

            //Se asigna el valor a moverse de cada enemigo
            rectangle.setX(rectangle.getX() + movimientoX);
            rectangle.setY(rectangle.getY() + movimientoY);
        }
    }
    public void startspawn() {
        spawn.startSpawn();
    }

    public void detenerspawn() {
        spawn.stopSpawn();
    }

    public ArrayList<Rectangle> getEnemigosTanque() {
        return enemigosTanque;
    }

    public Spawn getSpawn() {
        return spawn;
    }
}
