package juego;

import java.awt.Color;
import java.util.ArrayList;

import entorno.Entorno;

public class Murcielago {
    protected double x;
    protected double y;
    protected boolean activo;
    private int vida;
    private double velocidadBase;
    private double velocidadActual;
    private int danio;

    public Murcielago(double x, double y, double velocidadBase,int oleadaActual) {
        this.x = x;
        this.y = y;
        this.velocidadBase = velocidadBase;
        this.velocidadActual = velocidadBase + (oleadaActual * 0.3);
        this.activo = true;
        this.vida = 1;
        this.danio = 10;
    }
    public double distancia(Gondolf gondolf) {
    	return Math.sqrt(Math.pow(x - gondolf.getX(), 2) + Math.pow(y - gondolf.getY(), 2));
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
        entorno.dibujarCirculo(x, y, 15, new Color(90, 40, 60));
        
        entorno.dibujarTriangulo(x - 15, y, 10, 20, 315, new Color(150, 30, 50));
        entorno.dibujarTriangulo(x + 15, y, 10, 20, 45, new Color(150, 30, 50));
        
        entorno.dibujarCirculo(x - 5, y - 2, 3, Color.RED);
        entorno.dibujarCirculo(x + 5, y - 2, 3, Color.RED);
    }
    
    public void mover(double newX, double newY, Murcielago[] otrosMurcielagos, int cantidadOtros, double minDistance) {
    	 for (int i = 0; i < cantidadOtros; i++) {
             Murcielago otro = otrosMurcielagos[i];
             if (otro != null && otro != this) {
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
    public void recibirCuracion(int cantidad) {
        if (this.vida < 3) { 
            this.vida += cantidad;
        }
    }

    protected double distancia(Murcielago otro) {
        return Math.sqrt(Math.pow(x - otro.x, 2) + Math.pow(y - otro.y, 2));
    }
    public void aumentarDanio() {
        this.danio += 5;
    }
    
    public int getDanio() {
        return danio;
    }
    
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    
    
}