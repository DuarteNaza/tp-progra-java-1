package juego;

import java.awt.Color;
import entorno.Entorno;

public class Hechizo {
    private double x, y;
    private int radio;
    private int daño;
    private int costo;
    private boolean activo;

    public Hechizo(double x, double y, int radio, int daño, int costo) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.daño = daño;
        this.costo = costo;
        this.activo = true;
    }

    public void dibujar(Entorno entorno) {
        if (activo) {
            entorno.dibujarCirculo(x, y, radio * 2, new Color(0, 255, 255, 100));
        }
    }

    public boolean afectaA(Murcielago m) {
        double dx = x - m.getX();
        double dy = y - m.getY();
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia <= radio;
    }

    public boolean estaActivo() {
        return activo;
    }

    public void desactivar() {
        activo = false;
    }

    public int getCosto() {
        return costo;
    }

    public int getDaño() {
        return daño;
    }
}
