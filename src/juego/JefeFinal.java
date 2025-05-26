package juego;

import java.awt.Color;
import entorno.Entorno;

public class JefeFinal extends Murcielago {
    private int vida;
    private boolean ataqueEspecialDisponible;
    private int cooldownAtaque;
    
    public JefeFinal(double x, double y) {
        super(x, y, 1.5, 10); 
        this.vida = 20;
        this.ataqueEspecialDisponible = true;
        this.cooldownAtaque = 0;
    }
    
    @Override
    public void dibujar(Entorno entorno) {
        // Cuerpo rojo oscuro
        entorno.dibujarCirculo(x, y, 40, new Color(120, 0, 0));
        
        // Barra de vida
        double porcentajeVida = vida / 20.0;
        entorno.dibujarRectangulo(x, y - 50, 80 * porcentajeVida, 6, 0, 
            porcentajeVida > 0.6 ? Color.GREEN :
            porcentajeVida > 0.3 ? Color.YELLOW : Color.RED);
            
        // Cuernos
        entorno.dibujarTriangulo(x - 20, y - 30, 15, 25, 300, Color.DARK_GRAY);
        entorno.dibujarTriangulo(x + 20, y - 30, 15, 25, 60, Color.DARK_GRAY);
    }
    
    public void ataqueEspecial(Gondolf gondolf) {
        if (ataqueEspecialDisponible && cooldownAtaque <= 0) {
            gondolf.recibirDanio(15); 
            cooldownAtaque = 180;
            ataqueEspecialDisponible = false;
        }
    }
    
    public void actualizar() {
        if (cooldownAtaque > 0) {
            cooldownAtaque--;
        } else {
            ataqueEspecialDisponible = true;
        }
    }
    
    @Override
    public void recibirDanio(int cantidad) {
        this.vida -= cantidad;
        if (this.vida <= 0) {
            this.activo = false; 
        }
    }
    
    @Override
    public boolean estaActivo() {
        return this.activo && this.vida > 0;
    }
}