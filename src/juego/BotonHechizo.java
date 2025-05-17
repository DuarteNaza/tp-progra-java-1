package juego;

import java.awt.Color;
import entorno.Entorno;

public class BotonHechizo {
	private String nombre;
	private int y;
	private int costo;

	public BotonHechizo(String nombre, int y, int costo) {
		this.nombre = nombre;
		this.y = y;
		this.costo = costo;
	}

	public void dibujar(Entorno entorno, int offsetX, boolean seleccionado) {
		Color color = seleccionado ? Color.ORANGE : Color.WHITE;
		entorno.dibujarRectangulo(offsetX + 100, y, 180, 40, 0, color);
		entorno.cambiarFont("Arial", 14, Color.BLACK);
		entorno.escribirTexto(nombre + " (" + costo + ")", offsetX + 30, y + 5);
	}

	public boolean fueClickeado(int mouseX, int mouseY, int offsetX) {
		return mouseX >= offsetX && mouseX <= offsetX + 200 && mouseY >= y - 20 && mouseY <= y + 20;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCosto() {
		return costo;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}