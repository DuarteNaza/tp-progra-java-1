package juego;

import java.awt.Color;
import entorno.Entorno;

public class Pocion {
    private double x, y;
    private int tipo; 
    private boolean activa;
    
    public Pocion(double x, double y) {
        this.x = x;
        this.y = y;
        this.tipo = (int)(Math.random() * 2);
        this.activa = true;
    }
    
    public void dibujar(Entorno entorno) {
        if (activa) {
            Color color = (tipo == 0) ? new Color(255, 50, 50) : new Color(50, 50, 255);
            entorno.dibujarCirculo(x, y, 20, color);
        }
    }
    
    public boolean colisionaCon(Gondolf gondolf) {
        double distancia = Math.sqrt(Math.pow(x - gondolf.getX(), 2) + Math.pow(y - gondolf.getY(), 2));
        return distancia < 25 && activa;
    }
    
    public int getTipo() { return tipo; }
    	public void desactivar() { 
    		activa = false; 
    	}
}