package juego;

import java.awt.Color;
import entorno.Entorno;

public class Murcielago {
    private double x, y;
    private double velocidad;
    private boolean activo;
    private int vida;

    public Murcielago(double x, double y, double velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.activo = true;
        this.vida = 1;
    }

    public void moverHacia(Gondolf gondolf) {
        if (!activo) return;
        
        double dx = gondolf.getX() - x;
        double dy = gondolf.getY() - y;
        double distancia = Math.sqrt(dx*dx + dy*dy);
        
        if (distancia > 0) {
            x += (dx/distancia) * velocidad;
            y += (dy/distancia) * velocidad;
        }
    }
    public void recibirDanio(int cantidad) {
        vida -= cantidad;
        if (vida <= 0) {
            activo = false; 
        }
    }
    public boolean estaActivo() {
        return this.activo;
    }



    public void dibujar(Entorno entorno) {
        if (!activo) return;
        entorno.dibujarCirculo(x, y, 15, Color.RED);
    }

    // ... getters y setters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    
}