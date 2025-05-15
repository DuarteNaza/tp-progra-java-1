package juego;

import java.awt.Color;
import entorno.Entorno;

public class Hechizos {
    private double x, y;
    private double radio;
    private int daño;
    private boolean activo;

    public Hechizos(double x, double y, double radio, int daño) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.daño = daño;
        this.activo = true;
    }

    public void dibujar(Entorno entorno) {
        if (activo) {
            entorno.dibujarCirculo(x, y, radio * 2, new Color(255, 255, 0, 100));
        }
    }

    public boolean afectaA(Murcielago m) {
        double distancia = Math.sqrt(Math.pow(x - m.getX(), 2) + Math.pow(y - m.getY(), 2));
        return distancia <= radio;
    }

    public boolean estaActivo() {
        return activo;
    }

    public void desactivar() {
        this.activo = false;
    }

    public int getDaño() {
        return daño;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}