package juego;

import java.awt.Color;
import java.util.ArrayList;

import entorno.Entorno;

public class Murcielago {
    private double x, y;
    private double velocidad;
    private boolean activo;
    private int vida;
    private double velocidadBase;
    private double velocidadActual;

    public Murcielago(double x, double y, double velocidadBase,int oleadaActual) {
        this.x = x;
        this.y = y;
        this.velocidadBase = velocidadBase;
        this.velocidadActual = velocidadBase + (oleadaActual * 0.3);
        this.activo = true;
        this.vida = 1;
    }

    public void moverHacia(Gondolf gondolf) {
        if (!activo) return;
        
        double dx = gondolf.getX() - x;
        double dy = gondolf.getY() - y;
        double distancia = Math.sqrt(dx*dx + dy*dy);
        
        if (distancia > 0) {
        	x += (dx / distancia) * velocidadActual;
        	y += (dy / distancia) * velocidadActual;
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
    
    public void mover(double newX, double newY, ArrayList<Murcielago> otrosMurcielagos, double minDistance) {
        for (Murcielago otro : otrosMurcielagos) {
            if (otro != this) {
                double distanceX = Math.abs(newX - otro.x);
                double distanceY = Math.abs(newY - otro.y);
                if (distanceX < minDistance && distanceY < minDistance) {
                    if (distanceX < distanceY) {
                        newX += (newX > otro.x) ? minDistance : -minDistance;
                    } else {
                        newY += (newY > otro.y) ? minDistance : -minDistance;
                    }
                }
            }
        }
        this.x = newX;
        this.y = newY;
    }
    
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    
    
}