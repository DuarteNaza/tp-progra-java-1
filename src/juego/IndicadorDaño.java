package juego;

import java.awt.Color;
import entorno.Entorno;

public class IndicadorDaño {
    private double x, y;
    private String texto;
    private int framesRestantes;
    private Color color;

    public IndicadorDaño(double x, double y, int valor, Color color) {
        this.x = x;
        this.y = y;
        this.texto = (valor > 0) ? "-" + valor : "+" + Math.abs(valor); // Ej: "-10" o "+20"
        this.framesRestantes = 60; // Dura 1 segundo (60 frames)
        this.color = color;
    }

    public void dibujar(Entorno entorno) {
        if (framesRestantes > 0) {
            entorno.cambiarFont("Arial", 20, color);
            entorno.escribirTexto(texto, x, y - (framesRestantes / 2)); // Sube mientras desaparece
            framesRestantes--;
        }
    }

    public boolean estaActivo() {
        return framesRestantes > 0;
    }
}