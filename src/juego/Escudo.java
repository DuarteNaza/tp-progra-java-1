package juego;

import java.awt.Color;
import entorno.Entorno;

public class Escudo {
    private int duracionMaxima;        // En frames
    private int duracionRestante;      // En frames
    private int cooldownRestante;      // En frames
    private final int cooldownMaximo = 3600; // 5 segundos si tu juego va a 60 FPS

    public Escudo(int duracionFrames) {
        this.duracionMaxima = duracionFrames;
        this.duracionRestante = 0;
        this.cooldownRestante = 0;
    }

    public void activar() {
        if (!estaActivo() && !enCooldown()) {
            this.duracionRestante = duracionMaxima;
            this.cooldownRestante = cooldownMaximo;
        }
    }

    public void actualizar() {
        if (duracionRestante > 0) {
            duracionRestante--;
        }
        if (cooldownRestante > 0) {
            cooldownRestante--;
        }
    }

    public boolean estaActivo() {
        return duracionRestante > 0;
    }

    public boolean enCooldown() {
        return cooldownRestante > 0;
    }

    public void dibujar(Entorno entorno, double x, double y) {
        if (estaActivo()) {
            entorno.dibujarCirculo(x, y, 40, new Color(0, 200, 255, 120));
        }

        // Dibujar barra de duración
        if (estaActivo()) {
            double porcentaje = (double) duracionRestante / duracionMaxima;
            int barraAncho = 60;
            int barraAlto = 8;
            entorno.dibujarRectangulo(x, y - 40, barraAncho, barraAlto, 0, Color.GRAY);
            entorno.dibujarRectangulo(x - (barraAncho * (1 - porcentaje)) / 2, y - 40, barraAncho * porcentaje, barraAlto, 0, Color.CYAN);
        }
    }
}
