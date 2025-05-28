package juego;

import java.awt.Color;
import entorno.Entorno;

public class MurcielagoRalentizador extends Murcielago {
    private int duracionRalentizacion;
    private boolean efectoAplicado;
    
    public MurcielagoRalentizador(double x, double y, double velocidadBase, int oleada) {
        super(x, y, velocidadBase, oleada);
        this.duracionRalentizacion = 120; // 2 segundos
        this.efectoAplicado = false;
        
    }
    
    @Override
    public void dibujar(Entorno entorno) {
        // Murciélago morado para diferenciarlo
        entorno.dibujarCirculo(x, y, 15, new Color(128, 0, 128));
        entorno.dibujarTriangulo(x - 15, y, 10, 20, 315, new Color(100, 0, 100));
        entorno.dibujarTriangulo(x + 15, y, 10, 20, 45, new Color(100, 0, 100));
    }
    
    public void aplicarEfecto(Gondolf gondolf) {
        if (distancia(gondolf) < 100 && !efectoAplicado) {
            gondolf.ralentizar(0.5); // Reduce velocidad al 70%
            efectoAplicado = true;
            
            // Temporizador para resetear el efecto
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        efectoAplicado = false;
                    }
                },
                duracionRalentizacion * 16 
            );
        }
    }
    
    @Override
    public void moverHacia(Gondolf gondolf) {
        super.moverHacia(gondolf);
        aplicarEfecto(gondolf);
    }
}