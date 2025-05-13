package juego;
import java.awt.Color;
import entorno.Entorno;

public class Menu{
	private int x;
	private int ancho;
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
	        //  Fondo del menu
		  entorno.dibujarRectangulo(x, 0, ancho, alto, 0, new Color(50, 50, 50));
	        
	        //  Título
	        entorno.cambiarFont("Arial", 20, Color.WHITE);
	        entorno.escribirTexto("HECHIZOS", x + 50, 40);
	        
	        //  Botones
	        for (int i = 0; i < botones.length; i++) {
	            botones[i].dibujar(entorno, x, i == hechizoSeleccionado);
	        }
	        
	        //  Estadísticas
	        entorno.cambiarFont("Arial", 16, Color.WHITE);
	        entorno.escribirTexto("VIDA: " + vidaJugador, x + 20, 200);
	        entorno.escribirTexto("MAGIA: " + energiaMagica, x + 20, 230);
	        entorno.escribirTexto("ENEMIGOS: " + enemigosEliminados, x + 20, 260);
	        
	       
	    }
	  }

