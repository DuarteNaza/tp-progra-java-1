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
    	        // Efecto de explosión
    	        for (int i = 0; i < 3; i++) {
    	            Color color = (i == 0) ? new Color(255, 87, 51, 100) : 
    	                       (i == 1) ? new Color(255, 150, 0, 100) : 
    	                       new Color(255, 87, 51, 0);
    	            entorno.dibujarCirculo(x, y, radio * (i + 1), color);
    	        }
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