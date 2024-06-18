package fes.aragon.modelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class ComponentesJuego {
    protected double x;
    protected double y;
    protected String imagen;
    protected double velocidad;

    public ComponentesJuego(double x, double y, String imagen, double velocidad) {
        super();
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.velocidad = velocidad;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    public abstract void pintar(GraphicsContext graficos);
    public abstract void teclado(KeyEvent evento, boolean presiona);
    public abstract void raton(MouseEvent evento);
    public abstract void logicaCalculos();
}
