package fes.aragon.modelo;

import javafx.scene.shape.Rectangle;

// Clase interna para manejar disparos con dirección
public class DisparoDireccion extends Rectangle {
    private double dirX;
    private double dirY;
    private double velocidad;

    public DisparoDireccion(double x, double y, double dirX, double dirY, double velocidad) {
        super(x, y, 10, 10);
        double length = Math.sqrt(dirX * dirX + dirY * dirY);
        this.dirX = (dirX / length); // Normalización del vector de movimiento en X
        this.dirY = (dirY / length); // Normalización del vector de movimiento en Y
        this.velocidad = velocidad;
    }

    public void mover() {
        this.setX(this.getX() + dirX * velocidad);
        this.setY(this.getY() + dirY * velocidad);
    }
}
