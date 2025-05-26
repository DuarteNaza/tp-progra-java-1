package juego;

import java.awt.Color;
import entorno.Entorno;

public class MurcielagoCurador extends Murcielago {
    private int radioCuracion;
    
    public MurcielagoCurador(double x, double y, double velocidadBase, int oleada) {
        super(x, y, velocidadBase, oleada);
        this.radioCuracion = 150;
    }
    
    @Override
    public void dibujar(Entorno entorno) {
        // Murciélago verde para diferenciarlo
        entorno.dibujarCirculo(x, y, 15, new Color(0, 150, 0));
        entorno.dibujarTriangulo(x - 15, y, 10, 20, 315, new Color(0, 100, 0));
        entorno.dibujarTriangulo(x + 15, y, 10, 20, 45, new Color(0, 100, 0));
    }
    
    public void curarAliados(Murcielago[] aliados) {
        for (Murcielago aliado : aliados) {
            if (aliado != null && aliado != this && distancia(aliado) < radioCuracion) {
                aliado.recibirCuracion(1); // +1 de vida por frame
            }
        }
    }
}