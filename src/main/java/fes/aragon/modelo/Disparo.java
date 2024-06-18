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

public class Disparo extends ComponentesJuego {
    private boolean bossAlive = false;
    private ArrayList<DisparoDireccion> disparos = new ArrayList<>();
    private ArrayList<DisparoDireccion> disparosBoss = new ArrayList<>();
    private TipoDisparo tipoDisparo = TipoDisparo.NORMAL;
    private double velocidadDisparo;
    private Image img;

    public enum TipoDisparo {
        NORMAL, ESCOPETA, VELOCIDAD
    }

    public Disparo(int x, int y, String imagen, double velocidad) {
        super(x, y, imagen, velocidad);
        this.velocidadDisparo = velocidad;
        File f = new File(imagen);
        this.img = new Image(f.toURI().toString());
    }

    // Método para establecer la velocidad de disparo
    public void setVelocidadDisparo(double velocidad) {
        this.velocidadDisparo = velocidad;
    }

    @Override
    public void pintar(GraphicsContext graficos) {
        for (DisparoDireccion disparo : disparos) {
            graficos.drawImage(img, disparo.getX(), disparo.getY(), disparo.getWidth(), disparo.getHeight());
        }
        for (DisparoDireccion disparoBoss : disparosBoss) {
            graficos.drawImage(img, disparoBoss.getX(), disparoBoss.getY(), disparoBoss.getWidth(), disparoBoss.getHeight());
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
        // Recorrer disparos y moverlos
        ArrayList<DisparoDireccion> disparosAEliminar = new ArrayList<>();
        for (DisparoDireccion disparo : disparos) {
            disparo.mover();
            // Verificar si el disparo está fuera de la pantalla
            if (disparo.getX() < 0 || disparo.getX() > SingletonItems.getInstance().getFondo().getImg().getWidth()
                    || disparo.getY() < 0 || disparo.getY() > SingletonItems.getInstance().getFondo().getImg().getHeight()) {
                disparosAEliminar.add(disparo);
            }
        }
        for (DisparoDireccion disparo : disparosBoss) {
            disparo.mover();
            // Verificar si el disparo está fuera de la pantalla
            if (disparo.getX() < 0 || disparo.getX() > SingletonItems.getInstance().getFondo().getImg().getWidth()
                    || disparo.getY() < 0 || disparo.getY() > SingletonItems.getInstance().getFondo().getImg().getHeight()) {
                disparosAEliminar.add(disparo);
            }
        }

        // Eliminar los disparos que están fuera de la pantalla
        disparos.removeAll(disparosAEliminar);

        // Revisar si un disparo hace colisión con un enemigo
        ArrayList<Rectangle> enemigosAEliminar = new ArrayList<>();
        for (DisparoDireccion d : disparos) {
            for (Rectangle e : SingletonItems.getInstance().getEnemigos().getEnemigos()) {
                if (d.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                    System.out.println("Colisión");
                    EfectosMusica efectosMusica = new EfectosMusica("MuerteTank");
                    Thread hiloEfecto = new Thread(efectosMusica);
                    hiloEfecto.start();
                    enemigosAEliminar.add(e);
                    disparosAEliminar.add(d);
                    break;
                }
            }
        }
        for (DisparoDireccion d : disparos) {
            for (Rectangle e : SingletonItems.getInstance().getEnemigosVeloz().getEnemigosVeloz()) {
                if (d.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                    System.out.println("Colisión");
                    EfectosMusica efectosMusica = new EfectosMusica("MuerteTank");
                    Thread hiloEfecto = new Thread(efectosMusica);
                    hiloEfecto.start();
                    enemigosAEliminar.add(e);
                    disparosAEliminar.add(d);
                    break;
                }
            }
        }
        for (Rectangle d : disparos) {
            for (Rectangle e : SingletonItems.getInstance().getEnemigosTanque().getSpawn().getEnemigos()) {
                if (d.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                    int indice = SingletonItems.getInstance().getEnemigosTanque().getSpawn().getEnemigos().indexOf(e);
                    SingletonItems.getInstance().getEnemigosTanque().getSpawn().getVidaEnemigo().get(indice).vida--;
                    System.out.println("Enemigo: " + indice);
                    disparosAEliminar.add((DisparoDireccion) d);
                    if (SingletonItems.getInstance().getEnemigosTanque().getSpawn().getVidaEnemigo().get(indice).vida <= 0) {
                        enemigosAEliminar.add(e);
                        SingletonItems.getInstance().getEnemigosTanque().getSpawn().getVidaEnemigo().remove(indice);
                        break;
                    }
                }
            }
        }
        if(bossAlive) {
            for (Rectangle d : disparos) {
                for (Rectangle e : SingletonItems.getInstance().getBoss().getBoss()) {
                    if (d.getBoundsInLocal().intersects(e.getBoundsInLocal())) {
                        int indice = SingletonItems.getInstance().getBoss().getBoss().indexOf(e);
                        SingletonItems.getInstance().getBoss().vidaBoss--;
                        disparosAEliminar.add((DisparoDireccion) d);
                        if (SingletonItems.getInstance().getBoss().vidaBoss <= 0) {
                            EfectosMusica efectosMusica = new EfectosMusica("MuerteBoss");
                            Thread hiloEfecto = new Thread(efectosMusica);
                            hiloEfecto.start();
                            enemigosAEliminar.add(e);
                            SingletonItems.getInstance().getBoss().vidaBoss = 0;
                            bossAlive = false;
                            File f=new File(getClass().getResource("/fes/aragon/fondo/GoodEnding.jpg").getFile());
                            SingletonItems.getInstance().getFondo().setImg(new Image(f.toURI().toString()));
                            for (int i=1;i<SingletonItems.getInstance().getElementos().size();i++){
                                SingletonItems.getInstance().getElementos().remove(i);}
                            Timeline timeline =new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                                for (int i=1;i<SingletonItems.getInstance().getElementos().size();i++){
                                    SingletonItems.getInstance().getElementos().remove(i);}
                                System.out.println("Cerrar");
                                //cerrarJuego();
                            }));
                            timeline.play();
                            break;

                        }
                    }
                }
                SingletonItems.getInstance().getBoss().getBoss().removeAll(enemigosAEliminar);
            }
        }

        // Remover los disparos que hicieron colisión
        disparos.removeAll(disparosAEliminar);

        // Remover los enemigos que fueron golpeados
        SingletonItems.getInstance().getEnemigos().getEnemigos().removeAll(enemigosAEliminar);
        SingletonItems.getInstance().getEnemigosVeloz().getEnemigosVeloz().removeAll(enemigosAEliminar);
        SingletonItems.getInstance().getEnemigosTanque().getEnemigosTanque().removeAll(enemigosAEliminar);
    }

    public ArrayList<DisparoDireccion> getDisparos() {
        return disparos;
    }

    public ArrayList<DisparoDireccion> getDisparosBoss() {
        return disparosBoss;
    }

    public void nuke(){
        SingletonItems.getInstance().getEnemigos().getEnemigos().clear();
        SingletonItems.getInstance().getEnemigosVeloz().getEnemigosVeloz().clear();
        SingletonItems.getInstance().getEnemigosTanque().getEnemigosTanque().clear();
    }

    // Método para disparar las balas
    public void disparar(double dirX, double dirY) {

        double x = SingletonItems.getInstance().getNave().getX() + 5; // Posición X de la nave más un pequeño ajuste
        double y = SingletonItems.getInstance().getNave().getY() + 5; // Posición Y de la nave más un pequeño ajuste

        if (tipoDisparo == TipoDisparo.ESCOPETA) {
            EfectosMusica efectosMusica = new EfectosMusica("DisparoEscopeta");
            Thread hiloEfecto = new Thread(efectosMusica);
            hiloEfecto.start();
            // Direcciones de las balas en grados
            double angle1 = Math.toRadians(15); // Ángulo más cerrado
            double angle2 = Math.toRadians(-15); // Ángulo más cerrado en sentido contrario
            double cos1 = Math.cos(angle1);
            double sin1 = Math.sin(angle1);
            double cos2 = Math.cos(angle2);
            double sin2 = Math.sin(angle2);

            double originalDirX = dirX;
            double originalDirY = dirY;
            double length = Math.sqrt(originalDirX * originalDirX + originalDirY * originalDirY);

            originalDirX /= length;
            originalDirY /= length;

            // Calcular las direcciones para las balas diagonales
            double newDirX1 = originalDirX * cos1 - originalDirY * sin1;
            double newDirY1 = originalDirX * sin1 + originalDirY * cos1;
            double newDirX2 = originalDirX * cos2 - originalDirY * sin2;
            double newDirY2 = originalDirX * sin2 + originalDirY * cos2;

            disparos.add(new DisparoDireccion(x, y, originalDirX, originalDirY, velocidadDisparo));
            disparos.add(new DisparoDireccion(x, y, newDirX1, newDirY1, velocidadDisparo));
            disparos.add(new DisparoDireccion(x, y, newDirX2, newDirY2, velocidadDisparo));
        } else if (tipoDisparo == TipoDisparo.VELOCIDAD) {
            disparos.add(new DisparoDireccion(x, y, dirX, dirY, velocidadDisparo)); // Ajustar la velocidad aquí
        } else {
            disparos.add(new DisparoDireccion(x, y, dirX, dirY, velocidadDisparo));
            EfectosMusica efectosMusica = new EfectosMusica("Disparo");
            Thread hiloEfecto = new Thread(efectosMusica);
            hiloEfecto.start();
        }
    }

    public void setTipoDisparo(TipoDisparo tipoDisparo) {
        this.tipoDisparo = tipoDisparo;
    }

    public void addDisparo(DisparoDireccion disparo) {
        disparosBoss.add(disparo);
    }

    public boolean isBossAlive() {
        return bossAlive;
    }

    public void setBossAlive(boolean bossAlive) {
        this.bossAlive = bossAlive;
    }
}

