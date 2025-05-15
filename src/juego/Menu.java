package juego;
import java.awt.Color;
import entorno.Entorno;

public class Menu{
	private int x;
	public int ancho;

	public int getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	private int alto;

	private BotonHechizo[] botones;
	private int hechizoSeleccionado;
	
	private int enemigosEliminados;
	private int vidaJugador;
	private int energiaMagica;
	
	public Menu(int x, int ancho, int alto) {
		this.x = x;
		this.ancho = ancho;
		this.alto = alto;
		this.hechizoSeleccionado =  -1;
		
		this.botones = new BotonHechizo[] {
				new BotonHechizo("Explosion",100,0), 
				new BotonHechizo("Fuego",160,30)
				
		};
		
	}
	public void actualizar (int vida, int magia,int kills) {
		this.vidaJugador = vida;
		this.energiaMagica = magia;
		this.enemigosEliminados = kills;
		
	}
	public void dibujar(Entorno entorno) {
	    entorno.dibujarRectangulo(x + ancho / 2, alto / 2, ancho, alto, 0, new Color(80,80,80));

	    entorno.cambiarFont("Arial", 18, Color.BLACK);
	    entorno.escribirTexto("VIDA: " + vidaJugador, x + 20, 26);
	    entorno.escribirTexto("MAGIA: " + energiaMagica, x + 20, 55);
	    entorno.escribirTexto("ELIMINADOS: " + enemigosEliminados, x + 20, 180);
	    
	    for (int i = 0; i < botones.length; i++) {
	        boolean seleccionado = (i == hechizoSeleccionado);
	        botones[i].dibujar(entorno, x, seleccionado);
	    }
	}

	public void manejarClick(int mouseX, int mouseY) {
		for (int i = 0; i < botones.length; i++) {
			if (botones[i].fueClickeado(mouseX, mouseY, x)) {
				hechizoSeleccionado = i;
				return;
			}
		}
	}

	public void resetSeleccion() {
		hechizoSeleccionado = -1;
	}

	public BotonHechizo getHechizoSeleccionado() {
		if (hechizoSeleccionado >= 0 && hechizoSeleccionado < botones.length) {
			return botones[hechizoSeleccionado];
		}
		return null;
	}

}

