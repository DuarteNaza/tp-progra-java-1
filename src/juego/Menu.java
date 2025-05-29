package juego;
import java.awt.Color;
import entorno.Entorno;

public class Menu{
	private BotonHechizo[] botones;
	private int x;
	private int y;
	private int alto;
	private int hechizoSeleccionado;
	private int enemigosEliminados;
	private int vidaJugador;
	private int energiaMagica;
	
	public int getY() {
		return y;
	}
	
public void setY(int y) {
		this.y = y;
	}

	public int ancho;

	public int getAncho() {
		return ancho;
	}
	

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}


	
	 public Menu(int x, int ancho, int alto) {
	        this.botones = new BotonHechizo[] {
	            new BotonHechizo("Explosion", 100, 0), 
	            new BotonHechizo("Fuego", 160, 30),
	            new BotonHechizo ("Escudo", 220, 20)
	        };
	        this.x = x;
	        this.ancho = ancho;
	        this.alto = alto;
	    }
	

	public void actualizar (int vida, int magia, int kills) {
		this.vidaJugador = vida;
		this.energiaMagica = magia;
		this.enemigosEliminados = kills;
		
	}
	
	
	public void dibujar(Entorno entorno,String tiempoTotal, String tiempoOleada) {
	    
		entorno.dibujarRectangulo(x + ancho / 2, alto / 2, ancho, alto, 0, new Color(240,230,200));
		entorno.dibujarRectangulo(x + ancho / 2, alto / 2, ancho - 10, alto - 10, 0, new Color(200, 180, 150));
	    entorno.cambiarFont("Arial", 16, Color.BLACK);
	    entorno.cambiarFont("Papyrus", 18, new Color(161, 102, 47));
	    entorno.escribirTexto("VIDA: " + vidaJugador, x + 20, 26);
	    entorno.escribirTexto("MAGIA: " + energiaMagica, x + 20, 55);
	    entorno.escribirTexto("ENEMIGOS: " + enemigosEliminados, x + 20, 220);
	    entorno.cambiarFont("Arial", 18, Color.WHITE);
        entorno.cambiarFont("Arial", 14, Color.WHITE);
        entorno.escribirTexto("TIEMPO TOTAL: " + tiempoTotal, x + 20, 250); 
        entorno.escribirTexto("TIEMPO OLEADA: " + tiempoOleada, x + 20, 280);
        entorno.cambiarFont("Arial", 16, Color.BLACK);

	    for (int i = 0; i < botones.length; i++) {
	        boolean seleccionado = (i == hechizoSeleccionado);
	        botones[i].dibujar(entorno, x, seleccionado);
	    }
	}

	public void manejarClick(int mouseX, int mouseY) {
		for (int i = 0; i < botones.length; i++) {
			 if (mouseX >= x && 
			            mouseX <= x + 180 && 
			            mouseY >= botones[i].getY() - 20 && 
			            mouseY <= botones[i].getY() + 20) {
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

